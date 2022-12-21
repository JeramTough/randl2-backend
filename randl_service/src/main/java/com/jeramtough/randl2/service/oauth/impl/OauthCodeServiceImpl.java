package com.jeramtough.randl2.service.oauth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeramtough.randl2.common.mapper.OauthCodeMapper;
import com.jeramtough.randl2.common.model.dto.OauthCodeDto;
import com.jeramtough.randl2.common.model.entity.OauthCode;
import com.jeramtough.randl2.service.base.impl.MyBaseServiceImpl;
import com.jeramtough.randl2.service.oauth.OauthCodeService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-11-16
 */
@Service
public class OauthCodeServiceImpl extends MyBaseServiceImpl<OauthCodeMapper, OauthCode, OauthCodeDto> implements
        OauthCodeService {

    public OauthCodeServiceImpl(WebApplicationContext wc) {
        super(wc);
    }

}
