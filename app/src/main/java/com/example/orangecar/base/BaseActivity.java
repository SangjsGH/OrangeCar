package com.example.orangecar.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.example.orangecar.R;
import com.example.orangecar.until.LoadingUtils;
import com.example.orangecar.until.TipsToast;


public class BaseActivity extends AppCompatActivity {

    private Dialog mDialog;
    private TipsToast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor(this, R.color.white);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//黑色
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 状态栏
     * @param activity
     * @param statusColor
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        //取消状态栏透明
        // window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(statusColor));
        //设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        //让view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }
    /**
     * 跳转界面
     */
    public void goToActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
        // getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * 跳转界面
     */
    public void goToActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        //getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * 跳转界面
     */
    public void goToActivity(Class clazz, Bundle bundle, int code) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, code);
        //getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    /**
     * 加载对话框
     */
    public void showProgress(String title) {
        if (mDialog == null)
            mDialog = LoadingUtils.createLoadingDialog(this, title);
    }

    /**
     * 加载对话框
     */
    public void showProgress(int title) {
        if (mDialog == null) {
            mDialog = LoadingUtils.createLoadingDialog(this, getString(title));
        }
    }

    /**
     * 取消加载对话框
     */
    public void hideProgress() {
        if (mDialog != null && mDialog.isShowing()) {
            LoadingUtils.closeDialog(mDialog);
            mDialog = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);

        hideProgress();
        if (mDialog != null) {
            mDialog = null;
        }
    }

    public void showtoast(String msg) {
        if (toast == null) {
            toast = new TipsToast(this);
        }
        toast.setText(msg);
        toast.show();
    }
}
