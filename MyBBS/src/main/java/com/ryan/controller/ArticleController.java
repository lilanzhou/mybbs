package com.ryan.controller;

import com.alibaba.fastjson.JSONObject;
import com.ryan.po.Article;
import com.ryan.po.Bbsuser;
import com.ryan.po.Message;
import com.ryan.po.PageBean;
import com.ryan.service.ArticleService;
import com.ryan.service.BBSUserService;
import com.ryan.utils.FreemarkerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019:04:07
 *
 * @Author : Lilanzhou
 * 功能 :
 */
@Controller
@RequestMapping(value = {"/article"})
public class ArticleController {
    @Autowired
    private ArticleService service;
    @Autowired
    private BBSUserService  bbsUserService;
    @RequestMapping("/queryz/{cupage}")
    public void queryZT(HttpServletResponse response, HttpServletRequest request,
                        @PathVariable String cupage){
        //判断当前用户是否为游客，如果是默认是5行，不然要取数据库的pagenum条数
        int currentPage=Integer.parseInt(cupage);
        int size=0;
        if(request.getSession().getAttribute("username")==null){//登录成功，设置pageable

            size=5;

        }else {
            int userid=Integer.parseInt(request.getSession().getAttribute("userid").toString());
            size=bbsUserService.getPageNum(userid);
        }
        /**
         * 如果登录的是自己的账号展示个人主贴内容信息
         */

        Sort sort=new Sort(Sort.Direction.DESC,"id");
        Pageable pageable=new PageRequest(currentPage-1,size,sort);
        Page<Article> articlePageList = service.queryz(pageable);
        PageBean page=new PageBean();
        page.setMaxPage(articlePageList.getTotalPages());
        page.setCurPage(currentPage);
        page.setMaxRowCount((int)articlePageList.getTotalElements());
        page.setRowsPerPage(articlePageList.getNumberOfElements());
        page.setData(articlePageList.getContent());

        Map<String,Object> map=new HashMap<>();
        map.put("username",request.getSession().getAttribute("username"));
        map.put("userid",request.getSession().getAttribute("userid"));
        map.put("page",page);

      FreemarkerUtil.forward("show",map,response);
      //  request.getAttribute("")
//        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/article/queryz/1");
//        try {
//            requestDispatcher.forward(request, response);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
    @RequestMapping(value = {"/savez"})
    public void saveZ(@ModelAttribute Article article,HttpServletRequest request,HttpServletResponse response){
        article.setDatatime(new Date(System.currentTimeMillis()));
        article.setRootid(0);

        if(service.savez(article)!=null){
            RequestDispatcher dispatcher=request.getRequestDispatcher("/article/queryz/1");

            try {
                dispatcher.forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @RequestMapping(value = {"/savec"})
    public void saveC(@RequestParam("title")String title,
                      @RequestParam("content")String content,
                      @RequestParam("rootid")int rootid,
                      @RequestParam("userid")int userid,
                      HttpServletRequest request, HttpServletResponse response){
        Article article=new Article();
        article.setTitle(title);
        article.setRootid(rootid);
        article.setContent(content);
        Bbsuser bbsuser=new Bbsuser();
        bbsuser.setUserid(userid);
        article.setBbsuser(bbsuser);
        article.setDatatime(new Date(System.currentTimeMillis()));
        if(service.savez(article)!=null){
            //发消息成功后立刻给主题回馈信息
            service.sendMessage(article);


            /**
             * 刷当前queryc/rootid页
             */
            RequestDispatcher dispatcher=request.getRequestDispatcher("/article/queryc/"+rootid);

            try {
                dispatcher.forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @RequestMapping(value = "/con/{uid}")
    public void consumerMsg(@PathVariable String uid,HttpServletResponse response){
        List<Message> messages = service.consumerMsg(Integer.parseInt(uid));
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        String json = JSONObject.toJSONString(messages);
        try {
            PrintWriter out=response.getWriter();
             out.println(json);
             out.flush();
             out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @RequestMapping(value = {"/delz/{id}"})
    public void delZ(@PathVariable int id,HttpServletRequest request,HttpServletResponse response){
        service.delz(id);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/article/queryz/1");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = {"/delc/{id}/{rootid}"})
    public void delC(@PathVariable int id,@PathVariable int rootid, HttpServletRequest request,HttpServletResponse response){
        service.delc(id);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/article/queryc/"+rootid);
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/queryc/{id}"})
    public void querycByz(@PathVariable int id,HttpServletResponse response){
        Map<String, Object> map = service.querycByz(id);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        String json = JSONObject.toJSONString(map, true);
        try {
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
