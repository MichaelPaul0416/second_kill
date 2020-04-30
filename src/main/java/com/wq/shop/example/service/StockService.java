package com.wq.shop.example.service;

import com.wq.shop.example.Constant.CommonConstants;
import com.wq.shop.example.dao.bean.Stock;
import com.wq.shop.example.dao.bean.StockOrder;
import com.wq.shop.example.dao.inter.StockDao;
import com.wq.shop.example.dao.inter.StockOrderDao;
import com.wq.shop.example.exception.KillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: wangqiang20995
 * @Date: 2020/4/29 17:31
 * @Description:
 **/
@Service
public class StockService {

    private final Logger logger = LoggerFactory.getLogger(StockService.class);

    @Autowired
    private StockDao stockDao;

    @Autowired
    private StockOrderDao stockOrderDao;

    /**
     * 啥都不用，直接暴露接口
     *
     * @param sid
     * @return
     * @throws Exception
     */
    public int createWrongOrder(int sid) throws Exception {
        //校验库存
        Stock stock = checkStock(sid);
        //扣库存
        int i = saleStock(stock);
        //创建订单
        int id = createOrder(stock, i > 0 ? CommonConstants.ORDER_SUCCESS : CommonConstants.ORDER_FAILED);
        return id;
    }


    /**
     * 使用乐观锁
     *
     * @param sid
     * @return
     */
    public int createOptimisticStock(int sid) {
        Stock stock = checkStock(sid);
        saleStockOptimistic(stock);
        int it = createOrder(stock, CommonConstants.ORDER_SUCCESS);
        return stock.getCount() - (stock.getSale() + 1);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int createPessimisticOrder(int sid) {
        // get for update
        Stock stock = stockDao.selectStockForUpdate(sid);
        if (stock.getSale() >= stock.getCount()) {
            throw new KillException("9999", "库存不足");
        }

        stock.setSale(stock.getSale() + 1);
        stockDao.updateStockById(stock);

        int id = createOrder(stock, CommonConstants.ORDER_SUCCESS);
        return stock.getCount() - stock.getSale();


    }

    private void saleStockOptimistic(Stock stock) {
        logger.info("查询数据库，尝试更新库存");
        int count = stockDao.updateStockByOptimisticLock(stock);
        if (count == 0) {
            throw new KillException("9999", "获取购买资格失败，version版本不一致");
        }
    }

    /**
     * 销量大于等于库存的时候报错
     *
     * @param sid
     * @return
     */
    private Stock checkStock(int sid) {
        Stock stock = stockDao.getStockById(sid);
        if (stock.getSale() >= stock.getCount()) {
            throw new RuntimeException("库存不足");
        }
        return stock;
    }

    private int saleStock(Stock stock) {
        stock.setSale(stock.getSale() + 1);
        return stockDao.updateStockById(stock);
    }

    private int createOrder(Stock stock, int status) {
        StockOrder order = new StockOrder();
        order.setSid(stock.getId());
        order.setName(stock.getName());
        order.setOrderStatus(status);
        int id = stockOrderDao.insertStockOrder(order);
        return id;
    }
}
