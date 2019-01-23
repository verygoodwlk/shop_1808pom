package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.ICartDao;
import com.qf.dao.IGoodsDao;
import com.qf.entity.Cart;
import com.qf.entity.Goods;
import com.qf.entity.User;
import com.qf.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.UUID;

/**
 * @Author ken
 * @Date 2019/1/23
 * @Version 1.0
 */
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private ICartDao cartDao;

    @Autowired
    private IGoodsDao goodsDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String addCart(Cart cart, User user, String cartToken) {

        //判断是否登录
        if(user != null){
            //已经登录
            cart.setUid(user.getId());
            //将购物车信息保存进数据库中
            cartDao.insert(cart);

        } else {
            //未登录 - 购物车信息放入redis中

            //如果已经存在cart的uuid，就不用重新生成，说明原来已经有了临时购物车，如果为空，则生成一个新的uuid
            if(cartToken == null){
                cartToken = UUID.randomUUID().toString();
            }
            //将购物车的信息放入链表中，从左边插入
            redisTemplate.opsForList().leftPush(cartToken, cart);//cartToken - [cart - cart - cart - cart]
        }

        return cartToken;
    }

    /**
     * 合并购物车
     * @param cartToken
     * @param user
     * @return
     */
    @Override
    public int mergeCart(String cartToken, User user) {

        if(cartToken != null && redisTemplate.opsForList().size(cartToken) > 0){
            //有临时购物车

            //将临时购物车的数据取出，放入数据库中
            List<Cart> clist =  redisTemplate.opsForList().range(cartToken, 0, redisTemplate.opsForList().size(cartToken));
            for (Cart cart : clist) {
                //设置临时购物车的所属者
                cart.setUid(user.getId());
                //保存数据库
                cartDao.insert(cart);
            }

            //清空临时购物车
            redisTemplate.delete(cartToken);

            return 1;
        }
        return 0;
    }

    /**
     * 查询购物车列表
     * @param cartToken
     * @param user
     * @return
     */
    @Override
    public List<Cart> queryCartList(String cartToken, User user) {

        List<Cart> cartsList = null;

        if(user != null){
            //已经登录 - 数据库
            cartsList = cartDao.queryCartsByUid(user.getId());
        } else {
            //未登录 - redis
            if(cartToken != null && redisTemplate.opsForList().size(cartToken) > 0) {
                cartsList = redisTemplate.opsForList().range(cartToken, 0, redisTemplate.opsForList().size(cartToken));

                //根据临时购物车数据，从数据库中查询商品信息
                for (Cart cart : cartsList) {
                    //根据购物车的商品id，查询商品信息
                    QueryWrapper queryWrapper = new QueryWrapper();
                    queryWrapper.eq("id", cart.getGid());
                    Goods goods = goodsDao.selectOne(queryWrapper);

                    //将商品信息，放入购物车对象中
                    cart.setGoods(goods);
                }
            }
        }
        
        return cartsList;
    }
}
