package io.arusland.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by ruslan on 07.01.2017.
 */
public class BaseClientTest {
    protected TransportClient client;

    @Before
    public void before() throws UnknownHostException {
        Settings settings = Settings.builder()
                //.put("cluster.name", "elasticsearch")
                .build();
        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
    }

    @After
    public void after() {
        client.close();
    }
}
