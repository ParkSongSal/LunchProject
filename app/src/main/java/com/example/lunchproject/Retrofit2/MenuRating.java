package com.example.lunchproject.Retrofit2;

public class MenuRating {

    String seq;
    String mCode;
    String grade;
    String content;
    String registDate;
    String registId;
    String updateDate;
    String updateId;

    public MenuRating(){

    }

    public MenuRating(String seq, String mCode, String grade, String content, String registDate, String registId, String updateDate, String updateId) {
        this.seq = seq;
        this.mCode = mCode;
        this.grade = grade;
        this.content = content;
        this.registDate = registDate;
        this.registId = registId;
        this.updateDate = updateDate;
        this.updateId = updateId;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegistDate() {
        return registDate;
    }

    public void setRegistDate(String registDate) {
        this.registDate = registDate;
    }

    public String getRegistId() {
        return registId;
    }

    public void setRegistId(String registId) {
        this.registId = registId;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    @Override
    public String toString() {
        return "MenuRating{" +
                "seq='" + seq + '\'' +
                ", mCode='" + mCode + '\'' +
                ", grade='" + grade + '\'' +
                ", content='" + content + '\'' +
                ", registDate='" + registDate + '\'' +
                ", registId='" + registId + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", updateId='" + updateId + '\'' +
                '}';
    }
}
