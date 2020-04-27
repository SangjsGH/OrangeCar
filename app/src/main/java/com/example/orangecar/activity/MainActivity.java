package com.example.orangecar.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.orangecar.R;
import com.example.orangecar.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {


    @BindView(R.id.ctv_subject_one)
    CheckedTextView ctvSubjectOne;
    @BindView(R.id.ctv_subject_four)
    CheckedTextView ctvSubjectFour;
    @BindView(R.id.tv_car_text1)
    TextView tvCarText1;
    @BindView(R.id.tv_car_text2)
    TextView tvCarText2;
    @BindView(R.id.tv_truck_text1)
    TextView tvTruckText1;
    @BindView(R.id.tv_truck_text2)
    TextView tvTruckText2;
    @BindView(R.id.tv_passenger_text1)
    TextView tvPassengerText1;
    @BindView(R.id.tv_passenger_text2)
    TextView tvPassengerText2;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.rl_car)
    RelativeLayout rlCar;
    @BindView(R.id.rl_truck)
    RelativeLayout rlTruck;
    @BindView(R.id.rl_passenger)
    RelativeLayout rlPassenger;
    private int subject=1;
    private String model="c1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    private void setBottomSelect(int pos) {
        if (pos == 1) {
            rlCar.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_bottom_background));
            rlTruck.setBackgroundColor(getResources().getColor(R.color.init_color));
            rlPassenger.setBackgroundColor(getResources().getColor(R.color.init_color));
            model="c1";
        }else if (pos==2){
            rlTruck.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_bottom_background));
            rlPassenger.setBackgroundColor(getResources().getColor(R.color.init_color));
            rlCar.setBackgroundColor(getResources().getColor(R.color.init_color));
            model="a2";
        }else if (pos==3){
            rlPassenger.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_bottom_background));
            rlTruck.setBackgroundColor(getResources().getColor(R.color.init_color));
            rlCar.setBackgroundColor(getResources().getColor(R.color.init_color));
            model="b1";
        }
    }

    private void setTopSelect(int pos){
        if (pos==1){
            ctvSubjectOne.setChecked(true);
            ctvSubjectFour.setChecked(false);
            subject=1;
        }else {
            ctvSubjectOne.setChecked(false);
            ctvSubjectFour.setChecked(true);
            subject=4;
        }
    }
    @OnClick({R.id.ctv_subject_one, R.id.ctv_subject_four, R.id.rl_order, R.id.rl_exam, R.id.rl_random,
            R.id.rl_love, R.id.rl_exam_card, R.id.rl_car, R.id.rl_truck, R.id.rl_passenger, R.id.ll_side})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ctv_subject_one:
                setTopSelect(1);
                break;
            case R.id.ctv_subject_four:
                setTopSelect(2);
                break;
            case R.id.rl_order:
                Bundle bundle = new Bundle();
                bundle.putInt("subject",subject);
                bundle.putString("model",model);
                bundle.putString("testType","order");
                goToActivity(ExamActivity.class,bundle);
                break;
            case R.id.rl_exam:
                break;
            case R.id.rl_random:
                Bundle bundle1 = new Bundle();
                bundle1.putInt("subject",subject);
                bundle1.putString("model",model);
                bundle1.putString("testType","rand");
                goToActivity(ExamActivity.class,bundle1);
                break;
            case R.id.rl_love:
                break;
            case R.id.rl_exam_card:
                break;
            case R.id.rl_car:
                setBottomSelect(1);
                break;
            case R.id.rl_truck:
                setBottomSelect(2);
                break;
            case R.id.rl_passenger:
                setBottomSelect(3);
                break;
            case R.id.ll_side:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
        }
    }
}
