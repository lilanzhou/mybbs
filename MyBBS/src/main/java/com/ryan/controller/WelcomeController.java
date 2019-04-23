package com.ryan.controller;

import com.ryan.utils.FreemarkerUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019:04:06
 *
 * @Author : Lilanzhou
 * 功能 :
 */
@Controller
@RequestMapping(value = {"/welcome"})
public class WelcomeController {
    @RequestMapping(value = {"/index"})
    public void index(HttpServletResponse response, HttpServletRequest request){
      //  System.out.println("ok");
        Map map=new HashMap();
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/article/queryz/1");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       // FreemarkerUtil.forward("show",map,response);

    }
}
