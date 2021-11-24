package com.example.lunchproject.Retrofit2;

public class MenuRating {

    String seq;

    String writer;
    String grade;
    String date;
    String reply;

    public MenuRating(){

    }
    public MenuRating(String seq, String writer, String grade, String date, String reply) {
        this.seq = seq;
        this.writer = writer;
        this.grade = grade;
        this.date = date;
        this.reply = reply;
    }


    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "MenuRating{" +
                "seq='" + seq + '\'' +
                ", writer='" + writer + '\'' +
                ", grade='" + grade + '\'' +
                ", date='" + date + '\'' +
                ", reply='" + reply + '\'' +
                '}';
    }
}
