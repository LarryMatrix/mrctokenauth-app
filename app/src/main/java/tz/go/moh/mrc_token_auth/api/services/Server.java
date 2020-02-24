

package tz.go.moh.mrc_token_auth.api.services;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {

    private static final String API_URL = "http://196.192.73.42/api/mobile-app/v1/";

    private Retrofit retrofit;
    private static Server server;

    private Server() {
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("Accept", "application/json")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("Content-Type", "application/json")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        retrofit = new Retrofit
                .Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static synchronized Server getServerInstance () {
        if (server == null) {
            server = new Server();
        }
        return server;
    }

    public UserClient getUserClient() {
        return retrofit.create(UserClient.class);
    }

}
