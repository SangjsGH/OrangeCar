package com.example.orangecar.fragment;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orangecar.R;
import com.example.orangecar.adapter.MyRecyAdapter;
import com.example.orangecar.adapter.MyViewHolder;
import com.example.orangecar.base.BaseFragment;
import com.example.orangecar.mode.CarExam;
import com.example.orangecar.mode.Selection;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Selectionfragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.recycle_seletion)
    RecyclerView recyclerView;
    @BindView(R.id.iv_ques)
    ImageView ivQues;
    @BindView(R.id.tv_jiexi)
    HtmlTextView tvJiexi;
    @BindView(R.id.rl_jiexi)
    RelativeLayout rlJiexi;
    @BindView(R.id.tv_answer)
    TextView tvAnswer;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    private Unbinder bind;
    private List<Selection> selectList = new ArrayList<>();
    private CarExam.ResultBean carExam;
    private List<CarExam.ResultBean> dataList = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();
    private String selectpos = "";
    private int pos;
    private String answer;
    private Selection selection;
    private int type;//选择题 1  判断题 2 多选题 3
    private MyRecyAdapter adapter;
    private int anser_num;


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_seletion, null);
        bind = ButterKnife.bind(this, view);
        initData();
        initView();
        initAdapter();
        return view;
    }

    private void initView() {
        tvTitle.setText(carExam.getQuestion());
        if (carExam.getUrl().isEmpty()) {
            ivQues.setVisibility(View.GONE);
        } else {
            Glide.with(getActivity()).load(carExam.getUrl()).into(ivQues);
        }
        tvJiexi.setHtml("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + carExam.getExplains());
        anser_num = Integer.parseInt(carExam.getAnswer());
        if (carExam.getItem4().isEmpty()) {
            type = 2;
            if (carExam.getAnswer().equals("1")) {
                answer = "正确";
            } else if (carExam.getAnswer().equals("2")) {
                answer = "错误";
            }
        } else if (anser_num > 4) {
            type = 3;
            answer = getAnswer(anser_num);
            tvAgree.setVisibility(View.VISIBLE);
        } else {
            type = 1;
            answer = getAnswer(anser_num);
        }
        tvAnswer.setText("                       " + answer);

    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            carExam = (CarExam.ResultBean) bundle.getSerializable("exam");
            pos = bundle.getInt("position", 0);
        }
        stringList.clear();
        selectList.clear();
        if (carExam.getItem4().isEmpty()) {
            stringList.add("A.  正确");
            stringList.add("B.  错误");
        } else {
            stringList.add(carExam.getItem1());
            stringList.add(carExam.getItem2());
            stringList.add(carExam.getItem3());
            stringList.add(carExam.getItem4());
        }
        for (String str : stringList) {
            selectList.add(new Selection(str, 0, ""));
        }
    }

    private void initAdapter() {
        adapter = new MyRecyAdapter<Selection>(getActivity(), selectList, R.layout.item_seletion) {
            @Override
            public void onBindItem(RecyclerView.ViewHolder holder, int position, Selection item) {
                super.onBindItem(holder, position, item);
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.ctvTitle.setText(item.getSelection());
                if (type == 1 || type == 2) {//单选 判断
                    if (selectList.get(position).getStatus() == 1) {
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_select_true);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        viewHolder.ctvTitle.setCompoundDrawables(drawable, null, null, null);
                    } else if (selectList.get(position).getStatus() == -1) {
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_select_false);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        viewHolder.ctvTitle.setCompoundDrawables(drawable, null, null, null);
                    }
                } else {//多选
                    if (selectList.get(position).getStatus() == 3) {
                        viewHolder.ctvTitle.setChecked(true);
                    } else {
                        viewHolder.ctvTitle.setChecked(false);
                    }
                }


                viewHolder.ctvTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (type == 1 || type == 2) {
                            for (Selection selection : selectList) {
                                if (selection.getStatus() != 0) {
                                    return;
                                }
                            }
                            setJudge(position);
                            rlJiexi.setVisibility(View.VISIBLE);
                        } else if (type == 3) {
                            if (selectList.get(position).getStatus() == 3) {
                                selectList.get(position).setStatus(0);
                                selectpos.replace(getAnswer(position),"");
                                adapter.notifyDataSetChanged();
                                return;
                            }else if (selectList.get(position).getStatus() == 4){
                                return;
                            }
                            switch (position) {
                                case 0:
                                    selectpos += "A";
                                    break;
                                case 1:
                                    selectpos += "B";
                                    break;
                                case 2:
                                    selectpos += "C";
                                    break;
                                case 3:
                                    selectpos += "D";
                                    break;
                            }
                            selectList.get(position).setStatus(3);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }

            @Override
            protected MyViewHolder setComViewHolder(View view, int viewType) {
                return new ViewHolder(view);
            }

        };

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }

    @OnClick(R.id.tv_agree)
    public void onViewClicked() {
        if (isTrueanswer()) {
            showtoast("答案正确");
        } else {
            showtoast("答案错误");
        }
        for (int i = 0; i <selectList.size() ; i++) {
            selectList.get(i).setStatus(4);
        }
        rlJiexi.setVisibility(View.VISIBLE);

    }

    private boolean isTrueanswer() {
        boolean isanswer = false;
        int pos = selectpos.length();
        if (answer.length() == pos){
            for (int i = 0; i < pos; i++) {
                String s = selectpos.substring(i, i + 1);
                if (answer.contains(s)) {
                    isanswer = true;
                } else {
                    return false;
                }
            }
        }
        return isanswer;
    }

    class ViewHolder extends MyViewHolder {
        @BindView(R.id.ctv_title)
        CheckedTextView ctvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    /**
     * 判断答案
     */
    private void setJudge(int pos) {
        if (type == 1 || type == 2) {
            if (anser_num == pos + 1) {
                selectList.get(pos).setStatus(1);
            } else {
                selectList.get(anser_num - 1).setStatus(1);
                selectList.get(pos).setStatus(-1);
            }
        } else {
            //selectpos +=getAnswer(pos);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取答案
     */
    private String getAnswer(int i) {
        switch (i) {
            case 1:
                answer = "A";
                break;
            case 2:
                answer = "B";
                break;
            case 3:
                answer = "C";
                break;
            case 4:
                answer = "D";
                break;
            case 7:
                answer = "AB";
                break;
            case 8:
                answer = "AC";
                break;
            case 9:
                answer = "AD";
                break;
            case 10:
                answer = "BC";
                break;
            case 11:
                answer = "BD";
            case 12:
                answer = "CD";
                break;
            case 13:
                answer = "ABC";
                break;
            case 14:
                answer = "ABD";
                break;
            case 15:
                answer = "ACD";
            case 16:
                answer = "BCD";
                break;
            case 17:
                answer = "ABCD";
                break;

        }
        return answer;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
