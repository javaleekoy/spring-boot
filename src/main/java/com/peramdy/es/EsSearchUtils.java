package com.peramdy.es;

import com.peramdy.es.spring.EsClientFactory;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author pd 2018/3/13.
 */
@Component
public class EsSearchUtils {

    @Autowired
    private EsClientBuilder esClientBuilder;

    @Autowired
    private EsClientFactory clientFactory;

    /**
     * 查询
     *
     * @param indices   索引集合
     * @param types     类型集合
     * @param key       搜索字段
     * @param value     搜索值
     * @param pageIndex 当前页
     * @param pageSize  每页显示大小
     */
    public List<Map<String, Object>> searchIndices(String[] indices, String[] types, String key, Object value, Integer pageIndex, Integer pageSize) {
        TransportClient client = null;
        try {
            client = clientFactory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //sort
        SortBuilder sortBuilder = SortBuilders.fieldSort("age").order(SortOrder.DESC);
        SearchResponse response = client.prepareSearch(indices)
                .setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery(key, value))
                .addSort(sortBuilder)
                .setSize(pageSize)
                .setFrom(pageSize * (pageIndex - 1))
                .setExplain(true)
                .get();
        SearchHits hits = response.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            list.add(hit.getSource());
            System.out.println(hit.getSource());
        }
        return list;
    }

    /**
     * 查询 And && (must)
     *
     * @param indices   索引集合
     * @param types     类型集合
     * @param map       搜索kv
     * @param pageIndex 当前页
     * @param pageSize  每页显示大小
     */
    public List<Map<String, Object>> searchIndicesAnd(String[] indices, String[] types, Map<String, Object> map, Integer pageIndex, Integer pageSize) {
        TransportClient client = esClientBuilder.buildDefault();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (String key : map.keySet()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery(key, map.get(key)));
        }
        SearchResponse response = client.prepareSearch(indices).setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .addSort("age", SortOrder.ASC)
                .setSize(pageSize)
                .setFrom((pageIndex - 1) * pageSize)
                .setExplain(true)
                .get();
        SearchHits hits = response.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            list.add(hit.getSource());
            System.out.println(hit.getSource());
        }
        return list;
    }


    /**
     * 查询 not (mustNot)
     *
     * @param indices   索引集合
     * @param types     类型集合
     * @param map       搜索kv
     * @param pageIndex 当前页
     * @param pageSize  每页显示大小
     */
    public List<Map<String, Object>> searchIndicesNot(String[] indices, String[] types, Map<String, Object> map, Integer pageIndex, Integer pageSize) {
        TransportClient client = esClientBuilder.buildDefault();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (String key : map.keySet()) {
            boolQueryBuilder.mustNot(QueryBuilders.matchQuery(key, map.get(key)));
        }
        SearchResponse response = client.prepareSearch(indices).setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .setSize(pageSize)
                .setFrom((pageIndex - 1) * pageSize)
                .setExplain(true)
                .get();
        SearchHits hits = response.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            list.add(hit.getSource());
            System.out.println(hit.getSource());
        }
        return list;
    }


    /**
     * 查询 or (should)
     *
     * @param indices   索引集合
     * @param types     类型集合
     * @param map       搜索kv
     * @param pageIndex 当前页
     * @param pageSize  每页显示大小
     */
    public List<Map<String, Object>> searchIndicesShould(String[] indices, String[] types, Map<String, Object> map, Integer pageIndex, Integer pageSize) {
        TransportClient client = esClientBuilder.buildDefault();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (String key : map.keySet()) {
            boolQueryBuilder.should(QueryBuilders.matchQuery(key, map.get(key)));
        }
        SearchResponse response = client.prepareSearch(indices).setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .addSort(FieldSortBuilder.NAME, SortOrder.ASC)
                .setSize(pageSize)
                .setFrom((pageIndex - 1) * pageSize)
                .setExplain(true)
                .get();
        SearchHits hits = response.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            list.add(hit.getSource());
            System.out.println(hit.getSource());
        }
        return list;
    }


    /**
     * @param indices   索引集合
     * @param types     类型集合
     * @param fields    搜索字段集合
     * @param key       搜索值
     * @param pageIndex 当前页
     * @param pageSize  每页显示大小
     */
    public List<Map<String, Object>> searchIndicesHighlight(String[] indices, String[] types, String[] fields, String key, Integer pageIndex, Integer pageSize) {
        TransportClient client = esClientBuilder.buildDefault();
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<strong>");
        highlightBuilder.postTags("</strong>");
        highlightBuilder.field("gender");
        highlightBuilder.field("country");
        SearchResponse response = client.prepareSearch(indices).setTypes(types)
                //高亮
                .highlighter(highlightBuilder)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.moreLikeThisQuery(fields))
                .setSize(pageSize)
                .setFrom((pageIndex - 1) * pageSize)
                .setExplain(true)
                .get();
        SearchHits hits = response.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            Map<String, Object> map = hit.getSource();
            Text[] text = hit.getHighlightFields().get("gender").fragments();
            for (Text str : text) {
                System.out.println(str.string());
                map.put("gender", str.string());
            }
            list.add(map);
            System.out.println(map.toString());
        }
        return list;
    }

}
