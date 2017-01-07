package io.arusland.elasticsearch;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.junit.Assert.assertTrue;

/**
 * Created by ruslan on 07.01.2017.
 */
public class ClientUpdateTest extends BaseClientTest {
    @Test
    public void testMap() throws IOException {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user", "kimchy");
        json.put("postDate", new Date());
        json.put("message", "trying out Elasticsearch");

        IndexResponse response = client.prepareIndex("twitter666", "tweet", "1")
                .setSource(json)
                .get();

        System.out.println(response);

        UpdateResponse updateResponce = client.prepareUpdate("twitter666", "tweet", "1")
                .setDoc(jsonBuilder()
                        .startObject()
                        .field("user", "arusland")
                        .endObject())
                .get();

        System.out.println(updateResponce);

        GetResponse getResponse = client.prepareGet("twitter666", "tweet", "1").get();

        assertTrue(getResponse.isExists());

        String result = getResponse.getSourceAsString();

        System.out.println(getResponse);
    }
}
