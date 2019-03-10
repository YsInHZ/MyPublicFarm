package com.ys.administrator.mydemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.adapter.ProvinceAdapter;
import com.ys.administrator.mydemo.custom_view.SideBar;
import com.ys.administrator.mydemo.model.CityModel;
import com.ys.administrator.mydemo.model.Province;
import com.ys.administrator.mydemo.util.CharacterParser;
import com.ys.administrator.mydemo.util.PinyinComparator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *地址选择-省份级别
 */
public class ProvinceActivity extends AppCompatActivity {

    List<Province> mSortList ;
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private ProvinceAdapter adapter;
    private static final String TAG = "ProvinceActivity";

    /**汉字转换成拼音的类*/
    private CharacterParser characterParser;
    private List<Province> sourceDateList;
    List<CityModel> cityModel;
    /**根据拼音来排列ListView里面的数据类*/
    private PinyinComparator pinyinComparator;

    private String data = "[{\"id\":\"11\",\"provinceName\":\"北京市\"},{\"id\":\"12\",\"provinceName\":\"天津市\"},{\"id\":\"13\",\"provinceName\":\"河北省\"},{\"id\":\"14\",\"provinceName\":\"山西省\"},{\"id\":\"15\",\"provinceName\":\"内蒙古自治区\"},{\"id\":\"21\",\"provinceName\":\"辽宁省\"},{\"id\":\"22\",\"provinceName\":\"吉林省\"},{\"id\":\"23\",\"provinceName\":\"黑龙江省\"},{\"id\":\"31\",\"provinceName\":\"上海市\"},{\"id\":\"32\",\"provinceName\":\"江苏省\"},{\"id\":\"33\",\"provinceName\":\"浙江省\"},{\"id\":\"34\",\"provinceName\":\"安徽省\"},{\"id\":\"35\",\"provinceName\":\"福建省\"},{\"id\":\"36\",\"provinceName\":\"江西省\"},{\"id\":\"37\",\"provinceName\":\"山东省\"},{\"id\":\"41\",\"provinceName\":\"河南省\"},{\"id\":\"42\",\"provinceName\":\"湖北省\"},{\"id\":\"43\",\"provinceName\":\"湖南省\"},{\"id\":\"44\",\"provinceName\":\"广东省\"},{\"id\":\"45\",\"provinceName\":\"广西壮族自治区\"},{\"id\":\"46\",\"provinceName\":\"海南省\"},{\"id\":\"50\",\"provinceName\":\"重庆市\"},{\"id\":\"51\",\"provinceName\":\"四川省\"},{\"id\":\"52\",\"provinceName\":\"贵州省\"},{\"id\":\"53\",\"provinceName\":\"云南省\"},{\"id\":\"54\",\"provinceName\":\"西藏自治区\"},{\"id\":\"61\",\"provinceName\":\"陕西省\"},{\"id\":\"62\",\"provinceName\":\"甘肃省\"},{\"id\":\"63\",\"provinceName\":\"青海省\"},{\"id\":\"64\",\"provinceName\":\"宁夏回族自治区\"},{\"id\":\"65\",\"provinceName\":\"新疆维吾尔自治区\"},{\"id\":\"71\",\"provinceName\":\"台湾省\"},{\"id\":\"81\",\"provinceName\":\"香港特别行政区\"},{\"id\":\"82\",\"provinceName\":\"澳门特别行政区\"}]\n";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        initToolbar();
        initView();
        initData();

    }




    private void initView() {
        //实例化汉字转拼音类
        mSortList = new ArrayList<Province>();
        characterParser=CharacterParser.getInstance();

        pinyinComparator=new PinyinComparator();

        sideBar= (SideBar) findViewById(R.id.sidrbar);
        dialog= (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position=adapter.getPositionForSection(s.charAt(0));
                if(position!=-1)
                {
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView= (ListView) findViewById(R.id.lv_pro);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String provinceName = ((Province) adapter.getItem(i)).getProvinceName();
                    if("香港特别行政区".equals(provinceName)||"澳门特别行政区".equals(provinceName)||((Province) adapter.getItem(i-1)).getId()==-1){
                        Intent intent=new Intent();
                        intent.putExtra("cityName",provinceName);
                        setResult(1, intent);
                        finish();
                    }else{
                        List<CityModel.CityBean> city = new ArrayList<>();
                        for (CityModel item :cityModel) {
                            if(item.getName().equals(provinceName)){
                               city = item.getCity();
                               break;
                            }
                        }
                        Intent intent=new Intent();
                        intent.putExtra("data", JSONArray.toJSONString(city));
                        intent.putExtra("provinceName",provinceName);
                        intent.setClass(ProvinceActivity.this,CityChoiseActivity.class);
                        startActivityForResult(intent,0);


                }


            }
        });
