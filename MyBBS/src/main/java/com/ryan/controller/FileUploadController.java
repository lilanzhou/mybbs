package com.ryan.controller;

import com.ryan.service.KindFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2019:04:09
 *
 * @Author : Lilanzhou
 * 功能 :
 */
@Controller
@RequestMapping(value = {"/kind"})
public class FileUploadController {
    @Autowired
    KindFileService service;
    @RequestMapping(value = {"/up"})
    public void kindUpload(MultipartHttpServletRequest request, HttpServletResponse response){
        String str="";
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(request.getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
           str=service.uploadFile(request,commonsMultipartResolver);
        }
        PrintWriter out=null;
        try {
            out=response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(str);
        out.flush();
        out.close();
    }
}
