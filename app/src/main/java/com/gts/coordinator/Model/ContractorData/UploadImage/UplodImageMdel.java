package com.gts.coordinator.Model.ContractorData.UploadImage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UplodImageMdel {

    @SerializedName("FileNames")
    @Expose
    private String fileNames;
  /*  @SerializedName("phone")
    @Expose
    private Object phone;*/
    @SerializedName("CreatedTimestamp")
    @Expose
    private String createdTimestamp;
    @SerializedName("UpdatedTimestamp")
    @Expose
    private String updatedTimestamp;
    @SerializedName("DownloadLink")
    @Expose
    private String downloadLink;
    @SerializedName("ContentTypes")
    @Expose
    private String contentTypes;
    @SerializedName("Names")
    @Expose
    private String names;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Status")
    @Expose
    private String status;

    public String getFileNames() {
        return fileNames;
    }

    public void setFileNames(String fileNames) {
        this.fileNames = fileNames;
    }

   /* public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }*/

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(String updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getContentTypes() {
        return contentTypes;
    }

    public void setContentTypes(String contentTypes) {
        this.contentTypes = contentTypes;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}