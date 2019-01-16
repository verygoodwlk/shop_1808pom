package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @Value("${fdfs.serverpath}")
    private String fdfsPath;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

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

        model.addAttribute("fdfsPath", fdfsPath);

        return "goodslist";
    }

    /**
     * 添加商品
     * @return
     */
    @RequestMapping("/insert")
    public String insertGoods(Goods goods){

        //调用服务添加商品
        goodsService.insert(goods);

        return "redirect:/goods/list";
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
    @ResponseBody
    public String uploadImg(MultipartFile file) throws Exception {

        //后缀的作用就是告诉操作系统当前是一个什么类型的文件，但是并不能改变当前文件的本质
        //xxxx.mp4
        //将该图片上传到FastDFS服务上
        StorePath result = fastFileStorageClient.uploadImageAndCrtThumbImage(
                file.getInputStream(),
                file.getSize(),
                "png",
                null);

        //group1/M00/00/00/wKjitFw-496ACtd4AAMT7C75RYY463.PNG
        System.out.println("上传到FastDFS中的文件路径：" + result.getFullPath());

        //{"imgpath":"xxxxxxxxxxxx"}
        return "{\"imgpath\":\"" + result.getFullPath() + "\"}";
    }
}
