package com.wq.shop.example.dao.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wangqiang20995
 * @Date: 2020/4/29 16:55
 * @Description:
 **/
@Data
public class Stock implements Serializable {
    private int id;
    private String name;
    private int count;
    private int sale;
    private int version;
}
