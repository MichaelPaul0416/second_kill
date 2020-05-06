package com.wq.shop.example.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.wq.shop.example.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author: wangqiang20995
 * @Date: 2020/4/29 17:29
 * @Description:
 **/
@RestController
@RequestMapping("/stock")
public class StockController {

    private final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    // 令牌桶，每秒放10个令牌
    private RateLimiter rateLimiter = RateLimiter.create(10);

    /**
     * 裸发，什么保护都没有，直接1000个请求发到server
     *
     * @param sid
     * @return
     */
    @RequestMapping("/createWrongOrder/{sid}")
    public String createWrongOrder(@PathVariable int sid) {
        logger.info("购买商品编号:{}", sid);
        int id = 0;
        try {
            id = stockService.createWrongOrder(sid);
            logger.info("创建订单id: [{}]", id);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        return String.valueOf(id);
    }

    /**
     * 使用数据库的乐观锁优化
     *
     * @param sid
     * @return
     */
    @RequestMapping("/createOptimisticOrder/{sid}")
    public String createOptimisticOrder(@PathVariable int sid) {
        int id;
        try {
            id = stockService.createOptimisticStock(sid);
            logger.info("购买成功，剩余库存为: [{}]", id);
        } catch (Exception e) {
            logger.error("购买失败：[{}]", e.getMessage());
            return "购买失败，库存不足";
        }
        return String.format("购买成功，剩余库存为：%d", id);
    }

    /**
     * 基于乐观锁的前提上，结合令牌桶
     *
     * @param sid
     * @return
     */
    @RequestMapping("/createWithRateLimiter/{sid}")
    public String createWithRateLimiter(@PathVariable int sid) {

        // 阻塞式获取令牌
//        rateLimiter.tryAcquire();

        // 非阻塞式获取令牌
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            logger.info("尚未获取到令牌，下单失败，直接返回");
            return "no token";
        }

        int left = 0;
        try {
            left = stockService.createOptimisticStock(sid);
            logger.info("购买成功，剩余库存为: [{}]", left);
        } catch (Exception e) {
            logger.error("购买失败：[{}]", e.getMessage());
            return "购买失败，库存不足";
        }
        return String.format("购买成功，剩余库存为：%d", left);
    }

    /**
     * 悲观锁抢购
     *
     * @param sid
     * @return
     */
    @RequestMapping("/createWithPessimisticLock/{sid}")
    public String createWithPessimisticLock(@PathVariable int sid) {
        int id;
        try {
            id = stockService.createPessimisticOrder(sid);
            logger.info("购买成功，剩余库存为: [{}]", id);
        } catch (Exception e) {
            logger.error("购买失败：[{}]", e.getMessage());
            return "购买失败，库存不足";
        }
        return String.format("购买成功，剩余库存为：%d", id);
    }

    /**
     * 防止机器刷单，接口预先生成一个hash值用于后面的下单
     *
     * @param userId
     * @param sid
     * @return
     */
    @RequestMapping("/generateVerifyHash")
    public String generateVerifyHash(int userId, int sid) {
        try {
            return stockService.getVerifyHash(sid, userId);
        } catch (Throwable e) {
            logger.error("生成hash值失败:{}", e.getMessage(), e);
            return "服务异常，生成hash值失败";
        }

    }

    @RequestMapping(value = "createWithHashCode", method = RequestMethod.POST)
    public String createWithHashCode(int sid, int userId, String hash) {

    }
}