package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author ken
 * @Date 2019/1/16
 * @Version 1.0
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private IGoodsService goodsService;

    /**
     * 商品列表
     * @return
     */
    @RequestMapping("/list")
    public String goodsManger(Model model){

        //调用服务层获得商品信息
        List<Goods> goods = goodsService.queryAll();
        System.out.println("商品列表：" + goods);
        model.addAttribute("goods", goods);

        return "goodslist";
    }

    /**
     * 图片上传
     *
     * 上传后的文件名怎么处理？
     *  · 沿用原来的文件名 - 有可能重名、中文不太好处理
     *  · 使用系统自己的命名规则重新对文件命名 - uuid
     *
     * 上传到哪里？
     *  · 上传到tomcat
     *  · 上传到硬盘的某个文件夹中
     *  · 上传到分布式文件系统中 - FastDFS
     *
     * @return
     */
    @RequestMapping("/uploadimg")
    public String uploadImg(MultipartFile file){
        System.out.println("上传的文件名称：" + file.getOriginalFilename());
        System.out.println("上传的文件大小：" + file.getSize());
        return null;
    }
}
