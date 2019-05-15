package com.diao.test.wechat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author diaozw
 * @descrition 文件操作类
 * @date 2019-05-15
 */
public class FileUtils {

    /**
     * 获取文件大小
     * @param file
     */
    public static int getFileSize(File file) {
        FileInputStream fis = null;
        try {
            if(file.exists() && file.isFile()){
                String fileName = file.getName();
                fis = new FileInputStream(file);
//                System.out.println("文件"+fileName+"的大小是："+fis.available()+"\n");
                return fis.available();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(null!=fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        File file = new File("C:\\Users\\EDZ\\Pictures\\身份\\微信图片_20190514191805.jpg");
        getFileSize(file);
    }
}
