package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Address;

/**
 * @Author ken
 * @Time 2019/1/23 14:12
 * @Version 1.0
 */
public interface IAddressDao extends BaseMapper<Address> {

    int insertAddr(Address address);
}
