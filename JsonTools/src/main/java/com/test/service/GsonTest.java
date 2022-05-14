package com.test.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class GsonTest {

    /**
     * <pre> json:
     * [
     *     {
     *         "name": "zhangsan",
     *         "age": 24
     *     },
     *     {
     *         "name": "lisi",
     *         "age": 30
     *     }
     * ]
     * </pre>
     */
    private static final String json = "[{\"name\": \"zhangsan\",\"age\": 24},{\"name\": \"lisi\",\"age\": 30}]";

    public static void main(String[] args) {
        // 把 JSON 文本解析成 JsonElement 对象
        JsonElement jsonElement = JsonParser.parseString(json);
        // 该 JSON 文本最顶层的 JSON 模型是一个 JsonArray
        JsonArray jsonArray = (JsonArray) jsonElement;
        // JsonArray 的第一项是一个 JsonObject
        JsonObject jsonObject = (JsonObject) jsonArray.get(0);
        // 该 JsonObject 中 key 为 "name" 的值
        JsonPrimitive name = (JsonPrimitive) jsonObject.get("name");
        // 该 JsonObject 中 key 为 "age" 的值
        JsonPrimitive age = (JsonPrimitive) jsonObject.get("age");

        // 往 JsonObject 中添加键值对，或者修改 value
        jsonObject.addProperty("city", "beijing");
        jsonObject.addProperty("age", 25);

        // 往 JsonArray 中删除一项，或者添加一项
        jsonArray.remove(1);
        jsonArray.add("first new item");

        // 各种 Json 模型对象可以直接 new 出来，null 则推荐 JsonNull.INSTANCE
        jsonArray.add(new JsonPrimitive("second new item"));

        // 直接 print 可以打印 JSON 文本
        System.out.println(jsonElement);
    }
}
