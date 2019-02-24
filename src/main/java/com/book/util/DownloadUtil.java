package com.book.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DownloadUtil {
    /**
     * @param filePath 要下载的文件路径
     * @param returnName 返回的文件名
     * @param response HttpServletResponse
     * @param delFlag 是否删除文件
     */
    public void download(String filePath, String returnName, HttpServletResponse response, boolean delFlag) throws Exception{
        this.prototypeDownload(new File(filePath), returnName, response, delFlag);
    }

    /**
     * @param file 要下载的文件
     * @param returnName 返回的文件名
     * @param response HttpServletResponse
     * @param delFlag 是否删除文件
     */
    public void prototypeDownload(File file,String returnName,HttpServletResponse response,boolean delFlag) throws Exception{
        // 下载文件
        FileInputStream inputStream = null;
        ServletOutputStream outputStream = null;
        if(!file.exists()){
            throw new Exception("文件不存在！");
        }
        try {
            response.reset();
            //设置响应类型	PDF文件为"application/pdf"，WORD文件为："application/msword"， EXCEL文件为："application/vnd.ms-excel"。
            response.setContentType("application/octet-stream;charset=utf-8");

            //设置响应的文件名称,并转换成中文编码
            //returnName = URLEncoder.encode(returnName,"UTF-8");
            returnName = response.encodeURL(new String(returnName.getBytes(),"utf-8"));	//保存的文件名,必须和页面编码一致,否则乱码

            //attachment作为附件下载；inline客户端机器有安装匹配程序，则直接打开；注意改变配置，清除缓存，否则可能不能看到效果
            response.addHeader("Content-Disposition",   "attachment;filename="+returnName);

            //将文件读入响应流
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            int length = 1024;
            int readLength=0;
            byte buf[] = new byte[1024];
            readLength = inputStream.read(buf, 0, length);
            while (readLength != -1) {
                outputStream.write(buf, 0, readLength);
                readLength = inputStream.read(buf, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //删除原文件

            if(delFlag) {
                file.delete();
            }
        }
    }

}
