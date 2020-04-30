package com.wq.shop.example.dao.inter;

import com.wq.shop.example.dao.bean.Stock;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDao {

    void insertStock(Stock stock);

    Stock getStockById(int id);

    int updateStockById(Stock stock);

    int updateStockByOptimisticLock(Stock stock);

    Stock selectStockForUpdate(int id);
}
