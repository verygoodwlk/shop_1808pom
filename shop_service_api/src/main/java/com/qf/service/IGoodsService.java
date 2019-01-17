package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

/**
 * @Author ken
 * @Time 2019/1/16 10:30
 * @Version 1.0
 */
public interface IGoodsService {

    List<Goods> queryAll();

    Goods insert(Goods goods);
}
