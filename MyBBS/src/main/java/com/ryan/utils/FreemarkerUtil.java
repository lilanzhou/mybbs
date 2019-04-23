package com.ryan.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Administrator on 2019:04:06
 *
 * @Author : Lilanzhou
 * 功能 :
 */
public class FreemarkerUtil {
    private static Configuration configuration;

    /**
     * 创建configuration
     * 整个生命周期之，这个工作只做一次
     *
     * @return
     */
    private static Configuration buildConfiguration() {
        if (null == configuration) {
            configuration = new Configuration(Configuration.VERSION_2_3_23);
            String path = FreemarkerUtil.class.getResource("/").getPath();
            File ftlPathDir = new File(path + "templates");
            try {
                configuration.setDirectoryForTemplateLoading(ftlPathDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return configuration;
        }
        return configuration;
    }

    /**
     * 获取Template
     *
     * @param fltName
     * @return
     */
    private static Template getTemplate(String fltName) {
        Template template = null;
        try {
            template = buildConfiguration().getTemplate(fltName+".ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return template;
    }

    /**
     * @param fltName 要转向的页ftl
     * @param map  后端往前端传的值
     * @param response 响应对象
     */
    public static void forward(String fltName, Map map, HttpServletResponse response) {

        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();
            Template template = getTemplate(fltName);
            template.process(map,writer);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
