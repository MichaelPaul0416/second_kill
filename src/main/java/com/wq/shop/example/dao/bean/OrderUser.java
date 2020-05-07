package com.wq.shop.example.dao.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wangqiang20995
 * @Date: 2020/5/7 9:31
 * @Description:
 **/
@Data
public class OrderUser implements Serializable {
    private int serialNo;
    private int userId;
    private String createTime;
}
