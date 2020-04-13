package com.example.orangecar.mode;

public class Selection {
    private String selection;//每个选项的text
    private int status;      //选择状态码
    private String selectPos;//选择的答案

    public Selection(String selection, int status, String selectPos) {
        this.selection = selection;
        this.status = status;
        this.selectPos = selectPos;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(String selectPos) {
        this.selectPos = selectPos;
    }
}
