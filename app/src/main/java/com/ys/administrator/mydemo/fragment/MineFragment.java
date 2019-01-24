package com.ys.administrator.mydemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.activity.MineContactActivity;
import com.ys.administrator.mydemo.activity.MinePersonalDataActivity;
import com.ys.administrator.mydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class MineFragment extends Fragment {

    @BindView(R.id.ciHead)
    CircleImageView ciHead;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    Unbinder unbinder;

    RecyclerView df;
    public MineFragment() {
    }


    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ciHead, R.id.tvName, R.id.tvPhone, R.id.kfzx, R.id.wdwj, R.id.wdmb, R.id.fxyy, R.id.qchc,R.id.grzx,R.id.tvLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ciHead:
                break;
            case R.id.tvName:
                break;
            case R.id.tvPhone:
                break;
            case R.id.kfzx:
                ((BaseActivity)getContext()).openActivity(MineContactActivity.class);
                break;
            case R.id.wdwj:
                break;
            case R.id.wdmb:
                break;
            case R.id.fxyy:
                break;
            case R.id.qchc:
                break;
            case R.id.grzx:
                ((BaseActivity)getContext()).openActivity(MinePersonalDataActivity.class);
                break;
            case R.id.tvLogout:
                //TODO 注销
                break;
        }
    }
}
