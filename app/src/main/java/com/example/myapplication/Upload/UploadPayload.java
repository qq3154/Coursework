package com.example.myapplication.Upload;

import java.util.List;

public class UploadPayload {

    private String userId;
    private List<Detail> detailList;

    public UploadPayload(String userId, List<Detail> detailList) {
        this.userId = userId;
        this.detailList = detailList;
    }
}
