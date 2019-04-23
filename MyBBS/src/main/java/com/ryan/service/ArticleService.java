package com.ryan.service;

import com.ryan.dao.ArticleDao;
import com.ryan.dao.IArticleDAO;
import com.ryan.po.Article;
import com.ryan.po.Message;
import com.ryan.service.kafka.consumer.MsgConsumer;
import com.ryan.service.kafka.producer.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019:04:07
 *
 * @Author : Lilanzhou
 * 功能 :
 */
@Service
@Transactional
public class ArticleService {
    @Autowired
    private IArticleDAO dao;
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private MsgConsumer cdao;
    @Autowired
    private MsgProducer pdao; //消息生产者

    public Page<Article> queryz(Pageable pageable){
        return dao.queryz(pageable);
    }

    public Article savez(Article article){
        return dao.save(article);
    }
    public void delz(Integer id){
        dao.delz(id);
    }
    public Map<String,Object>querycByz( int id){
        return articleDao.querycByZid(id);
    }


    public void delc(Integer id){
        dao.delc(id);
    }
    public Article findOne(int id){
        return dao.findOne(id);
    }
    public void sendMessage(Article article) {
     pdao.sendMessage(article);
    }
    public List<Message> consumerMsg(Integer id) {
    return cdao.consumerMsg(id);
    }
}
