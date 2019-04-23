package com.ryan.po;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019:04:08
 *
 * @Author : Lilanzhou
 * 功能 :
 */
public class PageBean implements Serializable{
    private int curPage;//当前页
    private int maxPage;//最大页
    private int maxRowCount;//最大行数
    private int rowsPerPage;//每页多少行
    private List<Article> data; //article数据

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getMaxRowCount() {
        return maxRowCount;
    }

    public void setMaxRowCount(int maxRowCount) {
        this.maxRowCount = maxRowCount;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public List<Article> getData() {
        return data;
    }

    public void setData(List<Article> data) {
        this.data = data;
    }
}
