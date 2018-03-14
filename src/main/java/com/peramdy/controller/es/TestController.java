package com.peramdy.controller.es;

import com.peramdy.es.EsSearchUtils;
import com.peramdy.es.EsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pd 2018/3/14.
 */
@RestController("esTestController")
@RequestMapping("/es")
public class TestController {

    @Autowired
    private EsUtils esUtils;

    @Autowired
    private EsSearchUtils esSearchUtils;

    private static String index = "peramdy";
    private static String type = "huahua";

    @GetMapping("/addIndexMap")
    public String addIndexMap() {
        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("name", "张三");
//        map.put("gender", "男");
//        map.put("country", "中华人民共和国");
//        map.put("address", "中国上海市徐汇区");
//        map.put("age", 18);
        map.put("name", "陈二");
        map.put("gender", "男");
        map.put("country", "南非共和国");
        map.put("address", "豪登省*茨瓦内");
        map.put("age", 9);
        esUtils.addIndexForMap(index, type, "9", map);
        return "success";
    }

    @GetMapping("/addIndexJson")
    public String addIndexJson() {
        String json = "{\"name\":\"李四\",\"gender\":\"女\",\"country\":\"美利坚合众国\",\"address\":\"加利福尼亚州*洛杉矶\",\"age\":\"20\"}";
        esUtils.addIndexForJson(index, type, "2", json);
        return "success";
    }


    @GetMapping("/getIndex")
    public String getIndex() {
        String str = esUtils.getInfoById(index, type, "2");
        System.out.println(str);
        return str;
    }


    @GetMapping("/updateIndexMap")
    public String updateIndexForMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("age", "22");
        esUtils.updateIndexForMap(index, type, "3", map);
        return "success";
    }

    @GetMapping("/updateIndexMap2")
    public String updateIndexForMap2() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("desc", "我是个美国佬，怎样");
        esUtils.updateIndexForMap(index, type, "2", map);
        return "success";
    }

    @GetMapping("/upsertIndexMap")
    public String upsertIndexForMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "郑十");
        map.put("gender", "男");
        map.put("country", "阿拉伯联合酋长国");
        map.put("address", "迪拜");
        map.put("age", 40);
        esUtils.upsertIndexForMap(index, type, "8", map);
        return "success";
    }


    @GetMapping("/delInfoById")
    public String delInfoById() {
        esUtils.delInfoById(index, type, "3");
        return "success";
    }

    @GetMapping("/delQueryInfo")
    public String delQueryInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("gender", "男");
        map.put("name", "王五");
        String[] indices = new String[]{index};
        String[] types = new String[]{type};
        esUtils.delQueryInfo(map, indices, types);
        return "success";
    }


    @GetMapping("/delQueryInfoOperation")
    public String delQueryInfoOperation() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("gender", "男");
        map.put("name", "王五");
        String[] indices = new String[]{index};
        String[] types = new String[]{type};
        esUtils.delQueryInfoOperation(map, indices, types);
        return "success";
    }


    @GetMapping("/searchIndices")
    public String searchIndices() {
        String[] indices = new String[]{index};
        String[] types = new String[]{type};
        esSearchUtils.searchIndices(indices, types, "gender", "男");
        return "success";
    }


    @GetMapping("/searchIndicesAnd")
    public String searchIndicesAnd() {
        String[] indices = new String[]{index};
        String[] types = new String[]{type};
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("gender", "女");
//        map.put("name", "王五");
        esSearchUtils.searchIndicesAnd(indices, types, map);
        return "success";
    }

    @GetMapping("/searchIndicesNot")
    public String searchIndicesNot() {
        String[] indices = new String[]{index};
        String[] types = new String[]{type};
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("gender", "女");
        map.put("name", "王五");
        esSearchUtils.searchIndicesNot(indices, types, map);
        return "success";
    }

    @GetMapping("/searchIndicesShould")
    public String searchIndicesShould() {
        String[] indices = new String[]{index};
        String[] types = new String[]{type};
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("gender", "女");
        esSearchUtils.searchIndicesShould(indices, types, map);
        return "success";
    }


    @GetMapping("/searchIndicesHighlight")
    public String searchIndicesHighlight() {
        String[] indices = new String[]{index};
        String[] types = new String[]{type};
        String[] fields = new String[]{"gender", "country"};
        esSearchUtils.searchIndicesHighlight(indices, types, fields, "男");
        return "success";
    }

}
