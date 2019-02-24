package com.book.web;

import com.book.util.DownloadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class FileController {
    @RequestMapping("/filedownload.html")
    public void handleFileDownload(@RequestParam String fileName, HttpServletResponse response) throws Exception{
        String filePath = "/Users/ashley/xiaomo_ing/chatData/"+fileName;
        DownloadUtil du = new DownloadUtil();
        du.download(filePath, fileName, response, false);
    }
}
