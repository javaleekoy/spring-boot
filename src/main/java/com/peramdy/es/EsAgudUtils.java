package com.peramdy.es;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.script.Script;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author pd 2018/3/13.
 */
@Component
public class EsAgudUtils {

    private Logger logger = LoggerFactory.getLogger(EsAgudUtils.class);

    @Autowired
    private EsClientBuilder esClientBuilder;


    //======================================================添加索引数据=================================================>>

    /**
     * 添加索引(json)
     * 添加已存在的数据时此数据会被覆盖
     *
     * @param index 索引
     * @param type  类型
     * @param id    主键
     * @param json  内容
     */
    public void addIndexForJson(String index, String type, String id, String json) {
        TransportClient client = null;
        try {
            logger.info("index : " + index);
            logger.info("type : " + type);
            logger.info("id : " + id);
            logger.info("jsonContent : " + json);
            client = esClientBuilder.buildDefault();
            IndexResponse indexResponse;
            if (id == null) {
                indexResponse = client.prepareIndex(index, type).setSource(json, XContentType.JSON).get();
            } else {
                indexResponse = client.prepareIndex(index, type, id).setSource(json, XContentType.JSON).get();
            }
            logger.info("indexResponse-index : " + indexResponse.getIndex());
            logger.info("indexResponse-type : " + indexResponse.getType());
            logger.info("indexResponse-id : " + indexResponse.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    /**
     * 添加索引(map)
     *
     * @param index 索引
     * @param type  类型
     * @param id    主键
     * @param map   内容
     */
    public void addIndexForMap(String index, String type, String id, Map<String, Object> map) {
        TransportClient client = null;
        try {
            logger.info("index : " + index);
            logger.info("type : " + type);
            logger.info("id : " + id);
            logger.info("map : " + map.toString());
            client = esClientBuilder.buildDefault();
            IndexResponse indexResponse = null;
            if (id == null) {
                indexResponse = client.prepareIndex(index, type).setSource(map).get();
            } else {
                indexResponse = client.prepareIndex(index, type, id).setSource(map).get();
            }
            logger.info("indexResponse-index : " + indexResponse.getIndex());
            logger.info("indexResponse-type : " + indexResponse.getType());
            logger.info("indexResponse-id : " + indexResponse.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    //======================================================查询索引数据=================================================>>

    /**
     * 查询数据(get)
     *
     * @param index 索引
     * @param type  类型
     * @param id    主键
     * @return String
     */
    public String getInfoById(String index, String type, String id) {
        TransportClient client = null;
        try {
            logger.info("getIndex : " + index);
            logger.info("getType : " + type);
            logger.info("getId : " + id);
            client = esClientBuilder.buildDefault();
            GetResponse getResponse = client.prepareGet(index, type, id).get();
            logger.info("getResponse-index : " + getResponse.getIndex());
            logger.info("getResponse-type : " + getResponse.getType());
            logger.info("getResponse-id : " + getResponse.getId());
            String response = getResponse.getSourceAsString();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return null;
    }

    //======================================================更新索引数据=================================================>>

    /**
     * 更新数据(map)
     * 更新属性时如果没有会添加，有会更新(前提：此条数据存在)
     *
     * @param index
     * @param type
     * @param id
     * @param map
     */
    public void updateIndexForMap(String index, String type, String id, Map<String, Object> map) {
        TransportClient client = null;
        try {
            logger.info("index : " + index);
            logger.info("type : " + type);
            logger.info("id : " + id);
            logger.info("map : " + map.toString());
            client = esClientBuilder.buildDefault();
            UpdateResponse response = client.prepareUpdate(index, type, id).setDoc(map).get();
            logger.info("updateResponse-index : " + response.getIndex());
            logger.info("updateResponse-type : " + response.getType());
            logger.info("updateResponse-id : " + response.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    /**
     * groovy 脚本
     * 更新数据(script)
     * elasticsearch.yml,新增一行 ==> script.engine.groovy.inline.update: on
     *
     * @param index
     * @param type
     * @param id
     * @param script
     */
    public void updateIndexForScript(String index, String type, String id, String script) {
        TransportClient client = null;
        try {
            logger.info("index : " + index);
            logger.info("type : " + type);
            logger.info("id : " + id);
            logger.info("script : " + script);
            client = esClientBuilder.buildDefault();
            UpdateResponse response = client.prepareUpdate(index, type, id).setScript(new Script(script)).get();
            logger.info("updateResponse-index : " + response.getIndex());
            logger.info("updateResponse-type : " + response.getType());
            logger.info("updateResponse-id : " + response.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }


    /**
     * 更新或生成数据(map)
     *
     * @param index
     * @param type
     * @param id
     * @param map
     */
    public void upsertIndexForMap(String index, String type, String id, Map<String, Object> map) {
        TransportClient client = null;
        try {
            logger.info("index : " + index);
            logger.info("type : " + type);
            logger.info("id : " + id);
            logger.info("map : " + map.toString());
            client = esClientBuilder.buildDefault();

            IndexRequest indexRequest = new IndexRequest(index, type, id);
            XContentBuilder indexXContentBuilder = jsonBuilder().startObject();

            UpdateRequest updateRequest = new UpdateRequest(index, type, id);
            XContentBuilder updateXContentBuilder = jsonBuilder().startObject();

            for (String key : map.keySet()) {
                indexXContentBuilder.field(key, map.get(key));
                updateXContentBuilder.field(key, map.get(key));
            }

            indexXContentBuilder.endObject();
            updateXContentBuilder.endObject();

            indexRequest.source(indexXContentBuilder);
            updateRequest.doc(updateXContentBuilder).upsert(indexRequest);

            UpdateResponse updateResponse = client.update(updateRequest).get();

            logger.info("updateResponse-index : " + updateResponse.getIndex());
            logger.info("updateResponse-type : " + updateResponse.getType());
            logger.info("updateResponse-id : " + updateResponse.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    //======================================================删除索引数据=================================================>>

    /**
     * 删除索引(del)
     *
     * @param index 索引
     * @param type  类型
     * @param id    主键
     */
    public void delInfoById(String index, String type, String id) {
        TransportClient client = null;
        try {
            logger.info("delIndex : " + index);
            logger.info("delType : " + type);
            logger.info("delId : " + id);
            client = esClientBuilder.buildDefault();
            DeleteResponse deleteResponse = client.prepareDelete(index, type, id).get();
            logger.info("deleteResponse-index : " + deleteResponse.getIndex());
            logger.info("deleteResponse-type : " + deleteResponse.getType());
            logger.info("deleteResponse-id : " + deleteResponse.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    /**
     * 删除索引(query&&del)
     *
     * @param map
     * @param indices
     * @param types
     */
    public void delQueryInfo(Map<String, Object> map, String[] indices, String[] types) {
        TransportClient client = null;
        try {
            logger.info("delQueryIndices : " + indices);
            logger.info("delQueryType : " + types);
            client = esClientBuilder.buildDefault();

            DeleteByQueryAction deleteByQueryAction = DeleteByQueryAction.INSTANCE;
            DeleteByQueryRequestBuilder deleteByQueryRequestBuilder = deleteByQueryAction.newRequestBuilder(client);
            deleteByQueryRequestBuilder.source(indices);

            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            for (String key : map.keySet()) {
                boolQueryBuilder.must(QueryBuilders.matchQuery(key, map.get(key)));
            }
            deleteByQueryRequestBuilder.filter(boolQueryBuilder);

            BulkByScrollResponse bulkByScrollResponse = deleteByQueryRequestBuilder.get();
            logger.info("bulkByScrollResponse-deleted : " + bulkByScrollResponse.getDeleted());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    /**
     * 删除索引(query&&del)
     *
     * @param map
     * @param indices
     * @param types
     */
    public void delQueryInfoOperation(Map<String, Object> map, String[] indices, String[] types) {
        TransportClient client = null;
        try {
            logger.info("delQueryInfoOperationIndices : " + indices);
            logger.info("delQueryInfoOperationTypes : " + types);
            client = esClientBuilder.buildDefault();

            DeleteByQueryAction deleteByQueryAction = DeleteByQueryAction.INSTANCE;
            DeleteByQueryRequestBuilder deleteByQueryRequestBuilder = deleteByQueryAction.newRequestBuilder(client);
            deleteByQueryRequestBuilder.source(indices);

            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            for (String key : map.keySet()) {
                boolQueryBuilder.must(QueryBuilders.matchQuery(key, map.get(key)));
            }

            deleteByQueryRequestBuilder.filter(boolQueryBuilder);
            deleteByQueryRequestBuilder.execute(new ActionListener<BulkByScrollResponse>() {
                @Override
                public void onResponse(BulkByScrollResponse bulkByScrollResponse) {
                    logger.info("bulkByScrollResponse-deleted : " + bulkByScrollResponse.getDeleted());
                }

                @Override
                public void onFailure(Exception e) {
                    logger.info("delQueryInfoOperation del failure ");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }


}
