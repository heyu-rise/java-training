import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * @author heyu
 * @date 2021/7/4
 */
public class WithHttpClient {
    private static final String URL = "http://127.0.0.1:8801/test";

    public static void main(String[] args) {
        String response = httpRequest(URL);
        System.out.println(response);
    }

    public static String httpRequest(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpRequestBase httpRequestBase = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000)
                    .setConnectionRequestTimeout(1000).setSocketTimeout(30000).build();
            httpRequestBase.setConfig(requestConfig);
            // 执行get请求.
            CloseableHttpResponse response = httpClient.execute(httpRequestBase);
            return parseResponse(response);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String parseResponse(CloseableHttpResponse response) {
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            throw new RuntimeException("请求异常！");
        }
        String responseStr = null;
        try {
            responseStr = EntityUtils.toString(entity, "UTF-8");
            if (!Objects.equals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode())) {
                response.close();
                throw new RuntimeException("请求异常！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseStr;
    }


}
