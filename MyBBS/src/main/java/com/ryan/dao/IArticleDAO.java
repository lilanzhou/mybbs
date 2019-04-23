package com.ryan.dao;

import com.ryan.po.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019:04:07
 *
 * @Author : Lilanzhou
 * 功能 :
 */
public interface IArticleDAO extends CrudRepository<Article,Integer> {
    @Query("select a from Article a where a.rootid=0")
    public Page<Article> queryz(Pageable pageable);

    @Override
     public  Article save(Article article);
    @Modifying
    @Query("delete from Article where id=:id or rootid=:id")
    public int delz(@Param("id")Integer id);

    @Query("select c from Article c where id=:id or rootid=:id")
    public Map<String,Object> querycByz(@Param("id") Integer id);

    @Modifying
    @Query("delete from Article where id=:id")
    public int delc(@Param("id")Integer id);

    @Query("select a from Article a where id=:id")
    public Article findOne(@Param("id") Integer id);//根据从贴id找到主贴的详细信息

}
