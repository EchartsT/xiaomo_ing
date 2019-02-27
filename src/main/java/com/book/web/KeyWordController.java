package com.book.web;

import com.book.domain.KeyWord;
import com.book.service.KeyWordService;
import com.book.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

@Controller
public class KeyWordController {
    private KeyWordService keyWordService;
    @Autowired
    private void setKeyWordService (KeyWordService keyWordService){this.keyWordService=keyWordService;}

    //显示所有关键词
    @RequestMapping("/keywordList.html")
    public ModelAndView keywordList() throws IOException {
        KeyWordCommand keyWordCommand = new KeyWordCommand();
        keyWordCommand.getWordsFrequency(FileUtil.createDirectory()+"/"+"allchatdata.txt",FileUtil.createDirectory()+"/"+"removewords.txt");
        ModelAndView modelAndView = new ModelAndView("keywordList");
        modelAndView.addObject("list", keyWordService.keywordList());
        return modelAndView;
    }
    //删除对应操作记录
    @RequestMapping("/deletekeyword.html")
    public String deleteKeyword(HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {
        String operatorId = request.getParameter("keywordId");
        KeyWord keyWords = keyWordService.matchKeyword_Single(operatorId);
        FileWriter fw =null;
        File f=new File(FileUtil.createDirectory()+"/"+"removewords.txt");
        fw = new FileWriter(f,true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(keyWords.getKeywordName());
        pw.flush();
        pw.close();
        fw.close();
        int res = keyWordService.deletekeyword(operatorId);
        if (res == 1) {
            redirectAttributes.addFlashAttribute("succ", "操作流水记录删除成功！");
            return "redirect:/keywordList.html";
        } else {
            redirectAttributes.addFlashAttribute("error", "操作流水记录删除失败！");
            return "redirect:/keywordList.html";
        }
    }
    //搜索
    @RequestMapping("/querykeyword.html")
    public ModelAndView queryOperatorDo(HttpServletRequest request) throws UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView("keywordList");
        String str = new String(request.getParameter("searchWord").getBytes("ISO-8859-1"),"utf-8");
        modelAndView.addObject("list", keyWordService.matchKeyword(str));
        return modelAndView;
    }
}
