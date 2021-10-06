package com.indthudev.googlebooks.repository;

import com.indthudev.googlebooks.api.Service;
import com.indthudev.googlebooks.model.VolumeResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookRepository {

    private static final String BOOK_SERVICE_BASE_URL = "https://www.googleapis.com/";

    private Service service;
    private MutableLiveData<VolumeResponse> volumeResponseMutableLiveData;

    public BookRepository() {

        volumeResponseMutableLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        service = new retrofit2.Retrofit.Builder()
                .baseUrl(BOOK_SERVICE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Service.class);
    }

    public void searchVolumes(String keyword, String author) {
        service.searchVolumes(keyword, author)
                .enqueue(new Callback<VolumeResponse>() {
                    @Override
                    public void onResponse(Call<VolumeResponse> call, Response<VolumeResponse> response) {
                        if (response.body() != null) {
                            volumeResponseMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<VolumeResponse> call, Throwable t) {
                        volumeResponseMutableLiveData.postValue(null);
                    }
                });
    }

    public LiveData<VolumeResponse> getVolumesResponseLiveData() {
        return volumeResponseMutableLiveData;
    }

}
