package com.wq.shop.example.dao.inter;

import com.wq.shop.example.dao.bean.StockOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface StockOrderDao {

    int insertStockOrder(StockOrder stockOrder);
}
