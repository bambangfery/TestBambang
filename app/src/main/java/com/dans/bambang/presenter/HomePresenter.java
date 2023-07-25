package com.dans.bambang.presenter;

import android.os.Handler;

import com.dans.bambang.api.ApiClient;
import com.dans.bambang.api.ApiInterface;
import com.dans.bambang.model.JobListResp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {
    private View view;
    private ApiInterface mApiInterface;

    public HomePresenter(View view) {
        this.view = view;
    }

    public void getJobList(String desc, String loc, boolean fullTime){
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<JobListResp>> getJobList = mApiInterface.getListJob(desc,loc,fullTime);
        getJobList.enqueue(new Callback<List<JobListResp>>() {
            @Override
            public void onResponse(Call<List<JobListResp>> call, final Response<List<JobListResp>> response) {
                if (response.isSuccessful()){
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("test bambang resp : "+response.body());
                            view.getJobListResp(response.body());
                        }
                    },0);
                }else {
//                    Component.showSnackBar(getContext(),response.message(),"okay");

                }
            }

            @Override
            public void onFailure(Call<List<JobListResp>> call, Throwable t) {
                System.out.println("test bambang error : "+t.getLocalizedMessage());
//                Component.showSnackBar(getContext(),getContext().getResources().getString(R.string.internet_unreachable),"okay");
            }
        });
    }

    public void getJobListFilter(String desc, String loc, boolean fullTime, String page){
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<JobListResp>> getJobList = mApiInterface.getListJobLoadMore(desc,loc,fullTime,page);
        getJobList.enqueue(new Callback<List<JobListResp>>() {
            @Override
            public void onResponse(Call<List<JobListResp>> call, final Response<List<JobListResp>> response) {
                if (response.isSuccessful()){
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.getJobListLoadMore(response.body());
                        }
                    },0);
                }else {
//                    Component.showSnackBar(getContext(),response.message(),"okay");

                }
            }

            @Override
            public void onFailure(Call<List<JobListResp>> call, Throwable t) {
//                Component.showSnackBar(getContext(),getContext().getResources().getString(R.string.internet_unreachable),"okay");
            }
        });
    }

    public interface View{
        void getJobListResp(List<JobListResp> listResp);
        void getJobListLoadMore(List<JobListResp> listResp);
        void successLogin(String loginWith);
        void failureLogin();

    }
}
