package com.ryan.controller;

import com.ryan.po.Bbsuser;
import com.ryan.service.BBSUserService;
import com.ryan.utils.FreemarkerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019:04:06
 *
 * @Author : Lilanzhou
 * 功能 :
 */
@Controller
@RequestMapping(value = {"/user"})
@SessionAttributes
public class UserController {
    @Autowired
    private BBSUserService service;

    public UserController() {

    }

    /**
     * @param response 做相应对象
     * @param request  MultipartHttpServletRequest他继承的是HttpServletRequest 使用他是根据前端的
     *                 上传表单属性值为一个multipart ===enctype="multipart/form-data"
     */
    @RequestMapping("/reg")
    public void reg(HttpServletResponse response, MultipartHttpServletRequest request,
                    HttpServletRequest request1,HttpServletResponse response1) {
        /**
         *SpringMVC上传文件使用MultipartResolver实现
         * 它的两个类为：CommonsMultipartResolver    StandardServletMultipartResolver
         * 区别是第一个需要 fileupload jar包 第二个Servlet3版本以上不需要jar 使用servlet内置功能上传
         */
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getServletContext());
        Bbsuser bbsuser = null;
        if (commonsMultipartResolver.isMultipart(request)) {
            bbsuser = service.uploadPic(request, commonsMultipartResolver);
            service.register(bbsuser);
        }
       // FreemarkerUtil.forward("show", null, response);

        RequestDispatcher requestDispatcher = request1.getRequestDispatcher("/article/queryz/1");
        try {
            requestDispatcher.forward(request1,response1);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = {"/logout"})
    public void loginout(HttpServletResponse response,HttpServletRequest request) {
//      FreemarkerUtil.forward("show", null, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/article/queryz/1");
        request.getSession().removeAttribute("username");
        request.getSession().removeAttribute("userid");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/pic/{userid}"})
    public void pic(@PathVariable Integer userid, HttpServletResponse response) {
        Bbsuser bbsuser = service.pic(userid);
        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(bbsuser.getPic());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     *
     * @param username  前端输入的用户名
     * @param password  用户密码
     * @param sun    是否勾选7天
     * @param response  做响应给前端
     * @param request   请求前端的值
     * @param model
     */
    @RequestMapping(value = {"/login/{username}/{password}/{sun}"})
    public void login( @PathVariable String username,@PathVariable String password,
                       @PathVariable String sun, HttpServletResponse response, HttpServletRequest request, ModelMap model) {
        Bbsuser user=new Bbsuser();
        user.setUsername(username);
        user.setPassword(password);
        Bbsuser bbsuser = service.login(user);
        Map map = new HashMap();
        String flag="0";//代表没有成功
        if (bbsuser != null) {
//
//            map.put("username", bbsuser.getUsername());
//            map.put("userid", bbsuser.getUserid());
            request.getSession().setAttribute("username", bbsuser.getUsername());
            request.getSession().setAttribute("userid", bbsuser.getUserid());

//            request.getSession().setAttribute("sun",bbsuser.getSun());
//            user.setSun(request.getParameter("sun"));
            //处理Cookie
            if (sun.equals("on")) {

//                Cookie cookie = new Cookie("papaoku", user.getUsername());
//                cookie.setMaxAge(3600 * 24 * 7);
//                response.addCookie(cookie);
//                Cookie cookie1 = new Cookie("papaokp", user.getPassword());
//                cookie1.setMaxAge(3600 * 24 * 7);
//                response.addCookie(cookie1);
                flag=String.valueOf(bbsuser.getUserid());//成功后返回 id 到前端 目的做接受信息msgConsumer

            }
            //处理Session
       model.put("user", user);

        }

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");

        try {
            PrintWriter out=response.getWriter();
            out.print(flag);//传用户id到前端
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  FreemarkerUtil.forward("show", map, response);
            //登录后转向当前queryz的内容
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/article/queryz/1");
//            try {
//                requestDispatcher.forward(request, response);
//            } catch (ServletException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

    }
    @RequestMapping(value = {"/row/{userid}/{pagenum}"})
    public void setRowFromPageNum( @PathVariable Integer userid,@PathVariable Integer pagenum,
                                   HttpServletRequest request,HttpServletResponse response){
        service.setPageNum(pagenum,userid);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/article/queryz/1");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
