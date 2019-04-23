package com.ryan.dao;

import com.ryan.po.Article;
import com.ryan.po.Bbsuser;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Administrator on 2019:04:10
 *
 * @Author : Lilanzhou
 * 功能 :
 */
@Repository
public class ArticleDao {
    @PersistenceContext
    private EntityManager entityManager;
     public Map<String,Object> querycByZid(@PathVariable int id){
         StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("p_1");
          storedProcedureQuery.registerStoredProcedureParameter(1,Integer.class, ParameterMode.IN);
          storedProcedureQuery.registerStoredProcedureParameter(2, String.class,ParameterMode.OUT);
          storedProcedureQuery.setParameter(1,id);
          storedProcedureQuery.execute();

         List<Object[]> resultList = storedProcedureQuery.getResultList();
            List<Article>list=new ArrayList<>();
            resultList.forEach((o)->{
                Article article=new Article();
                article.setId(Integer.parseInt(o[0].toString()));
                article.setRootid(Integer.parseInt(o[1].toString()));
                article.setTitle(o[2].toString());
                article.setContent(o[3].toString());
                Bbsuser bbsuser=new Bbsuser();
                bbsuser.setUserid(Integer.parseInt(o[4].toString()));
                article.setBbsuser(bbsuser);
                try {
                    Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(o[5].toString());
                     article.setDatatime(new java.sql.Date(utilDate.getTime()));


                } catch (ParseException e) {
                    e.printStackTrace();
                }
              list.add(article);

            });
            String title=storedProcedureQuery.getOutputParameterValue(2).toString();
            Map<String,Object>map=new HashMap<>();
            map.put("list",list);
            map.put("title",title);
            return map;
     }
}
