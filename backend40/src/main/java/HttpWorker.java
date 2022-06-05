import DTO.HttpWorkerArgument;
import DTO.HttpWorkerResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HttpWorker {
    private CloseableHttpClient httpclient;

    public HttpWorker() {
        httpclient = HttpClients.createDefault();
    }

    public HttpWorkerResponse sendRequest(HttpWorkerArgument arg) {
        Double latency = null;
        Integer code = null;

        HttpPost post = new HttpPost(arg.getURL());
        try {
            post.setEntity(new StringEntity(arg.getBody()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        post.setHeader("content-type", "application/json");

        double time = System.nanoTime() / 1_000_000_000.0;

        try (CloseableHttpResponse response2 = httpclient.execute(post)) {
            latency = System.nanoTime() / 1_000_000_000.0 - time;
            code = response2.getStatusLine().getStatusCode();
            HttpEntity entity2 = response2.getEntity();
            EntityUtils.consume(entity2);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return new HttpWorkerResponse(latency, code);
    }

    public void close() {
        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
