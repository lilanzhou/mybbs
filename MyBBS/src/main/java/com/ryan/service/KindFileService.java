package com.ryan.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2019:04:09
 *
 * @Author : Lilanzhou
 * 功能 :
 */
@Service
public class KindFileService {
    private Map<String,String> types=new HashMap<>();
    public KindFileService(){
        //允许上传的文件类型
        types.put("image/jpeg", ".jpg");
        types.put("image/gif", ".gif");
        types.put("image/x-ms-bmp", ".bmp");
        types.put("image/png", ".png");
    }
    public String uploadFile(MultipartHttpServletRequest request, CommonsMultipartResolver commonsMultipartResolver){

        commonsMultipartResolver.setDefaultEncoding("utf-8");//上传头像的文件名可以是中文
        commonsMultipartResolver.setResolveLazily(true);
        commonsMultipartResolver.setMaxInMemorySize(1024*1024*4);//设置缓冲
        commonsMultipartResolver.setMaxUploadSizePerFile(1024*1024);//设置每个文件大小
        commonsMultipartResolver.setMaxUploadSize(1024*1024*2);//设置最多能上传文件的大小

        MultipartFile  multipartFile= request.getFile("imgFile");
        String fileType = types.get(multipartFile.getContentType());

        UUID uuid=UUID.randomUUID();
        String path=KindFileService.class.getClassLoader().getResource("").getPath();//取得当前文件路径src
        String dir=request.getParameter("dir");
        String filePath=path+"static/editor/upload/"+dir+"/"+uuid.toString()+fileType;
        File file = new File(filePath);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("error",0);
        jsonObject.put("url","/static/editor/upload/"+dir+"/"+uuid.toString()+fileType);

     return jsonObject.toJSONString();
    }
}
