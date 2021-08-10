package com.gts.coordinator.Model.ContractorData.UploadDocument;

public class DocumentDetail {
    private long vcabid;
    private String document_type;
    private String imageUrl;
    private boolean isDocSelected , underUploadProcess, isUploaded;

    public boolean isDocSelected() {
        return isDocSelected;
    }

    public void setDocSelected(boolean docSelected) {
        isDocSelected = docSelected;
    }

    public boolean isUnderUploadProcess() {
        return underUploadProcess;
    }

    public void setUnderUploadProcess(boolean underUploadProcess) {
        this.underUploadProcess = underUploadProcess;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public DocumentDetail(long vcabid, String document_type) {
        this.vcabid = vcabid;
        this.document_type = document_type;
    }

    public long getVcabid() {
        return vcabid;
    }

    public void setVcabid(long vcabid) {
        this.vcabid = vcabid;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
