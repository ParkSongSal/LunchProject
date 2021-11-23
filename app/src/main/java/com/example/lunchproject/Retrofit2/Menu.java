
package com.example.lunchproject.Retrofit2;

import com.google.gson.annotations.SerializedName;

public class Menu {

    @SerializedName("result")
    String result;

    @SerializedName("seq")
    String seq;
    @SerializedName("menu_kind")
    String menu_kind;
    @SerializedName("menu_name")
    String menu_name;
    @SerializedName("menu_code")
    String menu_code;

    public Menu(String seq, String menu_kind, String menu_name, String menu_code) {
        this.seq = seq;
        this.menu_kind = menu_kind;
        this.menu_name = menu_name;
        this.menu_code = menu_code;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMenu_kind() {
        return menu_kind;
    }

    public void setMenu_kind(String menu_kind) {
        this.menu_kind = menu_kind;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_code() {
        return menu_code;
    }

    public void setMenu_code(String menu_code) {
        this.menu_code = menu_code;
    }
}
