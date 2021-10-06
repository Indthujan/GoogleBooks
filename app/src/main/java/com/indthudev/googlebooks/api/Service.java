package com.indthudev.googlebooks.api;

import com.indthudev.googlebooks.model.VolumeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("books/v1/volumes")
    Call<VolumeResponse> searchVolumes(
            @Query("q") String query,
            @Query("inauthor") String author);
}
