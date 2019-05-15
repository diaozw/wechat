package com.diao.test.wechat.handler;

import com.diao.test.wechat.builder.ImageBuilder;
import com.diao.test.wechat.builder.TextBuilder;
import com.diao.test.wechat.utils.FileUtils;
import com.diao.test.wechat.utils.JsonUtils;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            // 文字类型
            if (wxMessage.getMsgType().equals(XmlMsgType.TEXT)) {
                if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                        && weixinService.getKefuService().kfOnlineList()
                        .getKfOnlineList().size() > 0) {
                    return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                            .fromUser(wxMessage.getToUser())
                            .toUser(wxMessage.getFromUser()).build();
                }else if(StringUtils.startsWithAny(wxMessage.getContent(), "刁振伟", "1")){
                    String context1 = "绿巨人大闹好莱坞";
                    return new TextBuilder().build(context1, wxMessage, weixinService);
                }
            }else if(wxMessage.getMsgType().equals(XmlMsgType.IMAGE)){
                // 获得一个在系统临时目录的文件
                File file = weixinService.getMaterialService().mediaDownload(wxMessage.getMediaId());
                // 获取
                int fileSize = FileUtils.getFileSize(file);
               if (fileSize > 2*1048576){
                    String context1 = "文件太大了小老弟！";
                   return new TextBuilder().build(context1, wxMessage, weixinService);
               }
                // 上传图片至临时素材
                WxMediaUploadResult res = weixinService.getMaterialService().mediaUpload(XmlMsgType.IMAGE, file);
                // 回复图片类型的数据

                return new ImageBuilder().build(res.getMediaId(), wxMessage, weixinService);
               // String context1 = "不支持图呀，老铁";
                //return new TextBuilder().build(context1, wxMessage, weixinService);
            }else if(wxMessage.getMsgType().equals(XmlMsgType.VIDEO)){
                String context1 = "不支持视频呀，老铁";
                return new TextBuilder().build(context1, wxMessage, weixinService);
            }else if(wxMessage.getMsgType().equals(XmlMsgType.VOICE)){
                String context1 = "不支持语音呀，老铁";
                return new TextBuilder().build(context1, wxMessage, weixinService);
            }

        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        //TODO 组装回复消息
        String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);

        return new TextBuilder().build(content, wxMessage, weixinService);

    }

}
