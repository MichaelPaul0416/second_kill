package com.wq.shop.example.controller;

import com.wq.shop.example.dao.bean.Stock;
import com.wq.shop.example.dao.inter.StockDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wangqiang20995
 * @Date: 2020/4/29 17:04
 * @Description:
 **/
@RestController
@RequestMapping("/simple")
public class SimpleController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StockDao stockDao;


    @RequestMapping("/insert/stock")
    public Stock insertStock(){
        Stock stock = new Stock();
        stock.setId(1);
        stock.setCount(10);
        stock.setName("kiko-kika");
        stock.setSale(0);
        stock.setVersion(1);

        stockDao.insertStock(stock);

        return stock;
    }
}
