package com.book.web;


import com.book.domain.Manager;
import com.book.service.ManagerService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class ManagerController {
    ManagerService managerService;

    @Autowired
    public void setManagerService(ManagerService managerService){
        this.managerService=managerService;
    }

    @RequestMapping("/managerlist.html")
    public ModelAndView managerList() {

        ModelAndView modelAndView = new ModelAndView("managerlist");
        modelAndView.addObject("list", managerService.managerList());
        return modelAndView;
    }
    //删除对应操作记录
    @RequestMapping("/deletemanager.html")
    public String deleteMa(HttpServletRequest request, RedirectAttributes redirectAttributes) {
       String managerId = request.getParameter("managerId");

       int res = managerService.deletemaList(managerId);

        if (res == 1) {
            redirectAttributes.addFlashAttribute("succ", "操作流水记录删除成功！");
            return "redirect:/managerlist.html";
        } else {
            redirectAttributes.addFlashAttribute("error", "操作流水记录删除失败！");
            return "redirect:/managerlist.html";
        }
    }

    //搜索
    @RequestMapping("/querymanager.html")
    public ModelAndView queryOperatorDo(HttpServletRequest request) throws UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView("managerlist");
        String searchWord = new String(request.getParameter("searchWord").getBytes("ISO-8859-1"),"utf-8");
        modelAndView.addObject("list", managerService.matchMa(searchWord));
        return modelAndView;
    }

    //更改
    @RequestMapping("/updatemanager_info.html")
    public ModelAndView updatemanager_info(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("updatemanager");
        String managerId = request.getParameter("managerId");
        Manager ma = managerService.searchMa(managerId);
        modelAndView.addObject("alog", ma);
        return modelAndView;
    }
    @RequestMapping("/updatemanager.html")
    public ModelAndView updatemanager(HttpServletRequest request) throws UnsupportedEncodingException {
        Manager manager = new Manager();
        manager.setManagerId(request.getParameter("managerId"));
        String managerName = new String(request.getParameter("managerName").getBytes("ISO-8859-1"),"utf-8");
        String managerStatus = new String(request.getParameter("managerStatus").getBytes("ISO-8859-1"),"utf-8");
        manager.setManagerName(managerName);
        manager.setManagerPwd(request.getParameter("managerPwd"));
        manager.setManagerStatus(managerStatus);
        boolean flag = managerService.editMa(manager);
        ModelAndView modelAndView = new ModelAndView("managerlist");
        modelAndView.addObject("list", managerService.managerList());
        if (flag){
            modelAndView.addObject("succ", "修改成功！");

        }else {
            modelAndView.addObject("error", "修改失败！");
        }
        return modelAndView;

    }

    //增加管理原
    @RequestMapping("/manageradd.html")
    public ModelAndView manageradd(HttpServletRequest request){
        return new ModelAndView("manager_add");
    }

    @RequestMapping("/manageradd_do.html")
    public ModelAndView manageradd_do(HttpServletRequest request, String managerId, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        Manager manager = new Manager();
//        manager.setManagerId(managerCommand.getManagerId());
//        manager.setManagerName(managerCommand.getManagerName());
//        manager.setManagerPwd(managerCommand.getManagerPwd());
//        manager.setManagerStatus(managerCommand.getManagerStatus());
        manager.setManagerId(request.getParameter("managerId"));
        String managerName = new String(request.getParameter("managerName").getBytes("ISO-8859-1"),"utf-8");
        String managerStatus = new String(request.getParameter("managerStatus").getBytes("ISO-8859-1"),"utf-8");
        manager.setManagerName(managerName);
        manager.setManagerPwd(request.getParameter("managerPwd"));
        manager.setManagerStatus(managerStatus);
        ModelAndView modelAndView = new ModelAndView("managerlist");
        boolean flag = managerService.addMa(manager);
//        ModelAndView modelAndView = new ModelAndView("managerlist");
        modelAndView.addObject("list", managerService.managerList());
        if(flag){
            modelAndView.addObject("succ", "添加成功！");
        }else {
            modelAndView.addObject("error", "添加失败！");

        }
        return modelAndView;
    }


}
