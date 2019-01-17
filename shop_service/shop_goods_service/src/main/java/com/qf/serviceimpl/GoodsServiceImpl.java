package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.IGoodsDao;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import com.qf.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ken
 * @Date 2019/1/16
 * @Version 1.0
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private IGoodsDao goodsDao;

    @Reference
    private ISearchService searchService;

    @Override
    public List<Goods> queryAll() {
        return goodsDao.selectList(null);
    }

    @Override
    @Transactional
    public Goods insert(Goods goods) {
        //保存进数据库
        goodsDao.insert(goods);

        //将商品信息同步到索引库中
        searchService.insertIndexed(goods);

        return goods;
    }
}
