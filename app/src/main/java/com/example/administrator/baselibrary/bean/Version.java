package com.example.administrator.baselibrary.bean;

public class Version {

    private int Code;
    private Object Message;
    private Version Data;
    private int TotalCount;

    private int AppInfoListID;
    private int AppID;
    private String Version;
    private int VersionCode;
    private Object FileSize;
    private String DownLoadUrl;
    private Object CompatibilityNote;
    private Object Note;
    private String Image;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public Object getMessage() {
        return Message;
    }

    public void setMessage(Object message) {
        Message = message;
    }

    public com.example.administrator.baselibrary.bean.Version getData() {
        return Data;
    }

    public void setData(com.example.administrator.baselibrary.bean.Version data) {
        Data = data;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int totalCount) {
        TotalCount = totalCount;
    }

    public int getAppInfoListID() {
        return AppInfoListID;
    }

    public void setAppInfoListID(int appInfoListID) {
        AppInfoListID = appInfoListID;
    }

    public int getAppID() {
        return AppID;
    }

    public void setAppID(int appID) {
        AppID = appID;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public int getVersionCode() {
        return VersionCode;
    }

    public void setVersionCode(int versionCode) {
        VersionCode = versionCode;
    }

    public Object getFileSize() {
        return FileSize;
    }

    public void setFileSize(Object fileSize) {
        FileSize = fileSize;
    }

    public String getDownLoadUrl() {
        return DownLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        DownLoadUrl = downLoadUrl;
    }

    public Object getCompatibilityNote() {
        return CompatibilityNote;
    }

    public void setCompatibilityNote(Object compatibilityNote) {
        CompatibilityNote = compatibilityNote;
    }

    public Object getNote() {
        return Note;
    }

    public void setNote(Object note) {
        Note = note;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
