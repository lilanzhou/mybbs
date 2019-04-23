package com.ryan.service.kafka.producer;

import com.alibaba.fastjson.JSONObject;
import com.ryan.po.Article;
import com.ryan.po.Message;
import com.ryan.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2019:04:20
 *
 * @Author : Lilanzhou
 * 功能 :
 */
@Component
public class MsgProducer {

    @Resource
    private KafkaTemplate<String,String >kafkaTemplate;
    @Autowired
    private ArticleService articleService;
    public void sendMessage(Article article){
        Message message=new Message();
        Article zt = articleService.findOne(article.getRootid());//找到发回帖的用户id
        message.setZid(String.valueOf(zt.getBbsuser().getUserid()));//当前发主题的用户id
        message.setTitle(article.getTitle());
        message.setRid(String.valueOf(article.getBbsuser().getUserid()));//发从贴id
        String topic="reply"+message.getZid();
        kafkaTemplate.send(topic, JSONObject.toJSONString(message));

    }
}
