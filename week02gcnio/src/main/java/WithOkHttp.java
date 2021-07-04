import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

/**
 * @author heyu
 * @date 2021/7/4
 */
public class WithOkHttp {
    private static final String URL = "http://127.0.0.1:8801/test";

    public static void main(String[] args) throws IOException {
        sync();
        async();
    }

    /**
     * 同步请求
     * @throws IOException
     */
    private static void sync() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(URL).get().build();
        Response response = okHttpClient.newCall(request).execute();
        String res = Objects.requireNonNull(response.body()).string();
        System.out.println(res);
    }

    /**
     * 异步请求
     */
    private static void async() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(URL).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                throw new RuntimeException("请求异常！");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res = Objects.requireNonNull(response.body()).string();
                System.out.println(res + "async");
            }
        });

    }

}
