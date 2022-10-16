package com.example.myapplication.Upload;

public class UploadResponse {

    private String uploadResponseCode;
    private String userId;
    private int number;
    private String names;
    private String message;

    //For testing
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("uploadResponseCode='" + uploadResponseCode + '\'');
        sb.append("\n");
        sb.append("     userId='" + userId + '\'' );
        sb.append("\n");
        sb.append("     number=" + number);
        sb.append("\n");
        sb.append("     names='" + names + '\'');
        sb.append("\n");
        sb.append("     message='" + message + '\'');

        return sb.toString();
//        return "uploadResponseCode='" + uploadResponseCode + '\'' +
//                ", userId='" + userId + '\'' +
//                ", number=" + number +
//                ", names='" + names + '\'' +
//                ", message='" + message + '\'' +
//                '}';
    }

    public UploadResponse(String uploadResponseCode, String userId, int number, String names, String message) {
        this.uploadResponseCode = uploadResponseCode;
        this.userId = userId;
        this.number = number;
        this.names = names;
        this.message = message;
    }

    public String getUploadResponseCode() {
        return uploadResponseCode;
    }

    public void setUploadResponseCode(String uploadResponseCode) {
        this.uploadResponseCode = uploadResponseCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
