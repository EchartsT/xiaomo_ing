package com.book.web;

import com.book.service.KeyWordService;
import com.book.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Controller
public class KeyWordController {
    private KeyWordService keyWordService;
    @Autowired
    private void setKeyWordService (KeyWordService keyWordService){this.keyWordService=keyWordService;}

    //显示所有关键词
    @RequestMapping("/keywordList.html")
    public ModelAndView keywordList() throws IOException {
        KeyWordCommand keyWordCommand = new KeyWordCommand();
        keyWordCommand.getWordsFrequency(FileUtil.createDirectory()+"/"+"allchatdata.txt");
        ModelAndView modelAndView = new ModelAndView("keywordList");
        modelAndView.addObject("list", keyWordService.keywordList());
        return modelAndView;
    }
    //删除对应操作记录
    @RequestMapping("/deletekeyword.html")
    public String deleteKeyword(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String operatorId = request.getParameter("keywordId");
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
    public ModelAndView queryOperatorDo(HttpServletRequest request, String searchWord) throws UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView("keywordList");
        searchWord = new String(searchWord.getBytes(),"UTF-8");
        modelAndView.addObject("list", keyWordService.matchKeyword(searchWord));
        return modelAndView;
    }

}
