package com.dans.bambang.api;


import com.dans.bambang.model.JobListResp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("positions.json")
    Call<List<JobListResp>> getListJob(@Query("description") String desc,
                                       @Query("location") String location,
                                       @Query("full_time") boolean fullTime);

    @GET("positions.json")
    Call<List<JobListResp>> getListJobLoadMore(@Query("description") String desc,
                                               @Query("location") String location,
                                               @Query("full_time") boolean fullTime,
                                               @Query("page") String page);

}
