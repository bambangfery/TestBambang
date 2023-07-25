package com.dans.bambang.model;

import com.google.gson.annotations.SerializedName;


public class JobListResp {
    @SerializedName("id")
    String id;
    @SerializedName("type")
    String type;
    @SerializedName("url")
    String url;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("company")
    String company;
    @SerializedName("company_url")
    String companyUrl;
    @SerializedName("location")
    String location;
    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;
    @SerializedName("how_to_apply")
    String gowToApply;
    @SerializedName("company_logo")
    String companyLogo;

    public JobListResp(String id, String type, String url, String createdAt, String company, String companyUrl, String location, String title, String description, String gowToApply, String companyLogo) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.createdAt = createdAt;
        this.company = company;
        this.companyUrl = companyUrl;
        this.location = location;
        this.title = title;
        this.description = description;
        this.gowToApply = gowToApply;
        this.companyLogo = companyLogo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGowToApply() {
        return gowToApply;
    }

    public void setGowToApply(String gowToApply) {
        this.gowToApply = gowToApply;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }
}
