package io.arusland.elasticsearch;

import org.elasticsearch.action.get.GetResponse;
import org.junit.Test;

/**
 * Created by ruslan on 07.01.2017.
 */
public class ClientGetTest extends BaseClientTest {
    @Test
    public void testGert() {
        GetResponse response = client.prepareGet("twiter", "tweet", "1").get();

        System.out.println(response);
    }
}
