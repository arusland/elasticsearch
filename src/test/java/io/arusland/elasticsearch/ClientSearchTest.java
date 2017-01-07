package io.arusland.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertTrue;

/**
 * Created by ruslan on 07.01.2017.
 */
public class ClientSearchTest extends BaseClientTest {
    @Test
    public void testSearchAll() {
        SearchResponse response = client.prepareSearch().get();

        System.out.println(response);
    }

    @Test
    public void testSearchComplex() {
        SearchResponse response = client.prepareSearch("twitter222", "twitter555")
                .setTypes("tweet")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchQuery("name", "Ruslan"))
                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(35))
                .setFrom(0).setSize(60).setExplain(true)
                .get();

        System.out.println(response);

        assertTrue(response.getHits().getTotalHits() > 0);
        Iterator<SearchHit> it = response.getHits().iterator();

        while (it.hasNext()) {
            System.out.println(it.next().getSourceAsString());
        }
    }
}
