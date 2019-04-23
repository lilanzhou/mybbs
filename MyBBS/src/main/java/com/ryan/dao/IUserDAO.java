package com.ryan.dao;

import com.ryan.po.Article;
import com.ryan.po.Bbsuser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


/**
 * Created by Administrator on 2019:04:06
 *
 * @Author : Lilanzhou
 * 功能 :
 */

public interface IUserDAO extends CrudRepository<Bbsuser,Integer>{
    @Query("select c from Bbsuser c where c.username=:u and c.password=:p")
    public Bbsuser login(@Param("u")String username,@Param("p")String password);
    public Bbsuser save(Bbsuser bbsuser);
    @Query("select c from Bbsuser c where c.userid=:id")
    public Bbsuser GetPicId(@Param("id")Integer userid);

    @Modifying
    @Query("update Bbsuser b set b.pagenum=:num where b.userid=:id")
    public int setPageNum(@Param("num") Integer pagenum,@Param("id") Integer userid);

    @Query("select pagenum from Bbsuser c where c.userid=:id ")
    public int getPageNum(@Param("id") Integer userid);
}
