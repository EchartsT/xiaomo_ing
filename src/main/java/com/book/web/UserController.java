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
import java.io.File;
import java.io.UnsupportedEncodingException;
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
//用户列表
    @RequestMapping("/userlist.html")
    public ModelAndView allUser(){
        ArrayList<User> users=userService.List();
        ModelAndView modelAndView=new ModelAndView("userlist");
        modelAndView.addObject("users",users);
        return modelAndView;
    }
//删除用户
    @RequestMapping("/deleteUser.html")
    public String deleteUser(HttpServletRequest request,RedirectAttributes redirectAttributes){
        String userId = request.getParameter("userId");
        String isSubscribe = request.getParameter("isSubscribe");
        String filePath = "D:\\apache-tomcat-7.0.84\\bin\\chatData\\" + userId +".txt";
        File file = new File(filePath);
        file.delete();

        if(isSubscribe.equals("false")){
            int success=userService.deleteUserList(userId);
            if(success==1){
                redirectAttributes.addFlashAttribute("succ", "删除成功！");
                return "redirect:/userlist.html";
            }else {
                redirectAttributes.addFlashAttribute("error", "删除失败！");
                return "redirect:/userlist.html";
            }
        }
        redirectAttributes.addFlashAttribute("error", "不能删除订阅用户！");
        return "redirect:/userlist.html";
    }
    @RequestMapping("/queryUser.html")
    public ModelAndView queryUser(HttpServletRequest request,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        String userName =new String(request.getParameter("userName").getBytes("ISO-8859-1"),"utf-8");
        ArrayList<User> userlist = userService.queryList(userName);
        ModelAndView modelAndView=new ModelAndView("userlist");
        modelAndView.addObject("users",userlist);
        return modelAndView;
    }
}
