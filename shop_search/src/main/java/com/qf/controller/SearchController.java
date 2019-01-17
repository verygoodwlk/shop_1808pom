package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author ken
 * @Date 2019/1/17
 * @Version 1.0
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    /**
     * 根据关键字进行搜索
     * @param keyword
     * @return
     */
    @RequestMapping("/query")
    public String search(String keyword, Model model){

        //调用服务搜索solr索引库
        List<Goods> goods =  searchService.queryByIndexed(keyword);
        model.addAttribute("goodslist", goods);

        return "searchlist";
    }
}
