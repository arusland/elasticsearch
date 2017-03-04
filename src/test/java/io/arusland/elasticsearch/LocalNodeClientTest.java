package io.arusland.elasticsearch;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeValidationException;
import org.elasticsearch.search.SearchHit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.junit.Assert.assertTrue;

/**
 * @author Ruslan Absalyamov
 * @since 2017-03-04
 */
public class LocalNodeClientTest {
    private Client client;
    private Node node;

    @Before
    public void beforeTest() throws NodeValidationException, InterruptedException {
        Settings settings = Settings.builder()
                .put("node.name", "local search node!")
                .put("path.home", "testelasticdata")
                .put("transport.type", "local")
                .put("http.enabled", "false")
                .build();
        node = new Node(settings).start();
        client = node.client();
        waitWorkableStatus();
    }

    @After
    public void afterTest() throws IOException {
        node.close();
    }

    @Test
    public void testIndex() throws IOException {
        System.out.println("client: " + client);

        XContentBuilder json = jsonBuilder()
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
                .endObject();

        System.out.println(json.string());

        IndexResponse response = client.prepareIndex("twitter777", "tweet", "1")
                .setSource(json)
                .get();

        System.out.println(response);
    }

    @Test
    public void testSearch() {
        SearchResponse response = client.prepareSearch("twitter777")
                .setTypes("tweet")
                .setQuery(QueryBuilders.matchQuery("user", "Malik"))
                .get();

        System.out.println(response);

        assertTrue(response.getHits().getTotalHits() > 0);
        Iterator<SearchHit> it = response.getHits().iterator();

        while (it.hasNext()) {
            System.out.println(it.next().getSourceAsString());
        }
    }

    private void waitWorkableStatus() throws InterruptedException {
        AdminClient adminClient = client.admin();
        ClusterHealthResponse healthResponse;

        do {
            healthResponse = adminClient.cluster()
                    .prepareHealth("*")
                    .get();

            System.out.println("wait workable status, current: " + healthResponse.getStatus());
            Thread.sleep(200L);
        } while (healthResponse.getStatus() == ClusterHealthStatus.RED);
    }
}
