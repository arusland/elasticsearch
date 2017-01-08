package io.arusland.elasticsearch;

import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by ruslan on 08.01.2017.
 */
public class JsonTest {
    @Test
    public void testNestedObject() throws IOException {
        String json = jsonBuilder()
                .startObject()
                .field("user", "Malik")
                .field("postDate", new Date())
                .field("message", "trying out Elasticsearch")
                .field("info")
                .startObject()
                    .field("age", 23)
                    .field("created", new Date())
                    .field("inner")
                    .startObject()
                        .field("abc", "xyz")
                        .field("ddd", 12.5D)
                    .endObject()
                .endObject()
                .endObject().string();

        System.out.println(json);
    }

    @Test
    public void testObjectWithArray() throws IOException {
        String json = jsonBuilder()
                .startObject()
                .field("user", "Malik")
                .field("postDate", new Date())
                .field("message", "trying out Elasticsearch")
                .startArray("group")
                    .startObject()
                        .field("name", "Corasr1")
                        .field("age", 45)
                    .endObject()
                    .startObject()
                        .field("name", "Max")
                        .field("age", 24)
                    .endObject()
                .endArray()
                .endObject().string();

        System.out.println(json);
    }
}
