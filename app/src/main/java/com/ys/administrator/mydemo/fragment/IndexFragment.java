package com.ys.administrator.mydemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.adapter.IndexAdapter;
import com.ys.administrator.mydemo.custom_view.MyFillDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class IndexFragment extends Fragment {


    @BindView(R.id.vPoint)
    View vPoint;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.rvProject)
    RecyclerView rvProject;
    Unbinder unbinder;
    IndexAdapter adapter;
    MyFillDialog typeDialog;
    public IndexFragment() {
        // Required empty public constructor
    }


    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_index, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {
        adapter = new IndexAdapter(R.layout.item_indexproject,new ArrayList<String>(){{add("");add("");add("");add("");add("");}});
        rvProject.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProject.setAdapter(adapter);

        typeDialog = new MyFillDialog(getContext(),R.layout.dialog_itemchoise);
        RecyclerView rvDialog = (RecyclerView) typeDialog.findViewById(R.id.rvDialog);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rlMsg, R.id.ivSearch, R.id.llType, R.id.llPlan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlMsg:
                break;
            case R.id.ivSearch:
                break;
            case R.id.llType:
                typeDialog.show();
                break;
            case R.id.llPlan:
                typeDialog.show();
                break;
        }
    }
}
