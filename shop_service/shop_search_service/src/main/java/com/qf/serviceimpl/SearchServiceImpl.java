package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ken
 * @Date 2019/1/17
 * @Version 1.0
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public List<Goods> queryByIndexed(String keyword) {
        //查询索引库
        SolrQuery solrQuery = new SolrQuery();
        if(keyword == null ){
            solrQuery.setQuery("*:*");
        } else {
            solrQuery.setQuery("gtitle:" + keyword + " || ginfo:" + keyword);
        }

        List<Goods> goodsList = new ArrayList<>();
        try {

//            //limit ?,?
//            //开始的位置
//            solrQuery.setStart();
//            //每页显示多少条
//            solrQuery.setRows();

            //获得查询结果
            QueryResponse query = solrClient.query(solrQuery);
//            //获得查询的总条数
//            query.getResults().getNumFound();


            SolrDocumentList results = query.getResults();
            for(SolrDocument document : results){
                String id = (String) document.get("id");
                String gtitle = (String) document.get("gtitle");
                String ginfo = (String) document.get("ginfo");
                float gprice = (float) document.get("gprice");
                int gcount = (int) document.get("gcount");
                String gimage = (String) document.get("gimage");

                Goods goods = new Goods(
                        Integer.parseInt(id),
                        gtitle,
                        ginfo,
                        gcount,
                        0,
                        0,
                        gprice,
                        gimage
                );
                goodsList.add(goods);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return goodsList;
    }

    /**
     * 将商品信息添加到索引库
     * @param goods
     * @return
     */
    @Override
    public int insertIndexed(Goods goods) {
        SolrInputDocument solrDocument = new SolrInputDocument();
        solrDocument.setField("id", goods.getId());
        solrDocument.setField("gtitle", goods.getTitle());
        solrDocument.setField("ginfo", goods.getGinfo());
        solrDocument.setField("gimage", goods.getGimage());
        solrDocument.setField("gcount", goods.getGcount());
        solrDocument.setField("gprice", goods.getPrice());

        try {
            solrClient.add(solrDocument);
            solrClient.commit();

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
