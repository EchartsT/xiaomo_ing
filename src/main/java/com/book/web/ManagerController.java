package com.book.web;


import com.book.domain.Manager;
import com.book.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

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
       // String managerId = request.getParameter("managerId");
        int res = managerService.deletemaList("managerId");

        if (res == 1) {
            redirectAttributes.addFlashAttribute("succ", "操作流水记录删除成功！");
            return "redirect:/managerList.html";
        } else {
            redirectAttributes.addFlashAttribute("error", "操作流水记录删除失败！");
            return "redirect:/managerList.html";
        }
    }

    //搜索
    @RequestMapping("/querymanager.html")
    public ModelAndView queryOperatorDo(HttpServletRequest request, String searchWord) {
        ModelAndView modelAndView = new ModelAndView("managerlist");
        modelAndView.addObject("list", managerService.matchMa(searchWord));
        return modelAndView;
    }
    //更改
    @RequestMapping("/updatemanager_info.html")
    public ModelAndView updatemanager_info(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("updatemanager");
        modelAndView.addObject("list", managerService.searchMa("managerId"));
        return modelAndView;
    }
    @RequestMapping("/updatemanager.html")
    public String updatemanager(HttpServletRequest request,Manager managercommand,RedirectAttributes redirectAttributes) {
       String managerId = request.getParameter("managerId");
       Manager manager = new Manager();
       manager.setManagerId(managerId);
       manager.setManagerName(managercommand.getManagerName());
       manager.setManagerPwd(managercommand.getManagerPwd());
       manager.setManagerStatus(managercommand.getManagerStatus());
       boolean succ = managerService.editMa(manager);
        if (succ){
            redirectAttributes.addFlashAttribute("succ", "图书修改成功！");
            return "redirect:/managerList.html";
        }
        else {
            redirectAttributes.addFlashAttribute("error", "图书修改失败！");
            return "redirect:/managerList.html";
        }
    }

//    //增加读者
//    @RequestMapping("/manageradd.html")
//    public ModelAndView manageradd(HttpServletRequest request){
//
//
//    }


}
