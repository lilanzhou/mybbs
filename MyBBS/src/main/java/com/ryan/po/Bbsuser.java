package com.ryan.po;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2019:04:06
 *
 * @Author : Lilanzhou
 * 功能 :
 */
@Entity
public class Bbsuser {
    private int userid;
    private String reusername;
    private String repassword;
    private byte[] pic;
    private Integer pagenum;
    private String picPath;
    private Set<Article> articles=new HashSet<>();
    private  String sun;

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    @Id
    @Column(name = "userid", nullable = false)
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "username", nullable = true, length = 20)
    public String getUsername() {
        return reusername;
    }

    public void setUsername(String username) {
        this.reusername = username;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 20)
    public String getPassword() {
        return repassword;
    }

    public void setPassword(String password) {
        this.repassword = password;
    }

    @Basic
    @Column(name = "pic", nullable = true)
    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    @Basic
    @Column(name = "pagenum", nullable = true)
    public Integer getPagenum() {
        return pagenum;
    }

    public void setPagenum(Integer pagenum) {
        this.pagenum = pagenum;
    }

    @Basic
    @Column(name = "pic_path", nullable = true, length = 255)
    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bbsuser bbsuser = (Bbsuser) o;

        if (userid != bbsuser.userid) return false;
        if (reusername != null ? !reusername.equals(bbsuser.reusername) : bbsuser.reusername != null) return false;
        if (repassword != null ? !repassword.equals(bbsuser.repassword) : bbsuser.repassword != null) return false;
        if (!Arrays.equals(pic, bbsuser.pic)) return false;
        if (pagenum != null ? !pagenum.equals(bbsuser.pagenum) : bbsuser.pagenum != null) return false;
        if (picPath != null ? !picPath.equals(bbsuser.picPath) : bbsuser.picPath != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userid;
        result = 31 * result + (reusername != null ?reusername.hashCode() : 0);
        result = 31 * result + (repassword != null ? repassword.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(pic);
        result = 31 * result + (pagenum != null ? pagenum.hashCode() : 0);
        result = 31 * result + (picPath != null ? picPath.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "bbsuser")
    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }
}
