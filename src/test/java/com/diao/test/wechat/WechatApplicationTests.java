package com.diao.test.wechat;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @describtion 微信公众平台测试接口
 * @author diaozw
 * @date 2019-05-14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatApplicationTests {
    @Autowired
    private WxMpService wxService;

    @Test
    public void contextLoads() {
        try {
          String token = wxService.getAccessToken();
            System.out.println("获取到的token为：---" + token);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

}
