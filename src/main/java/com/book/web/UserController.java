package com.book.web;

import com.book.domain.ReaderCard;
import com.book.domain.ReaderInfo;
import com.book.domain.User;
import com.book.service.LoginService;
import com.book.service.ReaderCardService;
import com.book.service.ReaderInfoService;
import com.book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class UserController {
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/userlist.html")
    public ModelAndView allBooks(){
        ArrayList<User> users=userService.List();
        ModelAndView modelAndView=new ModelAndView("userlist");
        modelAndView.addObject("users",users);
        return modelAndView;
    }

    @RequestMapping("/deleteUser.html")
    public String readerDelete(HttpServletRequest request,RedirectAttributes redirectAttributes){
        String userId = request.getParameter("userId");
        int success=userService.deleteUserList(userId);
        if(success==1){
            redirectAttributes.addFlashAttribute("succ", "删除成功！");
            return "redirect:/userlist.html";
        }else {
            redirectAttributes.addFlashAttribute("error", "删除失败！");
            return "redirect:/userlist.html";
        }
    }
    @RequestMapping("/queryUser.html")
    public ModelAndView queryUser(HttpServletRequest request,RedirectAttributes redirectAttributes){
        String userName = request.getParameter("userName");
        ArrayList<User> userlist = userService.queryList(userName);
        ModelAndView modelAndView=new ModelAndView("userlist");
        modelAndView.addObject("users",userlist);
        return modelAndView;
    }
}
