package com.book.web;

import com.book.service.ActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ActiveController {

    private ActiveService activeService;

    @Autowired
   private void setActiveService(ActiveService activeService) {
        this.activeService = activeService;
    }

    //显示所有操作记录
    @RequestMapping("/acList.html")
    public ModelAndView acList() {

        ModelAndView modelAndView = new ModelAndView("activerank");
        modelAndView.addObject("list", activeService.acList());
        return modelAndView;
    }

    //升序显示所有操作记录
    @RequestMapping("/acList_asc.html")
    public ModelAndView acList_asc() {

        ModelAndView modelAndView = new ModelAndView("activerank");
        modelAndView.addObject("list", activeService.acList_asc());
        return modelAndView;
    }
    //删除对应操作记录
    @RequestMapping("/deleteac.html")
    public String deleteAc(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String userId = request.getParameter("userId");
        int res = activeService.deleteacList(userId);

        if (res == 1) {
            redirectAttributes.addFlashAttribute("succ", "活跃度排名项删除成功！");
            return "redirect:/acList.html";
        } else {
            redirectAttributes.addFlashAttribute("error", "活跃度排名项删除失败！");
            return "redirect:/acList.html";
        }
    }
    //搜索
    @RequestMapping("/queryactive.html")
    public ModelAndView queryActiveDo(HttpServletRequest request, String searchWord) {
        ModelAndView modelAndView = new ModelAndView("activerank");
        modelAndView.addObject("list", activeService.matchAC(searchWord));
        return modelAndView;
    }

}
