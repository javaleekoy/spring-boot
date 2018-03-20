package com.peramdy.controller.mg;

import com.peramdy.mgo.MgClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pd 2018/3/20.
 */
@RestController
@RequestMapping("/mg")
public class MgController {

    @Autowired
    private MgClient mgClient;

    @GetMapping("/create")
    public void createCollection() {
        mgClient.createCollection("test", "huahua");
    }

    @GetMapping("/insertOne")
    public void insertOne() {
        Document document = new Document();
        document.append("name", "刘大");
        document.append("gender", "男");
        document.append("age", "8");
        mgClient.insertOne("test", "huahua", document);
    }

    @GetMapping("/insertMany")
    public void insertMany() {
        List<Document> documents = new ArrayList<Document>();
        Document document1 = new Document();
        document1.append("name", "张三");
        document1.append("gender", "男");
        document1.append("age", "10");
        Document document2 = new Document();
        document2.append("name", "张二");
        document2.append("gender", "女");
        document2.append("age", "5");
        documents.add(document1);
        documents.add(document2);
        mgClient.insertMany("test", "huahua", documents);
    }

    @GetMapping("/findAnd")
    public void findAnd() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "刘大");
        map.put("gender", "男");
        mgClient.findAnd("test", "huahua", map);
    }

    @GetMapping("/deleteOne")
    public void deleteOne() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "刘大");
        mgClient.deleteOne("test", "huahua", map);
    }

    @GetMapping("/deleteMany")
    public void deleteMany() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "刘大");
        mgClient.deleteMany("test", "huahua", map);
    }


    @GetMapping("/updateOne")
    public void updateOne() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "张二");
        map.put("age", "10");
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("age", "11");
        mgClient.updateOne("test", "huahua", map, map2);
    }


    @GetMapping("/updateMany")
    public void updateMany() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "张二");
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("age", "13");
        mgClient.updateMany("test", "huahua", map, map2);
    }


}
