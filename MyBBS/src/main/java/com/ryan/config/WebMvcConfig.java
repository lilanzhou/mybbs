package com.ryan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2019:04:06
 *
 * @Author : Lilanzhou
 * 功能 :
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //当访问主页的时候转向自己写的功能页Controller
        registry.addViewController("/").setViewName("forward:/welcome/index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);//设置最高级
        super.addViewControllers(registry);
    }

    //放行static下的东西
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true);
    }
}
