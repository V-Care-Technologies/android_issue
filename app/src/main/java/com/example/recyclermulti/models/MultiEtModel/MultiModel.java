package com.example.recyclermulti.models.MultiEtModel;

public class MultiModel {
    

    
    
    String social_gr;
    String no;

    String social_gr_value;
    String no_value;

    public String getSocial_gr_value() {
        return social_gr_value;
    }

    public void setSocial_gr_value(String social_gr_value) {
        this.social_gr_value = social_gr_value;
    }

    public String getNo_value() {
        return no_value;
    }

    public void setNo_value(String no_value) {
        this.no_value = no_value;
    }

    public MultiModel(String social_gr, String no) {
        this.social_gr = social_gr;
        this.no = no;
    }

    public String getSocial_gr() {
        return social_gr;
    }

    public void setSocial_gr(String social_gr) {
        this.social_gr = social_gr;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
