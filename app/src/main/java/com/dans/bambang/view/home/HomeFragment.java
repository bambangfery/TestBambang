package com.dans.bambang.view.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dans.bambang.R;
import com.dans.bambang.databinding.FragmentHomeBinding;
import com.dans.bambang.databinding.LayoutFooterBinding;
import com.dans.bambang.databinding.LayoutHeadBinding;
import com.dans.bambang.model.JobListResp;
import com.dans.bambang.presenter.HomePresenter;
import com.dans.bambang.presenter.LoginPresenter;
import com.dans.bambang.utils.SwipeRefreshLayout;
import com.dans.bambang.view.adapter.HomeAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeFragment extends Fragment implements HomePresenter.View {
    private FragmentHomeBinding binding;
    private LayoutHeadBinding headBinding;
    private LayoutFooterBinding footerBinding;
    private RecyclerView rvHome;
    public LinearLayoutManager layoutManager;
    private ProgressBar headerProgressBar, footerProgressBar;
    private TextView headerTextView, footerTextView;
    private ImageView headerImageView, footerImageView;
    private HomePresenter presenter;
    private HomeAdapter adapter;

    @NotNull
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initView();
        declareSwipeRefreshLayout();
        presenter = new HomePresenter(this);
        presenter.getJobList("","",false);
        binding.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.show.setVisibility(View.GONE);
                binding.hide.setVisibility(View.VISIBLE);
                binding.lyFilter.setVisibility(View.VISIBLE);
            }
        });

        binding.hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.show.setVisibility(View.VISIBLE);
                binding.hide.setVisibility(View.GONE);
                binding.lyFilter.setVisibility(View.GONE);
            }
        });
    }

    private void initView(){
        binding.rvList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        binding.rvList.setLayoutManager(layoutManager);
        binding.swipeRefresh.setHeaderViewBackgroundColor(getResources().getColor(R.color.whitef1));
        binding.swipeRefresh.setHeaderView(createHeaderView());
        binding.swipeRefresh.setFooterView(createFooterView());
        binding.swipeRefresh.setTargetScrollWithLayout(true);
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(binding.swipeRefresh.getContext())
                .inflate(R.layout.layout_head, null);
        headerProgressBar = headerView.findViewById(R.id.pb_view);
        headerTextView =  headerView.findViewById(R.id.text_view);
        headerTextView.setText(getResources().getString(R.string.refresh));
        headerImageView =  headerView.findViewById(R.id.image_view);
        headerImageView.setVisibility(View.VISIBLE);
        headerProgressBar.setVisibility(View.GONE);
        return headerView;
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(binding.swipeRefresh.getContext())
                .inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = footerView
                .findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerTextView.setText(getResources().getString(R.string.loading));
        return footerView;
    }

    private void hiddenHeaderRefreshLayout(){
        binding.swipeRefresh.setVisibility(View.VISIBLE);
        binding.swipeRefresh.setRefreshing(false);
        headerProgressBar.setVisibility(View.GONE);
    }

    private void hiddenFooterRefreshLayout(){
        binding.swipeRefresh.setVisibility(View.VISIBLE);
        footerImageView.setVisibility(View.VISIBLE);
        footerProgressBar.setVisibility(View.GONE);
        binding.swipeRefresh.setLoadMore(false);
    }

    private void declareSwipeRefreshLayout() {
        binding.swipeRefresh.setOnPullRefreshListener(new SwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                headerTextView.setText(getResources().getString(R.string.refreshing));
                headerImageView.setVisibility(View.GONE);
                headerProgressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hiddenHeaderRefreshLayout();
                        presenter.getJobList("","",false);
                    }
                },3000);
            }

            @Override
            public void onPullDistance(int distance) {
            }

            @Override
            public void onPullEnable(boolean enable) {
                headerTextView.setText(enable ? getResources().getString(R.string.refresh_release) : getResources().getString(R.string.pull_down_to_refresh));
                headerImageView.setVisibility(View.VISIBLE);
                headerImageView.setRotation(enable ? 180 : 0);
            }
        });

        binding.swipeRefresh.setOnPushLoadMoreListener(new SwipeRefreshLayout.OnPushLoadMoreListener() {

            @Override
            public void onLoadMore() {
                footerTextView.setText(getResources().getString(R.string.loading));
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMoreListJob();
                        hiddenFooterRefreshLayout();
                    }
                },3000);
            }

            @Override
            public void onPushEnable(boolean enable) {
                footerTextView.setText(enable ? getResources().getString(R.string.load_more) : getResources().getString(R.string.pull_up_to_load_more));
                footerImageView.setVisibility(View.VISIBLE);
                footerImageView.setRotation(enable ? 0 : 180);
            }

            @Override
            public void onPushDistance(int distance) {

            }

        });
    }

    public void loadMoreListJob(){
        presenter.getJobListFilter("","",false,"1");
    }

    @Override
    public void getJobListResp(List<JobListResp> listResp) {
        adapter = new HomeAdapter(listResp, getContext());
        binding.rvList.setAdapter(adapter);
    }

    @Override
    public void getJobListLoadMore(List<JobListResp> listResp) {
        adapter.addList(listResp);
    }

    @Override
    public void successLogin(String loginWith) {

    }

    @Override
    public void failureLogin() {

    }
}