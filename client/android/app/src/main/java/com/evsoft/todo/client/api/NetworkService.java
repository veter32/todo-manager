package com.evsoft.todo.client.api;

import com.evsoft.todo.client.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService instance;
    private final Retrofit retrofit;
    private TaskApi taskApi;

    private NetworkService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(BuildConfig.AUTH_TOKEN))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (instance == null) {
            instance = new NetworkService();
        }
        return instance;
    }

    public TaskApi getTaskApi() {
        if (taskApi == null) {
            taskApi = retrofit.create(TaskApi.class);
        }
        return taskApi;
    }
}
