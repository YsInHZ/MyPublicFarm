package com.ys.administrator.mydemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.activity.IndexActivity;
import com.ys.administrator.mydemo.activity.LoginActivity;
import com.ys.administrator.mydemo.activity.MineContactActivity;
import com.ys.administrator.mydemo.activity.MineFileActivity;
import com.ys.administrator.mydemo.activity.MinePersonalDataActivity;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.custom_view.MyFillDialog;
import com.ys.administrator.mydemo.model.BaseBean;
import com.ys.administrator.mydemo.model.StatusListBean;
import com.ys.administrator.mydemo.model.UserInfoDetialBean;
import com.ys.administrator.mydemo.util.Constant;
import com.ys.administrator.mydemo.util.DialogUtil;
import com.ys.administrator.mydemo.util.MD5;
import com.ys.administrator.mydemo.util.SPUtil;
import com.ys.administrator.mydemo.util.WeiXinUtil;

import java.util.HashMap;
import java.util.Map;

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
    MyFillDialog outDialog;
    MyFillDialog shareDialog;
    String jsonData ;
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
        initData();
        getUserDetialInfo();
        return view;
    }

    private void initData() {
        tvPhone.setText(Constant.getMobile());
        shareDialog = new MyFillDialog(getContext(),R.layout.dialog_share);
        View view = shareDialog.getView();
        DialogUtil.setFilleDialog(shareDialog);
        View hy = view.findViewById(R.id.hy);
        View pyq = view.findViewById(R.id.pyq);
        hy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
                WeiXinUtil.share(SendMessageToWX.Req.WXSceneSession);
            }
        });
        pyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
                WeiXinUtil.share(SendMessageToWX.Req.WXSceneTimeline);
            }
        });
    }

    /**
     * 获取用户信息
     */
    private void getUserDetialInfo() {

        MyModel.getNetData(MyModel.getRetrofitService().getUserDetialInfo(MyModel.getRequestHeaderMap("/user/my")), new ICallBack<UserInfoDetialBean>() {
            @Override
            public void onSuccess(UserInfoDetialBean data) {
                jsonData = JSON.toJSONString(data);
                String nickname = data.getUser().getNickname();
                tvName.setText(TextUtils.isEmpty(nickname)?"用户"+Constant.getMobile().substring(7):nickname);
                if(!TextUtils.isEmpty(data.getUser().getAvatar())){
                    Glide.with(getContext()).load(Constant.BitmapBaseUrl+data.getUser().getAvatar()).into(ciHead);
                }
            }

            @Override
            public void onFailure(String msg) {
                Log.d("", "onSuccess: ");
            }

            @Override
            public void onError() {
                Log.d("", "onSuccess: ");
            }

            @Override
            public void onComplete() {
                Log.d("", "onSuccess: ");
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ciHead, R.id.tvName, R.id.tvPhone, R.id.kfzx, R.id.wdwj, R.id.wdmb, R.id.fxyy, R.id.qchc,R.id.grzx,R.id.tvLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.ciHead:
//                break;
//            case R.id.tvName:
//                break;
//            case R.id.tvPhone:
//                break;
            case R.id.kfzx:
                ((BaseActivity)getContext()).openActivity(MineContactActivity.class);
                break;
            case R.id.wdwj:
                ((BaseActivity)getContext()).openActivity(MineFileActivity.class);
                break;
            case R.id.wdmb:
                break;
            case R.id.fxyy:
                shareDialog.show();
                break;
            case R.id.qchc:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"清除缓存成功",Toast.LENGTH_SHORT).show();
                    }
                },200);
                break;
            case R.id.grzx:
                Bundle bundle = new Bundle();
                bundle.putString("data",jsonData);
                ((BaseActivity)getContext()).openActivityWithResult(MinePersonalDataActivity.class,bundle,550);
                break;
            case R.id.tvLogout:
                // 注销
                if(outDialog==null){
                    initDialog();
                }
                outDialog.show();
                break;
        }
    }
    private void initDialog(){
        outDialog = new MyFillDialog(getContext(),R.layout.dialog_msg_twobutton);
        outDialog.setText(R.id.tvMsg,"是否退出当前账号？");
        View tvCancel = outDialog.findViewById(R.id.tvCancel);
        View tvSure = outDialog.findViewById(R.id.tvSure);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outDialog.dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.clearUserInfo();
                SPUtil.clearStore();
                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 550 && data!=null){
            jsonData = data.getStringExtra("data");
            UserInfoDetialBean infoDetialBean = JSON.parseObject(jsonData, UserInfoDetialBean.class);
            String nickname = infoDetialBean.getUser().getNickname();
            tvName.setText(TextUtils.isEmpty(nickname)?"用户"+Constant.getMobile().substring(7):nickname);
            if(!TextUtils.isEmpty(infoDetialBean.getUser().getAvatar())){
                Glide.with(getContext()).load(Constant.BitmapBaseUrl+infoDetialBean.getUser().getAvatar()).into(ciHead);
            }
        }
    }
}
