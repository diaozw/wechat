package com.diao.test.wechat.officialaccounts.controller;

import com.diao.test.wechat.utils.CheckUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author diaozw
 * @date 2019-05-10
 * @describe 微信公众号登录
 */
@Controller
@RequestMapping("weixin")
public class OfficialAccountsController {
    /**
     * 用于初始服务器连通验证
     * @param request
     * @param response
     */
    @RequestMapping(value = "wx",method= RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response){
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if(CheckUtil.checkSignature(signature, timestamp, nonce)){
                out.write(echostr);
            }
            System.out.println("--------------success-------------");
        } catch (IOException e) {
            System.out.println("--------------fail----------------");
            e.printStackTrace();
        }finally{
            out.close();
        }
    }
    @RequestMapping(value = "image",method= RequestMethod.POST)
    public void upload(@RequestParam("files") MultipartFile[] files){
        System.out.println("长度---" + files.length);
        if(files.length > 0 ){
            for (MultipartFile file :files) {
                System.out.println("文件为：" + file.getOriginalFilename());
            }
        }
    }
}
