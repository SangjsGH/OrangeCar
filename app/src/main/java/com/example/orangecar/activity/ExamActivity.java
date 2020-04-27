package com.example.orangecar.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.orangecar.R;
import com.example.orangecar.adapter.ViewpagerAdapter;
import com.example.orangecar.base.BaseActivity;
import com.example.orangecar.constant.URL;
import com.example.orangecar.fragment.Selectionfragment;
import com.example.orangecar.mode.CarExam;
import com.example.orangecar.okhttp.OkHttpClientManager;
import com.example.orangecar.okhttp.PostUtil;
import com.example.orangecar.until.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

public class ExamActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.exam_viewpager)
    ViewPager examViewpager;
    @BindView(R.id.tv_love)
    TextView tvLove;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_now_num)
    TextView tvNowNum;
    @BindView(R.id.tv_title_head)
    TextView tvTitleHead;
    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewpagerAdapter viewpagerAdapter;
    private List<CarExam.ResultBean> datalist = new ArrayList<>();
    private SpannableString mSpannableString;
    private int subject = 1;
    private String model;
    private String testType;
    private int type;
    private int second = 7200;//考试时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        ButterKnife.bind(this);
        initview();
        initData();
    }

    private void initData() {
        getdata();
    }


    private void initview() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            subject = bundle.getInt("subject", 1);
            model = bundle.getString("model");
            testType = bundle.getString("testType");
            type = bundle.getInt("type", 0);
        }
        if (type == 1) {
            handler.sendMessageDelayed(new Message(), 1000);
            tvTitleHead.setText("考试");
        }
    }

    private void initViewPager() {
        for (int i = 0; i < datalist.size(); i++) {
            Selectionfragment selectionfragment = new Selectionfragment();
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("exam", datalist.get(i));
            mBundle.putInt("position", i + 1);
            selectionfragment.setArguments(mBundle);
            fragmentList.add(selectionfragment);
            viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager(), fragmentList);
            examViewpager.setAdapter(viewpagerAdapter);
        }
        examViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String num = position + 1 + "/" + datalist.size();
                mSpannableString = new SpannableString(num);
                mSpannableString.setSpan(new RelativeSizeSpan(1.5f), 0, String.valueOf(position + 1).length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                tvNowNum.setText(mSpannableString);
                int i = Integer.parseInt(datalist.get(position).getAnswer());
                if (datalist.get(position).getItem4().isEmpty()) {
                    tvType.setText("判断题");
                } else if (i > 4) {
                    tvType.setText("多选题");
                } else {
                    tvType.setText("单选题");
                }
            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            second--;
            if (second > 0) {
                tvTitleHead.setText(getFormatTime(second));
                handler.sendMessageDelayed(new Message(), 1000);
            }
            return false;
        }
    });

    /**
     * 时间转换
     */
    public String getFormatTime(int time) {
        String timeStr = "";
        int minute = (time / 60);
        if (minute != 0) {
            timeStr = (time / 60) + "分";
        }
        timeStr = timeStr + time % 60 + "秒";
        return timeStr;
    }

    private void getdata() {
        HashMap<String, String> params = new HashMap<>();
        params.put("subject", subject + "");
        params.put("model", model);
        params.put("key", URL.KEY);
        params.put("testType", testType);

        PostUtil.get(this, URL.API, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                showtoast("00");
                Log.e("123", e + "");
            }

            @Override
            public void onResponse(String response) {
                if (response != null) {
                    String jsonstr = JsonUtils.getData(response);
                    datalist = JsonUtils.jsonToArrayList(jsonstr, CarExam.ResultBean.class);
                    initViewPager();
                }

            }
        });
    }

    @OnClick({R.id.img_back, R.id.tv_love})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_love:
                break;
        }
    }
}
