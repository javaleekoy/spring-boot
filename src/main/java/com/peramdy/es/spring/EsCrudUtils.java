package com.peramdy.es.spring;

import com.alibaba.fastjson.JSON;
import com.peramdy.entity.Stu;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author pd 2018/3/15.
 */
@Component
public class EsCrudUtils {

    private static Logger logger = LoggerFactory.getLogger(EsCrudUtils.class);

    @Autowired
    private EsClientFactory esClientFactory;

    /**
     * 添加数据
     *
     * @param index
     * @param type
     * @param id
     * @param entity
     * @return
     */
    public String saveDoc(String index, String type, String id, Object entity) throws Exception {
        IndexResponse response = esClientFactory.getObject().prepareIndex(index, type, id).setSource(getXContentBuilderKeyValue(entity)).get();
        return response.getId();
    }

    /**
     * 更新数据
     *
     * @param index
     * @param type
     * @param id
     * @param entity
     * @return
     * @throws Exception
     */
    public String updateDoc(String index, String type, String id, Object entity) throws Exception {
        UpdateResponse response = esClientFactory.getObject().prepareUpdate(index, type, id).setDoc(getXContentBuilderKeyValue(entity)).get();
        return response.getId();
    }

    /**
     * 删除数据
     *
     * @param index
     * @param type
     * @param id
     * @return
     * @throws Exception
     */
    public String deleteById(String index, String type, String id) throws Exception {
        DeleteResponse response = esClientFactory.getObject().prepareDelete(index, type, id).get();
        return response.getId();
    }

    /**
     * 查询数据
     *
     * @param index
     * @param type
     * @param id
     * @return
     * @throws Exception
     */
    public String getIdx(String index, String type, String id) throws Exception {
        GetResponse response = esClientFactory.getObject().prepareGet(index, type, id).get();
        if (response.isExists()) {
            return response.getSourceAsString();
        }
        return null;
    }

    private <T> T parseObject(T t, String src) {
        try {
            return (T) JSON.parseObject(src, t.getClass());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("解析失败，{}", e.getMessage());
        }
        return null;
    }

    /**
     * 查询数据
     *
     * @param index
     * @param type
     * @param id
     * @param t
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T getIdx(String index, String type, String id, T t) throws Exception {
        return parseObject(t, getIdx(index, type, id));
    }


    /**
     * 全文搜索
     *
     * @param filed
     * @param queryValue
     * @param pageNum
     * @param pageSize
     * @param indices
     * @throws Exception
     */
    public void searchFullText(String filed, String queryValue, int pageNum, int pageSize, String... indices) throws Exception {
        QueryBuilder builder = QueryBuilders.matchQuery(filed, queryValue);
        TransportClient client = esClientFactory.getObject();
        SearchResponse scrollResp = client.prepareSearch(indices)
                .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
//                .setFrom(pageNum * pageSize)
                .setSize(pageSize)
                .setScroll(new TimeValue(6, TimeUnit.SECONDS))
                .setQuery(builder)
                .execute()
                .actionGet();
        do {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                System.out.println("result:+++++" + hit.getSourceAsString());
            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute()
                    .actionGet();
        } while (scrollResp.getHits().getHits().length != 0);
    }

    /**
     * 全文搜索
     *
     * @param param
     * @param page
     * @param indices
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> EsPage<T> searchFullText(T param, EsPage<T> page, String... indices) throws Exception {
        QueryBuilder builder = null;
        Map<String, Object> map = getObjectMap(param);
        if (map == null) {
            return null;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                // builder =QueryBuilders.multiMatchQuery(text, fieldNames)(entry.getKey(),entry.getValue());
                // builder =QueryBuilders.wildcardQuery( entry.getKey(), "*" + entry.getValue().toString()+ "*" );
                builder = QueryBuilders.matchQuery(entry.getKey(), entry.getValue());
            }
        }
        TransportClient client = esClientFactory.getObject();

        HighlightBuilder highlight = new HighlightBuilder();
        highlight.field("stuName");
        highlight.preTags("<strong>");
        highlight.postTags("</strong>");
        SearchResponse scrollResp = client.prepareSearch(indices)
                .setFrom((page.getPageNo() - 1) * page.getPageSize())
                .highlighter(highlight)
                .setSize(page.getPageSize())
                // .setScroll(newTimeValue(60000))
                .setQuery(builder)
                .get();
        List<T> result = new ArrayList<T>();
        // ElasticSearchPage<T> ret = new ElasticSearchPage<>();
        for (SearchHit hit : scrollResp.getHits().getHits()) {
            try {
                Map<String, HighlightField> highlightResult = hit.getHighlightFields();
                System.out.println(highlightResult.get("stuName"));
                result.add(parseObject(param, hit.getSourceAsString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        page.setTotal(scrollResp.getHits().totalHits);
        page.setParam(param);
        page.setRetList(result);
        return page;
    }


    public EsPage<Stu> searchFullText2(Stu stu, EsPage<Stu> page, String... indices) throws Exception {
        QueryBuilder builder = null;
        Map<String, Object> map = getObjectMap(stu);
        if (map == null) {
            return null;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                builder = QueryBuilders.wildcardQuery(entry.getKey(), "*" + entry.getValue() + "*");
            }
        }
        TransportClient client = esClientFactory.getObject();
        HighlightBuilder highlight = new HighlightBuilder();
        highlight.field("stuName");
        highlight.field("age");
        highlight.preTags("<strong>");
        highlight.postTags("</strong>");
        SearchResponse searchResponse = client.prepareSearch(indices)
                .setFrom((page.getPageNo() - 1) * page.getPageSize())
                .highlighter(highlight)
                .setSize(page.getPageSize())
                .setQuery(builder)
                .get();
        List<Stu> result = new ArrayList<Stu>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            try {
                Map<String, HighlightField> highlightResult = hit.getHighlightFields();
                Stu parseStu = parseObject(stu, hit.getSourceAsString());
                HighlightField highlightField = highlightResult.get("stuName");
                for (Text text : highlightField.fragments()) {
                    System.out.println(text);
                    parseStu.setStuName(text.string());
                }
                result.add(parseStu);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        page.setTotal(searchResponse.getHits().totalHits);
        page.setParam(stu);
        page.setRetList(result);
        return page;
    }


    /**
     * @param index
     * @param type
     * @return
     * @throws Exception
     */
    public boolean isTypeExist(String index, String type) throws Exception {
        TransportClient client = esClientFactory.getObject();
        return client.admin().indices().prepareTypesExists(index).setTypes(type).execute().actionGet().isExists();
    }

