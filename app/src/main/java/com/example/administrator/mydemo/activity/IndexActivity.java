package com.example.administrator.mydemo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.mydemo.R;
import com.example.administrator.mydemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndexActivity extends BaseActivity {

    @BindView(R.id.vpMain)
    ViewPager vpMain;
    @BindView(R.id.ivNewProject)
    ImageView ivNewProject;
    @BindView(R.id.ivMain)
    ImageView ivMain;
    @BindView(R.id.tvMain)
    TextView tvMain;
    @BindView(R.id.llMain)
    LinearLayout llMain;
    @BindView(R.id.ivCenter)
    ImageView ivCenter;
    @BindView(R.id.tvCenter)
    TextView tvCenter;
    @BindView(R.id.llCenter)
    LinearLayout llCenter;

    FragmentStatePagerAdapter adapter;
    List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {
        fragments = new ArrayList<>();
//        fragments.add()
        adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        vpMain.setAdapter(adapter);
        setSelectedMain(true);
    }

    @Override
    public void showData(Object data) {

    }

    @OnClick({R.id.ivNewProject, R.id.llMain, R.id.llCenter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivNewProject:
                break;
            case R.id.llMain:

                setSelectedMain(true);
                break;
            case R.id.llCenter:
                setSelectedMain(false);
                break;
        }
    }

    private void setSelectedMain(boolean selected){
        ivMain.setSelected(selected);
        tvMain.setSelected(selected);
        ivCenter.setSelected(!selected);
        tvCenter.setSelected(!selected);
    }
}