//        View v = getLayoutInflater().inflate(R.layout.gps_list_head,null);
//        TextView tv = (TextView) v.findViewById(R.id.city);
//        tv.setText(SharedPreference.getInstance().getInfo(SharedPreference.FILE_GPS,this,SharedPreference.GPS_CITY));
//        sortListView.addHeaderView(v);

    }

    private void initData() {
        Log.i(TAG, "initData: "+readRaw());
        List<Province> list=new ArrayList<>();
        Gson gson=new Gson();
       cityModel = JSONArray.parseArray(readRaw(), CityModel.class);
        Type listType=new TypeToken<List<Province>>(){}.getType();
         list=gson.fromJson(data,listType);
        sourceDateList=filledData(cityModel);
//        sourceDateList=filledData(list);
        //根据a-z进行排序源数据
        Collections.sort(sourceDateList,pinyinComparator);
        adapter=new ProvinceAdapter(this,sourceDateList);
        //绑定适配器
        sortListView.setAdapter(adapter);

    }
    private String readRaw(){
        String  result = null;
        InputStream inputstream = getResources().openRawResource(R.raw.city);
        try {
            InputStreamReader reader = new InputStreamReader(inputstream,"UTF-8");
            BufferedReader br = new BufferedReader(reader);
            String str = null;
            StringBuffer sb = new StringBuffer();
            while ((str=br.readLine())!=null){
                sb.append(str);
            }
            result = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 为ListView填充数据
     * @param
     * @return
     */
//    private List<Province> filledData(List<Province> list){
//
//
//
//        //TODO 添加热门城市
//
//        for(int i=0; i<list.size(); i++){
//            Province province = new Province();
//            province.setProvinceName(list.get(i).getProvinceName());
//            province.setId(list.get(i).getId());
//            //汉字转换成拼音
//            String pinyin = characterParser.getSelling(list.get(i).getProvinceName());
//            String sortString = pinyin.substring(0, 1).toUpperCase();//获取拼音首字母
//            // 正则表达式，判断首字母是否是英文字母
//            if(sortString.matches("[A-Z]")){
//                province.setSortLetters(sortString.toUpperCase());
//            }else{
//                province.setSortLetters("#");
//            }
//
//            mSortList.add(province);
//        }
//        return mSortList;
//
//    }
    private List<Province> filledData(List<CityModel> list){



        //TODO 添加热门城市

        for(int i=0; i<list.size(); i++){
            Province province = new Province();
            province.setProvinceName(list.get(i).getName());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(list.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();//获取拼音首字母
            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                province.setSortLetters(sortString.toUpperCase());
            }else{
                province.setSortLetters("#");
            }

            mSortList.add(province);
        }
        return mSortList;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0)
        {
            if(resultCode==1)
            {
                setResult(1,data);
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
//        toolbar.setSubtitleTextColor(Color.parseColor("#3d90d7"));
//        toolbar.setTitleTextColor(Color.parseColor("#3d90d7"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.img_back);
        toolbar.setNavigationContentDescription("测试");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProvinceActivity.this.finish();
            }
        });

        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("选择省份");

    }
}
