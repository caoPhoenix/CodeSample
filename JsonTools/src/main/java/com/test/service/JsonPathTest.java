package com.test.service;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

public class JsonPathTest {

    /**
     * <pre> json:
     * {
     *     "tool": {
     *         "jsonpath": {
     *             "creator": {
     *                 "name": "Jayway Inc.",
     *                 "location": [
     *                     "Malmo",
     *                     "San Francisco",
     *                     "Helsingborg"
     *                 ]
     *             }
     *         }
     *     },
     *     "book": [
     *         {
     *             "title": "Beginning JSON",
     *             "price": 49.99
     *         },
     *         {
     *             "title": "JSON at Work",
     *             "price": 29.99
     *         }
     *     ]
     * }
     * </pre>
     */
    private static final String json = "{\"tool\":{\"jsonpath\":{\"creator\":{\"name\":\"Jayway Inc.\",\"location\":[\"Malmo\",\"San Francisco\",\"Helsingborg\"]}}},\"book\":[{\"title\":\"Beginning JSON\",\"price\":49.99},{\"title\":\"JSON at Work\",\"price\":29.99}]}";

    public static void main(String[] args) throws Exception {
        String path1 = "$.tool.jsonpath.creator.location[1]";
        String path2 = "$['tool']['jsonpath']['creator']['location'][*]";
        String path3 = "$.book..title";
        String path4 = "$.book[1]";

        DocumentContext jsonContext = JsonPath.parse(json);
        // 读出来的对象是 1.Java原生类型 或者 2.JsonPath 库的类型
        Object o1 = JsonPath.read(json, path1);   // class java.lang.String
        Object o2 = jsonContext.read(path2);   // class net.minidev.json.JSONArray
        // 可以直接用正确的类型接收返回值
        net.minidev.json.JSONArray o3 = jsonContext.read(path3);
        java.util.LinkedHashMap o4 = jsonContext.read(path4);   // 很奇怪 o4 的类型是 Map 而不是 net.minidev.json.JSONObject


        // 如果想使用 Gson 的 JSON 动态类型，需要
        Configuration gsonConf = Configuration.builder()
                .jsonProvider(new GsonJsonProvider())
                .mappingProvider(new GsonMappingProvider())
                .build();
        DocumentContext gsonContext = JsonPath.using(gsonConf).parse(json);
        Object g1 = gsonContext.read(path1);   // class com.google.gson.JsonPrimitive
        Object g2 = gsonContext.read(path2);   // class com.google.gson.JsonObject
        com.google.gson.JsonArray g3 = gsonContext.read(path3);
        com.google.gson.JsonObject g4 = gsonContext.read(path4);


        // 如果想使用 Jackson 的 JSON 动态类型，需要
        Configuration jacksonConf = Configuration.builder()
                .jsonProvider(new JacksonJsonNodeJsonProvider()) // 注意：不是 JacksonJsonProvider
                .mappingProvider(new JacksonMappingProvider())
                .build();
        DocumentContext jacksonContext = JsonPath.using(jacksonConf).parse(json);
        Object k1 = jacksonContext.read(path1);   // class com.fasterxml.jackson.databind.node.TextNode
        Object k2 = jacksonContext.read(path2);   // class com.fasterxml.jackson.databind.node.ObjectNode
        com.fasterxml.jackson.databind.node.ArrayNode k3 = jacksonContext.read(path3);
        com.fasterxml.jackson.databind.node.ArrayNode k4 = jacksonContext.read(path4);
    }
}
