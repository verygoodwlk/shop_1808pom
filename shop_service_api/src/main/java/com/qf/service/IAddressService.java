package com.qf.service;

import com.qf.entity.Address;

import java.util.List;

/**
 * @Author ken
 * @Time 2019/1/23 14:11
 * @Version 1.0
 */

public interface IAddressService {

    List<Address> queryAddressByUid(Integer uid);

    int insertAddress(Address address);

}
