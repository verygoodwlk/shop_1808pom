package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.IGoodsDao;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import com.qf.service.ISearchService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

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

        //通知详情工程生成静态页 - 提供方
        //将goods对象发送到消息队列中
        rabbitTemplate.convertAndSend("goods_exchange", "", goods);
        return goods;
    }
}
