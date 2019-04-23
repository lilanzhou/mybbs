package com.ryan.po;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Administrator on 2019:04:06
 *
 * @Author : Lilanzhou
 * 功能 :
 */
@Entity
public class Article {
    private int id;
    private Integer rootid;
    private String title;
    private String content;
    private Date datatime;
    private Bbsuser bbsuser;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "rootid", nullable = true)
    public Integer getRootid() {
        return rootid;
    }

    public void setRootid(Integer rootid) {
        this.rootid = rootid;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content", nullable = true, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "datatime", nullable = true)
    public Date getDatatime() {
        return datatime;
    }

    public void setDatatime(Date datatime) {
        this.datatime = datatime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (id != article.id) return false;
        if (rootid != null ? !rootid.equals(article.rootid) : article.rootid != null) return false;
        if (title != null ? !title.equals(article.title) : article.title != null) return false;
        if (content != null ? !content.equals(article.content) : article.content != null) return false;
        if (datatime != null ? !datatime.equals(article.datatime) : article.datatime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (rootid != null ? rootid.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (datatime != null ? datatime.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    public Bbsuser getBbsuser() {
        return bbsuser;
    }

    public void setBbsuser(Bbsuser bbsuser) {
        this.bbsuser = bbsuser;
    }
}
