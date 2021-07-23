import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class HelloClient {
    public static void main(String[] args) throws IOException {
        final BasicCookieStore cookieStore = new BasicCookieStore();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();
        HttpGet httpGet = new HttpGet("");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();

    }

}
