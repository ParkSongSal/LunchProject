package com.example.lunchproject.Retrofit2;

public class MenuRating {

    String b_id;
    String m_code;
    String grade;
    String content;
    String registDate;
    String registId;
    String update_date;
    String update_id;

    public MenuRating(){

    }

    public MenuRating(String b_id, String m_code, String grade, String content, String registDate, String registId, String update_date, String update_id) {
        this.b_id = b_id;
        this.m_code = m_code;
        this.grade = grade;
        this.content = content;
        this.registDate = registDate;
        this.registId = registId;
        this.update_date = update_date;
        this.update_id = update_id;
    }

    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public String getM_code() {
        return m_code;
    }

    public void setM_code(String m_code) {
        this.m_code = m_code;
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

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(String update_id) {
        this.update_id = update_id;
    }

    @Override
    public String toString() {
        return "MenuRating{" +
                "b_id='" + b_id + '\'' +
                ", m_code='" + m_code + '\'' +
                ", grade='" + grade + '\'' +
                ", content='" + content + '\'' +
                ", registDate='" + registDate + '\'' +
                ", registId='" + registId + '\'' +
                ", update_date='" + update_date + '\'' +
                ", update_id='" + update_id + '\'' +
                '}';
    }
}
