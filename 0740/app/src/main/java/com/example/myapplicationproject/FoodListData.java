package com.example.myapplicationproject;

public class FoodListData {
    public int NO;
    public String f_once;
    public Long f_cabo;
    public Long f_fat;
    public Long f_kcal;
    public String f_name;
    public Long f_natrium;
    public Long f_protein;
    public Long f_sugar;

    public FoodListData() {
    }

    public FoodListData(int NO, String f_once, Long f_cabo, Long f_fat, Long f_kcal, String f_name, Long f_natrium, Long f_protein, Long f_sugar) {
        this.NO = NO;
        this.f_once = f_once;
        this.f_cabo = f_cabo;
        this.f_fat = f_fat;
        this.f_kcal = f_kcal;
        this.f_name = f_name;
        this.f_natrium = f_natrium;
        this.f_protein = f_protein;
        this.f_sugar = f_sugar;
    }

    public int  getf_no() {
        return NO;
    }

    public void setf_no(int  f_no) {
        this.NO = f_no;
    }

    public String getf_once() {
        return f_once;
    }

    public void setf_once(String f_once) {
        this.f_once = f_once;
    }

    public Long getf_cabo() {
        return f_cabo;
    }

    public void setf_cabo(Long f_cabo) {
        this.f_cabo = f_cabo;
    }

    public Long getf_fat() {
        return f_fat;
    }

    public void setf_fat(Long f_fat) {
        this.f_fat = f_fat;
    }

    public Long getf_kcal() {
        return f_kcal;
    }

    public void setf_kcal(Long f_kcal) {
        this.f_kcal = f_kcal;
    }

    public String getf_name() {
        return f_name;
    }

    public void setf_name(String f_name) {
        this.f_name = f_name;
    }

    public Long getf_natrium() {
        return f_natrium;
    }

    public void setf_natrium(Long f_natrium) {
        this.f_natrium = f_natrium;
    }

    public Long getf_protein() {
        return f_protein;
    }

    public void setf_protein(Long f_protein) {
        this.f_protein = f_protein;
    }

    public Long getf_sugar() {
        return f_sugar;
    }

    public void setf_sugar(Long f_sugar) {
        this.f_sugar = f_sugar;
    }

}
