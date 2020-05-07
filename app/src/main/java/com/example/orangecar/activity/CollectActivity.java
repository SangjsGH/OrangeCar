package com.example.orangecar.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.orangecar.R;
import com.example.orangecar.base.BaseActivity;
import com.example.orangecar.base.MyApplication;
import com.example.orangecar.greendao.CollectDao;
import com.example.orangecar.mode.Collect;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectActivity extends BaseActivity {
    @BindView(R.id.tv_title_head)
    TextView tvTitleHead;
    @BindView(R.id.index_viewpager)
    ViewPager indexViewpager;
    @BindView(R.id.noitemview)
    LinearLayout noitemview;

    private CollectDao collectDao;
    private List<Collect> datalist_collect = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        collectDao= MyApplication.getDaoSession().getCollectDao();
    }

    private void initData() {
        datalist_collect = collectDao.queryBuilder().list();
        showtoast(datalist_collect.size()+"");
    }

    @OnClick({R.id.img_back, R.id.tv_show, R.id.rl_delete, R.id.tv_do})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.tv_show:
                break;
            case R.id.rl_delete:
                break;
            case R.id.tv_do:
                break;
        }
    }
}
