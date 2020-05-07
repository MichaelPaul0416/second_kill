package com.wq.shop.example.dao.inter;

import com.wq.shop.example.dao.bean.OrderUser;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderUserDao {

    void insertOrderUser(OrderUser orderUser);
}
