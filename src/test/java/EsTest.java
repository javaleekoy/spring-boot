import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by peramdy on 2017/10/16.
 */
public class EsTest {

    private TransportClient client;

    @Before
    public void testBefore() {

        try {
            TransportAddress transportAddress_one = new InetSocketTransportAddress(InetAddress.getByName("192.168.136.130"), 19302);

            TransportAddress transportAddress_two = new InetSocketTransportAddress(InetAddress.getByName("192.168.136.130"), 19303);

            Settings settings = Settings.builder()
                    .put("cluster.name", "my_es")
                    /***##client.transport.sniff##为true会报错，问题还要查***/
                    .put("client.transport.sniff", false)
                    .build();

            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(transportAddress_one)
                    .addTransportAddress(transportAddress_two);

//            transportClient.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testOne() {
        GetResponse response = client.prepareGet()
                .setIndex("people")
                .setType("student")
                .setId("1")
                .get();

        Map<String, Object> map = response.getSource();
        System.out.println(map.toString());
    }

}