    /**
     * @param index
     * @return
     * @throws Exception
     */
    public boolean isIndexExist(String index) throws Exception {
        TransportClient client = esClientFactory.getObject();
        return client.admin().indices().prepareExists(index).execute().actionGet().isExists();
    }

    /**
     * @param index
     * @param type
     * @param entity
     * @return
     * @throws Exception
     */
    public boolean createType(String index, String type, Object entity) throws Exception {
        TransportClient client = esClientFactory.getObject();
        if (!isIndexExist(index)) {
            logger.error("{}索引不存在", index);
            return false;
        }
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
        Class object = entity.getClass();
        List<Field> fieldList = new ArrayList<Field>();
        while (object != null) {
            fieldList.addAll(Arrays.asList(object.getDeclaredFields()));
            object = object.getSuperclass();
        }
        for (Field field : fieldList) {
            if (field.isAnnotationPresent(ESearchType.class)) {
                ESearchType eSearchType = field.getAnnotation(ESearchType.class);
                boolean analyze = eSearchType.analyze();
                EsDataType esDataType = eSearchType.type();
                System.out.println(field.getName());
                builder.startObject(field.getName());
                //判断field类型
                if (EsDataType.STRING.getId() == esDataType.getId()) {
                    builder.field("type", EsDataType.STRING.getValue());
                } else if (EsDataType.SHORT.getId() == esDataType.getId()) {
                    builder.field("type", EsDataType.SHORT.getValue());
                } else if (EsDataType.BYTE.getId() == esDataType.getId()) {
                    builder.field("type", EsDataType.BYTE.getValue());
                } else if (EsDataType.DOUBLE.getId() == esDataType.getId()) {
                    builder.field("type", EsDataType.DOUBLE.getValue());
                } else if (EsDataType.FLOAT.getId() == esDataType.getId()) {
                    builder.field("type", EsDataType.FLOAT.getValue());
                } else if (EsDataType.INTEGER.getId() == esDataType.getId()) {
                    builder.field("type", EsDataType.INTEGER.getValue());
                } else {
                    builder.field("type", EsDataType.STRING.getValue());
                }
                //判断是否用分词器
                if (analyze) {
                    builder.field("analyzer", "ik_max_word");
                    builder.field("search_analyzer", "ik_max_word");
                }
                builder.endObject();
            }
        }
        builder.endObject();

        try {
            // 若type存在则可通过该方法更新type
            return client.admin().indices().preparePutMapping(index).setType(type).setSource(builder).get().isAcknowledged();
        } catch (Exception e) {
            logger.error("创建type失败，{}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param object
     * @return
     */
    public static XContentBuilder getXContentBuilderKeyValue(Object object) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();

            List<Field> fieldList = new ArrayList<Field>();
            Class tempClass = object.getClass();

            // 当父类为null的时候说明到达了最上层的父类(Object类).
            while (tempClass != null) {
                fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
                // 得到父类,然后赋给自己
                tempClass = tempClass.getSuperclass();
            }
            for (Field field : fieldList) {
                if (field.isAnnotationPresent(ESearchType.class)) {
                    ESearchType eSearchType = field.getAnnotation(ESearchType.class);
                    System.out.println(eSearchType.analyze());
                    System.out.println(eSearchType.type());
                    PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), object.getClass());
                    Object value = descriptor.getReadMethod().invoke(object);
                    if (value != null) {
                        if (Integer.class.isInstance(value)) {
                            builder.field(field.getName(), Integer.parseInt(value.toString()));
                        } else if (String.class.isInstance(value)) {
                            builder.field(field.getName(), value.toString());
                        } else {
                            builder.field(field.getName(), value.toString());
                        }
                    }
                }
            }
            builder.endObject();
            logger.debug(builder.string());
            return builder;
        } catch (Exception e) {
            logger.error("获取object key-value失败，{}", e.getMessage());
        }
        return null;
    }

    public static Map<String, Object> getObjectMap(Object o) {
        List<Field> fieldList = new ArrayList<Field>();
        @SuppressWarnings("rawtypes")
        Class tempClass = o.getClass();
        while (tempClass != null) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
        }
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            for (Field field : fieldList) {
                if (field.isAnnotationPresent(ESearchType.class)) {
                    PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), o.getClass());
                    Object value = descriptor.getReadMethod().invoke(o);
                    result.put(field.getName(), value);
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("获取object key-value失败，{}", e.getMessage());
            return null;
        }
    }

}
