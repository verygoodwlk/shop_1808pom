package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

/**
 * @Author ken
 * @Time 2019/1/17 15:15
 * @Version 1.0
 */
public interface ISearchService {

    /**
     * 根据关键字查询索引库并且返回商品列表
     * @param keyword
     * @return
     */
    List<Goods> queryByIndexed(String keyword);

    int insertIndexed(Goods goods);
}
