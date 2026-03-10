package com.evsoft.todo.client.api;

import android.util.Log;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TaskApi {
    @GET("tasks")
    Call<List<Task>> getTasks();
}

//Call<List<Task>> call = NetworkService.getInstance().getTaskApi().getTasks();
//        call.enqueue(new Callback<List<Task>>() {
//    @Override
//    public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
//        if (response.isSuccessful()) {
//            Log.d("TODOSERVER", "Found tasks: " + response.body().size());
//        } else {
//            Log.e("TODOSERVER", "Server error: " + response.code());
//        }
//    }
//
//    @Override
//    public void onFailure(Call<List<Task>> call, Throwable t) {
//        Log.e("TODOSERVER", "Network failure: " + t.getMessage());
//    }
//});
