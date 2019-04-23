package com.ryan.service;

import com.ryan.dao.IUserDAO;
import com.ryan.po.Bbsuser;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Created by Administrator on 2019:04:06
 *
 * @Author : Lilanzhou
 * 功能 :
 */
@Service
@Transactional
public class BBSUserService {
    @Autowired
    private  IUserDAO dao;
    private Map<String,String> types=new HashMap<>();
    public BBSUserService(){
        //允许上传的文件类型
        types.put("image/jpeg", ".jpg");
        types.put("image/gif", ".gif");
        types.put("image/x-ms-bmp", ".bmp");
        types.put("image/png", ".png");
    }
    public int getPageNum( Integer userid){
        return dao.getPageNum(userid);
    }
    public Bbsuser login(Bbsuser user){
         return dao.login(user.getUsername(),user.getPassword());
    }
    public void register(Bbsuser bbsuser){
        dao.save(bbsuser);
    }
    public Bbsuser pic(Integer userid){
        return dao.GetPicId(userid);

    }
    public int setPageNum(Integer pageNum,Integer userid){
        return dao.setPageNum(pageNum,userid);

    }
    public Bbsuser uploadPic(MultipartHttpServletRequest request,
                             CommonsMultipartResolver commonsMultipartResolver){

        commonsMultipartResolver.setDefaultEncoding("utf-8");//上传头像的文件名可以是中文
        commonsMultipartResolver.setResolveLazily(true);
        commonsMultipartResolver.setMaxInMemorySize(1024*1024*4);//设置缓冲
        commonsMultipartResolver.setMaxUploadSizePerFile(1024*1024);//设置每个文件大小
        commonsMultipartResolver.setMaxUploadSize(1024*1024*2);//设置最多能上传文件的大小

        MultipartFile multipartFile=request.getFile("file0");//d读取头像的文件域

        String fileType=types.get(multipartFile.getContentType());//取出头像的文件类型


        UUID uuid= UUID.randomUUID();
        String picPath="d:/P/upload/";
        File file=new File("d:/P/upload/"+uuid+fileType);//新头像文件对象
        Bbsuser user=new Bbsuser();
        try {
            multipartFile.transferTo(file);//传输头像
            //注册

            user.setUsername(request.getParameter("reusername"));
            user.setPassword(request.getParameter("repassword"));

            user.setPagenum(5);//默认是每页数据5个
            //byte lob存储 char lob
            FileInputStream fis=new FileInputStream(file);//读
            byte[] buffer=new byte[fis.available()];//开辟该文件大小的缓冲区bbbs
            fis.read(buffer);//把头像读到缓冲区内
            user.setPic(buffer);
            user.setPicPath(picPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  System.out.println("ok");
        return user;
    }
}
