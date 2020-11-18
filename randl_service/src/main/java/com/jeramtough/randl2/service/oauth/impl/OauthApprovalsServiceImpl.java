package com.jeramtough.randl2.service.oauth.impl;

import com.jeramtough.randl2.common.mapper.OauthApprovalsMapper;
import com.jeramtough.randl2.common.model.dto.OauthApprovalsDto;
import com.jeramtough.randl2.common.model.entity.OauthApprovals;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeramtough.randl2.service.base.impl.MyBaseServiceImpl;
import com.jeramtough.randl2.service.oauth.OauthApprovalsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-11-16
 */
@Service
public class OauthApprovalsServiceImpl extends MyBaseServiceImpl<OauthApprovalsMapper, OauthApprovals,
        OauthApprovalsDto> implements OauthApprovalsService {

    public OauthApprovalsServiceImpl(WebApplicationContext wc) {
        super(wc);
    }

    @Override
    protected OauthApprovalsDto toDto(OauthApprovals oauthApprovals) {
        return null;
    }
}
