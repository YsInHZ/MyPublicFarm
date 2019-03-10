package com.ys.administrator.mydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.model.FileUpBean;
import com.ys.administrator.mydemo.model.UserInfoDetialBean;
import com.ys.administrator.mydemo.presenter.CommonPresenter;
import com.ys.administrator.mydemo.util.Constant;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 个人资料
 */
public class MinePersonalDataActivity extends BaseActivity {

    @BindView(R.id.ciHead)
    CircleImageView ciHead;
    @BindView(R.id.etNick)
    EditText etNick;
    @BindView(R.id.etPhone)
    TextView etPhone;

    UserInfoDetialBean infoDetialBean;
    @BindView(R.id.rbNan)
    RadioButton rbNan;
    @BindView(R.id.rbNv)
    RadioButton rbNv;

    String path = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_personal_data);
        ButterKnife.bind(this);
        initToolbar("个人资料", true);
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
        initData();

    }

    private void initData() {
        String data = getIntent().getStringExtra("data");
        infoDetialBean = JSON.parseObject(data, UserInfoDetialBean.class);
        String nickname = infoDetialBean.getUser().getNickname();
        if (!TextUtils.isEmpty(nickname)) {
            etNick.setText(nickname);
        }
        if (infoDetialBean.getUser().getGender() == 1) {
            rbNan.setChecked(true);
            rbNv.setChecked(false);
        } else if (infoDetialBean.getUser().getGender() == 2) {
            rbNan.setChecked(false);
            rbNv.setChecked(true);
        } else {
            rbNan.setChecked(false);
            rbNv.setChecked(false);
        }
        if (infoDetialBean.getUser().getMobile() != null) {
            etPhone.setText(infoDetialBean.getUser().getMobile());
        }
        if(!TextUtils.isEmpty(infoDetialBean.getUser().getAvatar())){
            if(infoDetialBean.getUser().getAvatar().indexOf("http")==-1){
                Glide.with(getContext()).load(Constant.BitmapBaseUrl+infoDetialBean.getUser().getAvatar()).into(ciHead);
            }else {
                Glide.with(getContext()).load(infoDetialBean.getUser().getAvatar()).into(ciHead);
            }

        }
        // 自定义图片加载器
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            if(checkInput()){
                showUpingDialog();
                if(path!=null){
                    upLoadImage();
                }else {
                    saveUserDetialInfo(infoDetialBean.getUser().getAvatar());
                }
            }
        }
        return false;
    }

    private boolean checkInput() {
        if(etNick.getText().toString().trim().isEmpty()){
            showToast("请输入昵称");
            return false;
        }
        if(!rbNan.isChecked() && !rbNv.isChecked()){
            showToast("请选择性别");
            return false;
        }
        return true;
    }

    @Override
    public void showData(Object data) {

    }

    private void upLoadImage(){
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        MyModel.getNetData(mContext,MyModel.getRetrofitService().uploadAavatar(MyModel.getRequestHeaderMap("/upload/avatar"), body), new ICallBack<FileUpBean>() {
            @Override
            public void onSuccess(FileUpBean data) {
                String avatar = data.getAvatar();
                saveUserDetialInfo(avatar);
            }

            @Override
            public void onFailure(String msg) {
                closeUpingDialog();
            }

            @Override
            public void onError() {
                closeUpingDialog();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void saveUserDetialInfo(String avatar){
        Map<String,String> map = new HashMap<>();
        map.put("id", Constant.getUserId()+"");
        map.put("nickname", etNick.getText().toString().trim()+"");
        map.put("gender",( rbNan.isChecked()?1:2)+"");
        map.put("avatar",avatar==null?"":avatar);
        MyModel.getNetData(mContext,MyModel.getRetrofitService().saveUserDetialInfo(MyModel.getRequestHeaderMap("/user/my"), MyModel.getJsonRequestBody(map)), new ICallBack<UserInfoDetialBean>() {
            @Override
            public void onSuccess(UserInfoDetialBean data) {
                Intent i = new Intent();
                i.putExtra("data",JSON.toJSONString(data));
                setResult(200,i);
                showToast("保存个人资料成功");
                finish();
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {
                closeUpingDialog();
            }
        });
    }
    @OnClick({R.id.ciHead, R.id.bChange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ciHead:
                // 自由配置选项
                ISListConfig config = new ISListConfig.Builder()
                        // 是否多选, 默认true
                        .multiSelect(false)
                        // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                        .rememberSelected(false)
                        // “确定”按钮背景色
                        .btnBgColor(Color.GRAY)
                        // “确定”按钮文字颜色
                        .btnTextColor(Color.BLUE)
                        // 使用沉浸式状态栏
                        .statusBarColor(Color.parseColor("#4996FF"))
                        // 返回图标ResId
                        .backResId(R.mipmap.img_back)
                        // 标题
                        .title("图片")
                        // 标题文字颜色
                        .titleColor(Color.WHITE)
                        // TitleBar背景色
                        .titleBgColor(Color.parseColor("#4996FF"))
                        // 裁剪大小。needCrop为true的时候配置
                        .cropSize(1, 1, 200, 200)
                        .needCrop(false)
                        // 第一个是否显示相机，默认true
                        .needCamera(false)
                        // 最大选择图片数量，默认9
                        .maxNum(1)
                        .build();
                    // 跳转到图片选择器
                ISNav.getInstance().toListActivity(this, config, 300);
                break;
            case R.id.bChange:
                Bundle bundle = new Bundle();
                bundle.putString("data",infoDetialBean.getUser().getMobile());
                openActivity(MinePersonalPhoneChangeActivity.class,bundle);

                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 300 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if(pathList!=null && pathList.size()>0){
                path = pathList.get(0);
                Glide.with(mContext).load(pathList.get(0)).into(ciHead);
            }
        }
    }
}
