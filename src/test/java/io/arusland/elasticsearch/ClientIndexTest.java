package io.arusland.elasticsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by ruslan on 07.01.2017.
 */
public class ClientIndexTest extends BaseClientTest {
    @Test
    public void testWithJsonBuilder() throws IOException {
        IndexResponse response = client.prepareIndex("twitter222", "tweet", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
                .get();

        System.out.println(response);
    }

    @Test
    public void testJsonString() {
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";

        IndexResponse response = client.prepareIndex("twitter333", "tweet", "1")
                .setSource(json)
                .get();

        System.out.println(response);
    }

    @Test
    public void testMap() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user", "kimchy");
        json.put("postDate", new Date());
        json.put("message", "trying out Elasticsearch");

        IndexResponse response = client.prepareIndex("twitter444", "tweet", "1")
                .setSource(json)
                .get();

        System.out.println(response);
    }

    @Test
    public void testJackson() throws JsonProcessingException {
        Calendar cal = Calendar.getInstance();
        cal.set(1985, Calendar.SEPTEMBER, 10);
        TestBean bean = new TestBean("Ruslan Absalyamov {\"arusland\"}", 31, cal.getTime());
        ObjectMapper mapper = new ObjectMapper();

        byte[] json = mapper.writeValueAsBytes(bean);

        IndexResponse response = client.prepareIndex("twitter555", "tweet", "1")
                .setSource(json)
                .get();

        System.out.println(response);
    }
}
