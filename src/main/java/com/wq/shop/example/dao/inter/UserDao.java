package com.wq.shop.example.dao.inter;

import com.wq.shop.example.dao.bean.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    User getUserById(int id);
}
