package com.example.recyclermulti.models.req.getHistory;

public class HistoryReq {
    String user_id;
    String categories_id="";

    public HistoryReq(String user_id, String categories_id) {
        this.user_id = user_id;
        this.categories_id = categories_id;
    }
}
