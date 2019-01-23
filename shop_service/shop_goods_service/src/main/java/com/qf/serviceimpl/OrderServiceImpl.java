package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.IAddressDao;
import com.qf.dao.ICartDao;
import com.qf.dao.IOrderDao;
import com.qf.dao.IOrderDetilsDao;
import com.qf.entity.*;
import com.qf.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author ken
 * @Date 2019/1/23
 * @Version 1.0
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IAddressDao addressDao;

    @Autowired
    private ICartDao cartDao;

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private IOrderDetilsDao orderDetilsDao;

    /**
     * 添加订单
     * @param aid
     * @param cids
     * @return
     */
    @Override
    @Transactional
    public int addOrder(Integer aid, Integer[] cids, User user) {

        //根据收货地址的id，查询收货地址详细信息
        QueryWrapper qw = new QueryWrapper();
        qw.eq("id", aid);
        Address address = addressDao.selectOne(qw);

        //根据购物车id，查询当前购物车的详细信息
        List<Cart> clist = cartDao.queryCartsByIds(cids);

        //计算订单总价
        double allprice = 0;
        for (Cart cart : clist) {
            allprice += cart.getGnumber() * cart.getGoods().getPrice();
        }

        //根据收货地址生成订单对象，并且保存订单
        Orders orders = new Orders();
        orders.setOrderid(UUID.randomUUID().toString());
        orders.setUid(user.getId());
        orders.setPerson(address.getPerson());
        orders.setAddress(address.getAddress());
        orders.setCode(address.getCode());
        orders.setOrdertime(new Date());
        orders.setPhone(address.getPhone());
        orders.setStatus(0);
        orders.setOprice(allprice);//订单总价

        orderDao.insert(orders);

        //根据购物车详情，生成订单详情，循环插入到数据库中
        for (Cart cart : clist) {
            OrderDetils orderDetils = new OrderDetils();
            orderDetils.setOid(orders.getId());
            orderDetils.setGid(cart.getGid());
            orderDetils.setGname(cart.getGoods().getTitle());
            orderDetils.setGinfo(cart.getGoods().getGinfo());
            orderDetils.setPrice(cart.getGoods().getPrice());
            orderDetils.setGimage(cart.getGoods().getGimage());

            orderDetilsDao.insert(orderDetils);
        }

        //删除购物车
        cartDao.deleteBatchIds(Arrays.asList(cids));

        return 1;
    }

    /**
     * 根据用户查询所有订单
     * @param uid
     * @return
     */
    @Override
    public List<Orders> queryByUid(Integer uid) {

        //查询当前用户的所有订单
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        List<Orders> orders = orderDao.selectList(queryWrapper);

        //根据订单id查询订单详情
        for (Orders order : orders) {
            QueryWrapper queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("oid", order.getId());
            List<OrderDetils> list = orderDetilsDao.selectList(queryWrapper2);

            //将订单详情放入订单对象中
            order.setOrderDetils(list);
        }

        return orders;
    }
}
