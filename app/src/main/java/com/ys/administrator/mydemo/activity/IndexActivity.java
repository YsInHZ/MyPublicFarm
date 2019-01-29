package com.ys.administrator.mydemo.activity;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.custom_view.MyFillDialog;
import com.ys.administrator.mydemo.fragment.IndexFragment;
import com.ys.administrator.mydemo.fragment.MineFragment;
import com.ys.administrator.mydemo.model.StatusListBean;
import com.ys.administrator.mydemo.presenter.CommonPresenter;
import com.ys.administrator.mydemo.util.NavigationBarUtil;
import com.ys.administrator.mydemo.util.SPUtil;
import com.ys.administrator.mydemo.util.StatusbarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页
 */
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
        if(NavigationBarUtil.hasNavigationBar(this)){
            NavigationBarUtil.initActivity(findViewById(android.R.id.content));
        }
        StatusbarUtils.enableTranslucentStatusbar(this);
        setContentView(R.layout.activity_index);
        ButterKnife.bind(this);

        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
        interceptKeyBack = true;
        initViewPager();
    }



    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(IndexFragment.newInstance());
        fragments.add(MineFragment.newInstance());
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
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setSelectedMain(i==0);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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
                vpMain.setCurrentItem(0);
                break;
            case R.id.llCenter:
                setSelectedMain(false);
                vpMain.setCurrentItem(1);
                break;
        }
    }
//    getContentResolver().registerContentObserver(Settings.System.getUriFor("navigationbar_is_min"), true, mNavigationStatusObserver);
//    private ContentObserver mNavigationStatusObserver = new ContentObserver(new Handler()) {
//        @Override
//        public void onChange(boolean selfChange) {
//            int navigationBarIsMin = Settings.System.getInt(getContentResolver(),
//                    "navigationbar_is_min", 0);
//            if (navigationBarIsMin == 1) {//导航键隐藏了
//                Log.e("导航键隐藏了", "-----");
//
//            } else {//导航键显示了
//                Log.e("导航键显示了", "-----");
//
//            }
//        }
//    };

    private void setSelectedMain(boolean selected){
        ivMain.setSelected(selected);
        tvMain.setSelected(selected);
        ivCenter.setSelected(!selected);
        tvCenter.setSelected(!selected);
    }
}
