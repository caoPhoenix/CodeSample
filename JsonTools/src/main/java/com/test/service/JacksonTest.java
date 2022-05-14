package com.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class JacksonTest {

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

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        JsonNode jsonNode = null;
        try {
            // 把 JSON 文本解析成 JsonNode 对象
            jsonNode = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // 该 JSON 文本最顶层的 JSON 模型是一个 ArrayNode
        ArrayNode arrayNode = (ArrayNode) jsonNode;
        // ArrayNode 的第一项是一个 ObjectNode
        ObjectNode objectNode = (ObjectNode) arrayNode.get(0);
        // 该 objectNode 中 key 为 "name" 的值
        TextNode name = (TextNode) objectNode.get("name");
        // 该 objectNode 中 key 为 "age" 的值
        IntNode age = (IntNode) objectNode.get("age");

        // 往 ObjectNode 中添加键值对，或者修改 value
        objectNode.put("city", "beijing");
        objectNode.put("age", 25);

        // 往 ArrayNode 中删除一项，或者添加一项
        arrayNode.remove(1);
        arrayNode.add("a new item");
        // JsonNodeFactory.instance.*Node 方法可以创建各种 Node 对象
        arrayNode.add(JsonNodeFactory.instance.nullNode());

        // 直接 print 可以打印 JSON 文本
        System.out.println(jsonNode);

        // ArrayNode/ObjectNode/ObjectMapper 也可以创建各种 Node 对象
        TextNode textNode1 = arrayNode.textNode("test");
        ArrayNode arrayNode1 = objectMapper.createArrayNode();
        System.out.println(textNode1);
    }
}
