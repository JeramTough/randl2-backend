package com.jeramtough.randl2.adminapp.service.impl;

import com.jeramtough.jtcomponent.tree.adapter.OnceTreeNodeAdapter;
import com.jeramtough.jtcomponent.tree.processor.DefaultTreeProcessor;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtcomponent.tree.util.TreeNodeUtils;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.adminapp.component.setting.AppSetting;
import com.jeramtough.randl2.adminapp.component.userdetail.SystemUser;
import com.jeramtough.randl2.adminapp.component.userdetail.UserHolder;
import com.jeramtough.randl2.adminapp.component.userdetail.login.AdminUserLoginer;
import com.jeramtough.randl2.adminapp.component.userdetail.login.UserLoginer;
import com.jeramtough.randl2.adminapp.service.RandlModuleAuthService;
import com.jeramtough.randl2.common.component.moduletree.ModuleAuthDtoOnceTreeNodeAdapter;
import com.jeramtough.randl2.common.mapper.SourceSurfaceImageMapper;
import com.jeramtough.randl2.common.model.dto.RandlModuleAuthDto;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.params.adminuser.UserCredentials;
import com.jeramtough.randl2.adminapp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.function.Predicate;

/**
 * <pre>
 * Created on 2020/10/3 11:09
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class LoginServiceImpl extends BaseServiceImpl implements LoginService, WithLogger {

    private final SourceSurfaceImageMapper surfaceImageMapper;
    private final RandlModuleAuthService randlModuleAuthService;
    private final AppSetting appSetting;

    @Autowired
    public LoginServiceImpl(WebApplicationContext wc,
                            SourceSurfaceImageMapper surfaceImageMapper,
                            RandlModuleAuthService randlModuleAuthService,
                            AppSetting appSetting) {
        super(wc);
        this.surfaceImageMapper = surfaceImageMapper;
        this.randlModuleAuthService = randlModuleAuthService;
        this.appSetting = appSetting;
    }

    @Override
    public SystemUserDto adminLogin(UserCredentials userCredentials) {
        BeanValidator.verifyParams(userCredentials);

        UserLoginer userLoginer = super.getWC().getBean(AdminUserLoginer.class);
        SystemUser systemUser;
        try {
            systemUser = userLoginer.login(userCredentials);
        }
        catch (ApiResponseException e) {
            getLogger().debug("登录失败! 登录参数%s 因为：%s", userCredentials.toString(), e.getMessage());
            throw e;
        }
        getLogger().verbose("登录成功，登录参数%s", userCredentials.toString());

        //到了这里，用户必须是登录成功了的
        UserHolder.afterLogin(systemUser);

        //processing SystemUserDto
        SystemUserDto systemUserDto = getMapperFacade().map(systemUser, SystemUserDto.class);
        String surfaceImage = surfaceImageMapper.selectById(
                systemUser.getSurfaceImageId()).getSurfaceImage();
        systemUserDto.setSurfaceImage(surfaceImage);

        getLogger().verbose("开始获取用户模块授权信息");
        List<RandlModuleAuthDto> moduleAuthDtoList =
                randlModuleAuthService.getRandlModuleAuthDtosByAppIdAndRoleId(appSetting.getAdminDefaultAppId(),
                        systemUser.getRoleId());


        getLogger().debug(Arrays.deepToString(moduleAuthDtoList.toArray(new RandlModuleAuthDto[0])));

        getLogger().verbose("开始处理用户模块授权信息作为树形结构");
        List<OnceTreeNodeAdapter<RandlModuleAuthDto>> onceTreeNodeAdapterList = new ArrayList<>();
        for (RandlModuleAuthDto randlModuleAuthDto : moduleAuthDtoList) {
            OnceTreeNodeAdapter<RandlModuleAuthDto> adapter = new ModuleAuthDtoOnceTreeNodeAdapter(
                    randlModuleAuthDto);
            onceTreeNodeAdapterList.add(adapter);
        }
        TreeNode rootTreeNode = new DefaultTreeProcessor().processing(onceTreeNodeAdapterList);
        rootTreeNode = rootTreeNode.andPredicate(treeNode -> {
            if (treeNode.getValue() != null && treeNode.getValue() instanceof RandlModuleAuthDto) {
                RandlModuleAuthDto moduleAuthDto = (RandlModuleAuthDto) treeNode.getValue();
                //如果是菜单的话就留着
                return moduleAuthDto.getModuleType() == 0;
            }
            return false;
        });
        Map<String, Object> treeNodeMap = TreeNodeUtils.toTreeMap(rootTreeNode);
        getLogger().debug("处理为树形结构并且过滤出菜单模块完成: " + rootTreeNode.getDetail());

        systemUserDto.setModuleAuthList((List<Map<String, Object>>) treeNodeMap.get("children"));

        return systemUserDto;
    }

    @Override
    public String adminLogout() {
        return null;
    }
}
