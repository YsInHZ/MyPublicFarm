package com.ys.administrator.mydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.adapter.CityAdapter;
import com.ys.administrator.mydemo.custom_view.SideBar;
import com.ys.administrator.mydemo.model.City;
import com.ys.administrator.mydemo.model.CityModel;
import com.ys.administrator.mydemo.util.CharacterParser;
import com.ys.administrator.mydemo.util.PinyinComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AreaChoiseActivity extends AppCompatActivity {

    private Context mContext;

    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private CityAdapter adapter;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<City> sourceDateList;
    List<String> arrayList;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    /**
     * 城市对应的省份id
     */
    private String provinceName;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        mContext=this;

        //获取activity跳转传递的信息
//        provinceId= (int) getIntent().getExtras().get("provinceId");
        provinceName= (String) getIntent().getExtras().get("provinceName");
        cityName= (String) getIntent().getExtras().get("cityName");
        initToolbar();
        initView();
        initData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent=new Intent();
        intent.putExtra("areaName","");
        intent.putExtra("cityName",cityName);
        intent.putExtra("provinceName",provinceName);
//                intent.putExtra("provinceName",provinceName);
        setResult(1, intent);
        finish();
        return false;
    }
    private void initData() {
        List<City> list=new ArrayList<>();
//        Gson gson=new Gson();
//        Type listType=new TypeToken<List<City>>(){}.getType();
//        list=gson.fromJson(data,listType);
        String data = getIntent().getStringExtra("data");
        List<String> arrayList = JSONArray.parseArray(data,String.class);
//        int i=0;
//        while (i<list.size()){
//            if(list.get(i).getPrinvcecode()==provinceId){
//                i++;
//            }else{
//                list.remove(i);
//            }
//        }
        for (String item:arrayList) {
            list.add(new City(item));
        }
        sourceDateList=filledData(list);
        //根据a-z进行排序源数据
        Collections.sort(sourceDateList,pinyinComparator);
        adapter=new CityAdapter(this,sourceDateList);
        //绑定适配器
        sortListView.setAdapter(adapter);
    }

    private void initView() {
        //实例化汉字转拼音类
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
                Intent intent=new Intent();
                intent.putExtra("areaName",((City)adapter.getItem(i)).getCityName());
                intent.putExtra("cityName",cityName);
                intent.putExtra("provinceName",provinceName);
//                intent.putExtra("provinceName",provinceName);
                setResult(1, intent);
                finish();
            }
        });
    }

    /**
     * 回调函数
     * @param requestCode
     * @param resultCode
     * @param data
     */
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

    /**
     * Volley加载数据
     */
    private void volley_get(){

    }

    /**
     * 为ListView填充数据
     * @param
     * @return
     */
    private List<City> filledData(List<City> list){
        List<City> mSortList = new ArrayList<City>();

        for(int i=0; i<list.size(); i++){
            City city = new City();
            city.setCityName(list.get(i).getCityName());
            city.setId(list.get(i).getId());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(list.get(i).getCityName());
            String sortString = pinyin.substring(0, 1).toUpperCase();//获取拼音首字母
            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                city.setSortLetters(sortString.toUpperCase());
            }else{
                city.setSortLetters("#");
            }

            mSortList.add(city);
        }
        return mSortList;

    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.img_back);
        toolbar.setNavigationContentDescription("测试");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AreaChoiseActivity.this.finish();
            }
        });

        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("选择区域");

    }
}
