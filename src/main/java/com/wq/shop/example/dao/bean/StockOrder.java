package com.wq.shop.example.dao.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wangqiang20995
 * @Date: 2020/4/29 16:56
 * @Description:
 **/
@Data
public class StockOrder implements Serializable {
    private int id;
    private int sid;
    private String name;
    private String createTime;

    private int orderStatus;
}
