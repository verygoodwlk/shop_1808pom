package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ken
 * @Time 2019/1/23 9:24
 * @Version 1.0
 */
public interface ICartDao extends BaseMapper<Cart> {

    /**
     * 根据用户id查询所有的购物车信息
     * @param uid
     * @return
     */
    List<Cart> queryCartsByUid(Integer uid);


    /**
     * 根据购物车id数组查询相应的购物车信息
     * @param cids
     * @return
     */
    List<Cart> queryCartsByIds(@Param("cids") Integer[] cids);
}
