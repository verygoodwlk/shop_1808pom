package com.qf.shop_search;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSearchApplicationTests {

    @Autowired
    private SolrClient solrClient;

    /**
     * 添加索引库
     *
     * 后台添加商品 -> 数据库、索引库
     * 后台添加商品 -> 数据库
     *   定时任务（3:00） -> 数据库数据同步到索引库
     *
     */
    @Test
    public void insert() throws IOException, SolrServerException {
        for(int i = 0; i < 10; i++) {
            //创建一个document对象 -> 一个商品
            SolrInputDocument document = new SolrInputDocument();
            //设置字段
            document.setField("id", i + 1);

            if(i == 5){
                document.setField("gtitle", "小霸王学习机霸王霸王霸王霸王霸王霸王霸王霸王" + i);
            } else {
                document.setField("gtitle", "小霸王学习机" + i);
            }
            document.setField("ginfo", "学电脑更容易");
            document.setField("gprice", 199.9 + i);
            document.setField("gimage", "http://www.baidu.com");
            document.setField("gcount", 100 + i);

            //将该商品放入索引库
            solrClient.add(document);

        }

        solrClient.commit();
    }


    @Test
    public void update(){
        //创建一个document对象 -> 一个商品
        SolrInputDocument document = new SolrInputDocument();
        //设置字段
        document.setField("id", 11);
        document.setField("gtitle", "健力宝");
        document.setField("ginfo", "功能性饮料，饮料中的小霸王");
        document.setField("gprice", 3);
        document.setField("gimage", "http://www.baidu.com");
        document.setField("gcount", 1000);

        try {
            //将该商品放入索引库
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() throws IOException, SolrServerException {
//        solrClient.deleteById("1");
        solrClient.deleteByQuery("*:*");
        solrClient.commit();
    }


    @Test
    public void query(){
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("gtitle:霸王 || ginfo:霸王");

        try {
            QueryResponse query = solrClient.query(solrQuery);
            SolrDocumentList results = query.getResults();

            for(SolrDocument document : results){
                String id = (String) document.get("id");
                String gtitle = (String) document.get("gtitle");
                String ginfo = (String) document.get("ginfo");
                float gprice = (float) document.get("gprice");
                int gcount = (int) document.get("gcount");
                String gimage = (String) document.get("gimage");
                System.out.println(id + " " + gtitle + " " + ginfo + " " + gprice + " " + gcount + " " + gimage);
            }

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

