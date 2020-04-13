package com.example.orangecar.base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.orangecar.okhttp.OkHttpClientManager;
import com.example.orangecar.until.LoadingUtils;
import com.example.orangecar.until.TipsToast;




public abstract class BaseFragment extends Fragment {

    private Dialog mDialog;
    private TipsToast toast;

    public abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle bundle);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        View view = initView(inflater, container, bundle);
        return view;
    }


    /**
     * 跳转界面
     */
    public void goToActivity(Class clazz) {
        startActivity(new Intent(getActivity(), clazz));
       // getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * 跳转界面
     */
    public void goToActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
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
        Intent intent = new Intent(getContext(), clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, code);
       // getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * 加载对话框
     */
    public void showProgress(String title) {
        if (mDialog == null)
            mDialog = LoadingUtils.createLoadingDialog(getActivity(), title);
    }

    /**
     * 加载对话框
     */
    public void showProgress(int title) {
        if (mDialog == null) {
            mDialog = LoadingUtils.createLoadingDialog(getActivity(), getString(title));
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
        OkHttpClientManager.getInstance().cancelTag(this);

        hideProgress();
        if (mDialog != null) {
            mDialog = null;
        }
    }

    public void showtoast(String msg) {
        if (toast == null) {
            toast = new TipsToast(getActivity());
        }
        toast.setText(msg);
        toast.show();
    }

}