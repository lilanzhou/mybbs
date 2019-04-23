package com.ryan.po;

import java.io.Serializable;

/**
 * Created by Administrator on 2019:04:20
 *
 * @Author : Lilanzhou
 * 功能 :
 */
public class Message implements Serializable {

    private String zid;
    private String title;
    private String rid;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
