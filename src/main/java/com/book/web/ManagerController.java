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
        String managerId = request.getParameter("managerId");
        ArrayList<Manager> ma = managerService.searchMa(managerId);
        modelAndView.addObject("list", managerService.searchMa(managerId));
        return modelAndView;
    }
    @RequestMapping("/updatemanager.html")
    public @ResponseBody Object updatemanager(HttpServletRequest request) {
        Manager manager = new Manager();
        manager.setManagerId(request.getParameter("managerId"));
        manager.setManagerName(request.getParameter("managerName"));
        manager.setManagerPwd(request.getParameter("managerPwd"));
        manager.setManagerStatus(request.getParameter("managerStatus"));
//        manager.setManagerId(managerId);
//        manager.setManagerName(managerName);
//        manager.setManagerPwd(managerPwd);
//        manager.setManagerStatus(managerStatus);
       boolean succ = managerService.editMa(manager);
        HashMap<String, String> res = new HashMap<String, String>();
        if (succ){
            //redirectAttributes.addFlashAttribute("succ", "添加成功！");
            //return "redirect:/managerList.html";
            res.put("stateCode", "1");
            res.put("msg","添加成功！");
        }
        else {
            //redirectAttributes.addFlashAttribute("error", "添加失败！");
            // return "redirect:/managerList.html";
            res.put("stateCode", "0");
            res.put("msg","添加失败！");
        }
        return res;
    }

    //增加管理原
    @RequestMapping("/manageradd.html")
    public ModelAndView manageradd(HttpServletRequest request){
        return new ModelAndView("manager_add");
    }

    @RequestMapping("/manageradd_do.html")
    public @ResponseBody Object manageradd_do(HttpServletRequest request, String managerId, RedirectAttributes redirectAttributes){
        Manager manager = new Manager();
//        manager.setManagerId(managerCommand.getManagerId());
//        manager.setManagerName(managerCommand.getManagerName());
//        manager.setManagerPwd(managerCommand.getManagerPwd());
//        manager.setManagerStatus(managerCommand.getManagerStatus());
        manager.setManagerId(request.getParameter("managerId"));
        manager.setManagerName(request.getParameter("managerName"));
        manager.setManagerPwd(request.getParameter("managerPwd"));
        manager.setManagerStatus(request.getParameter("managerStatus"));
        boolean succ = managerService.addMa(manager);
        HashMap<String, String> res = new HashMap<String, String>();
        if (succ){
           //redirectAttributes.addFlashAttribute("succ", "添加成功！");
            //return "redirect:/managerList.html";
            res.put("stateCode", "1");
            res.put("msg","添加成功！");
        }
        else {
            //redirectAttributes.addFlashAttribute("error", "添加失败！");
           // return "redirect:/managerList.html";
            res.put("stateCode", "0");
            res.put("msg","添加失败！");
        }
        return res;
    }


}
