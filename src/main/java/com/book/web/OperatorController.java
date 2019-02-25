package com.book.web;

import com.book.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import javax.servlet.*;
import javax.servlet.ServletRequest;
import java.util.ArrayList;

@Controller
public class OperatorController {
   private OperatorService operatorService;

   @Autowired
   private void setOperatorService(OperatorService operatorService) {
      this.operatorService = operatorService;
   }

   //显示所有操作记录
   @RequestMapping("/opList.html")
   public ModelAndView lendList() {

      ModelAndView modelAndView = new ModelAndView("operatorList");
      modelAndView.addObject("list", operatorService.opList());
      return modelAndView;
   }

   //删除对应操作记录
   @RequestMapping("/deleteop.html")
   public String deleteOp(HttpServletRequest request, RedirectAttributes redirectAttributes) {
      String operatorId = request.getParameter("operatorId");
      int res = operatorService.deleteopList(operatorId);

      if (res == 1) {
         redirectAttributes.addFlashAttribute("succ", "操作流水记录删除成功！");
         return "redirect:/opList.html";
      } else {
         redirectAttributes.addFlashAttribute("error", "操作流水记录删除失败！");
         return "redirect:/opList.html";
      }
   }

   //搜索
   @RequestMapping("/queryoperater.html")
   public ModelAndView queryOperatorDo(HttpServletRequest request, String searchWord) {
      ModelAndView modelAndView = new ModelAndView("operatorList");
      modelAndView.addObject("list", operatorService.matchOP(searchWord));
      return modelAndView;
   }

   // 设定输出的类型
   private static final String TXT = "application/txt;charset=UTF-8";

   //打开文件
   @RequestMapping("/openfile.html")
   public void openFile(HttpServletRequest request, HttpServletResponse response )throws IOException {
      String fileName = request.getParameter("fileName");
      String filePath = "D:\\apache-tomcat-7.0.84\\bin\\chatData\\" + fileName;
      response.setContentType("text/html;charset=GBK");

      InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "GBK");
      BufferedReader br = new BufferedReader(isr);
      PrintWriter out = response.getWriter();
      String line = "";
      while ((line = br.readLine())!= null){
         out.println(line);
      }
      br.close();
      out.flush();
      out.close();
//      StringBuffer filecontent = new StringBuffer("");
//      String strtemp = null;
//      strtemp = br.readLine();
//      while (strtemp!=null){
//         filecontent.append(strtemp);
//         filecontent.append("\n");
//         strtemp = br.readLine();
//      }



//      // 文件流
//      InputStream is = null;
//      // 输入缓冲流
//      BufferedInputStream bis = null;
//      // 得到输出流
//      OutputStream output = null;
//      // 输出缓冲流
//      BufferedOutputStream bos = null;
//      // 2.设置响应数据字符集
//      response.setCharacterEncoding("UTF-8");
//      // 4.重置response
//      response.reset();
//      // 5.设置响应数据格式
//      response.setContentType(TXT);
//      //String filePath = "WEB-INF/operatorFiles/" + fileName;
//      // 获取当前web应用程序
//      //ServletContext webApp = this.getServletContext();
//      // 6.获取指定文件上传的真实路径
//      // filePath = webApp.getRealPath(filePath);
//
//      // 7.读取目标文件，通过response将目标文件写到客户端
//      try {
//         is = new FileInputStream(filePath);
//      } catch (FileNotFoundException e) {
//         e.printStackTrace();
//      }
//      bis = new BufferedInputStream(is);
//      try {
//         output = response.getOutputStream();
//      } catch (IOException e) {
//         e.printStackTrace();
//      }
//      bos = new BufferedOutputStream(output);
//      byte data[] = new byte[1024];// 缓冲字节数
//      int size = bis.read(data);
//      while (size != -1) {
//         bos.write(data, 0, size);
//         size = bis.read(data);
//      }
//      // 关闭流
//      bis.close();
//      bos.flush();// 清空输出缓冲流
//      bos.close();
//      output.close();
   }

}
