package com.peramdy.es;

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
    private EsUtils esUtils;

    /**
     * 查询
     *
     * @param indices
     * @param types
     * @param key
     * @param value
     */
    public List<Map<String, Object>> searchIndices(String[] indices, String[] types, String key, Object value) {
        TransportClient client = esUtils.createEsClient();

        SortBuilder sortBuilder = SortBuilders.fieldSort("age").order(SortOrder.DESC);

        SearchResponse response = client.prepareSearch(indices)
                .setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery(key, value))
                .addSort(sortBuilder)
                .setSize(2)
                .setFrom(0)
                .setExplain(true)
                .get();
        SearchHits hits = response.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            list.add(hit.getSource());
        }
        return list;
    }

    /**
     * 查询 And && (must)
     *
     * @param indices
     * @param types
     * @param map
     * @return
     */
    public List<Map<String, Object>> searchIndicesAnd(String[] indices, String[] types, Map<String, Object> map) {
        TransportClient client = esUtils.createEsClient();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (String key : map.keySet()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery(key, map.get(key)));
        }
        SearchResponse response = client.prepareSearch(indices).setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .addSort("age", SortOrder.ASC)
                .setSize(10)
                .setFrom(0)
                .setExplain(true)
                .get();
        SearchHits hits = response.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            list.add(hit.getSource());
        }
        return list;
    }


    /**
     * 查询 not (mustNot)
     *
     * @param indices
     * @param types
     * @param map
     * @return
     */
    public List<Map<String, Object>> searchIndicesNot(String[] indices, String[] types, Map<String, Object> map) {
        TransportClient client = esUtils.createEsClient();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (String key : map.keySet()) {
            boolQueryBuilder.mustNot(QueryBuilders.matchQuery(key, map.get(key)));
        }
        SearchResponse response = client.prepareSearch(indices).setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .setSize(10)
                .setFrom(0)
                .setExplain(true)
                .get();
        SearchHits hits = response.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            list.add(hit.getSource());
        }
        return list;
    }


    /**
     * 查询 or (should)
     *
     * @param indices
     * @param types
     * @param map
     * @return
     */
    public List<Map<String, Object>> searchIndicesShould(String[] indices, String[] types, Map<String, Object> map) {
        TransportClient client = esUtils.createEsClient();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (String key : map.keySet()) {
            boolQueryBuilder.should(QueryBuilders.matchQuery(key, map.get(key)));
        }

        SearchResponse response = client.prepareSearch(indices).setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .addSort(FieldSortBuilder.NAME, SortOrder.ASC)
                .setSize(10)
                .setFrom(0)
                .setExplain(true)
                .get();
        SearchHits hits = response.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            list.add(hit.getSource());
        }
        return list;
    }


    /**
     * @param indices
     * @param types
     * @param fields
     * @param key
     * @return
     */
    public List<Map<String, Object>> searchIndicesHighlight(String[] indices, String[] types, String[] fields, String key) {
        TransportClient client = esUtils.createEsClient();
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<strong>");
        highlightBuilder.postTags("</strong>");
        highlightBuilder.field("gender");
        highlightBuilder.field("country");
        SearchResponse response = client.prepareSearch(indices).setTypes(types)
                .highlighter(highlightBuilder) //高亮
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.moreLikeThisQuery(fields))
                .setSize(10)
                .setFrom(0)
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
