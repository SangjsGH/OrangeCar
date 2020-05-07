package com.example.orangecar.mode;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Collect {
    @Id
    private long cid;
    private String id;
    private String question;
    private String answer;
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String explains;
    private String url;
    @Generated(hash = 806001373)
    public Collect(long cid, String id, String question, String answer,
            String item1, String item2, String item3, String item4, String explains,
            String url) {
        this.cid = cid;
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.explains = explains;
        this.url = url;
    }
    @Generated(hash = 1726975718)
    public Collect() {
    }
    public long getCid() {
        return this.cid;
    }
    public void setCid(long cid) {
        this.cid = cid;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getQuestion() {
        return this.question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() {
        return this.answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getItem1() {
        return this.item1;
    }
    public void setItem1(String item1) {
        this.item1 = item1;
    }
    public String getItem2() {
        return this.item2;
    }
    public void setItem2(String item2) {
        this.item2 = item2;
    }
    public String getItem3() {
        return this.item3;
    }
    public void setItem3(String item3) {
        this.item3 = item3;
    }
    public String getItem4() {
        return this.item4;
    }
    public void setItem4(String item4) {
        this.item4 = item4;
    }
    public String getExplains() {
        return this.explains;
    }
    public void setExplains(String explains) {
        this.explains = explains;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
