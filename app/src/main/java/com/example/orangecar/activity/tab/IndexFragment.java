package com.example.orangecar.activity.tab;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.orangecar.R;
import com.example.orangecar.activity.ExamActivity;
import com.example.orangecar.base.BaseFragment;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndexFragment extends BaseFragment {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.img_suiji)
    ImageView imgSuiji;
    @BindView(R.id.rl_suiji)
    RelativeLayout rlSuiji;
    @BindView(R.id.img_love)
    ImageView imgLove;
    @BindView(R.id.rl_love)
    RelativeLayout rlLove;
    @BindView(R.id.img_shunxu)
    ImageView imgShunxu;
    @BindView(R.id.tv_shunxu)
    TextView tvShunxu;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.rl_shunxu)
    RelativeLayout rlShunxu;
    @BindView(R.id.img_exam)
    ImageView imgExam;
    @BindView(R.id.tv_exam)
    TextView tvExam;
    @BindView(R.id.rl_exam)
    RelativeLayout rlExam;
    @BindView(R.id.img_pinfen)
    ImageView imgPinfen;
    @BindView(R.id.rl_pinfen)
    RelativeLayout rlPinfen;
    @BindView(R.id.img_reset)
    ImageView imgReset;
    @BindView(R.id.rl_reset)
    RelativeLayout rlReset;
    private List<Integer> localImages = new ArrayList<>();

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        ButterKnife.bind(this, view);
        //initview();
        return view;
    }


    private void initview() {
        localImages.clear();
        localImages.add(R.drawable.banner_test1);
        localImages.add(R.drawable.banner_test2);
        //设置图片加载器
        banner.setImageLoader(new MyLoader());
        //设置图片集合
        banner.setImages(localImages);
        //设置轮播时间
        banner.setDelayTime(5000);
        banner.start();
    }

    @OnClick({R.id.rl_suiji, R.id.rl_love, R.id.rl_shunxu, R.id.rl_exam, R.id.rl_pinfen, R.id.rl_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_suiji:
                break;
            case R.id.rl_love:
                break;
            case R.id.rl_shunxu:
                goToActivity(ExamActivity.class);
                break;
            case R.id.rl_exam:

                break;
            case R.id.rl_pinfen:
                break;
            case R.id.rl_reset:
                break;
        }
    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((int) path).into(imageView);
        }
    }


}
