
package com.gts.coordinator.Model.ContractorData.Bonus;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferProgressModel {

    @SerializedName("getresponse")
    @Expose
    private Getresponse getresponse;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("totalSedan")
    @Expose
    private String totalSedan;
    @SerializedName("totalHatchback")
    @Expose
    private String totalHatchback;
    @SerializedName("totalMini")
    @Expose
    private String totalMini;
    @SerializedName("totalSuv")
    @Expose
    private String totalSuv;
    @SerializedName("totalMiniSuv")
    @Expose
    private String totalMiniSuv;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("totalcab")
    @Expose
    private String totalcab;
    @SerializedName("instructionlist")
    @Expose
    private List<Instructionlist> instructionlist = null;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    public Getresponse getGetresponse() {
        return getresponse;
    }

    public void setGetresponse(Getresponse getresponse) {
        this.getresponse = getresponse;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTotalSedan() {
        return totalSedan;
    }

    public void setTotalSedan(String totalSedan) {
        this.totalSedan = totalSedan;
    }

    public String getTotalHatchback() {
        return totalHatchback;
    }

    public void setTotalHatchback(String totalHatchback) {
        this.totalHatchback = totalHatchback;
    }

    public String getTotalMini() {
        return totalMini;
    }

    public void setTotalMini(String totalMini) {
        this.totalMini = totalMini;
    }

    public String getTotalSuv() {
        return totalSuv;
    }

    public void setTotalSuv(String totalSuv) {
        this.totalSuv = totalSuv;
    }

    public String getTotalMiniSuv() {
        return totalMiniSuv;
    }

    public void setTotalMiniSuv(String totalMiniSuv) {
        this.totalMiniSuv = totalMiniSuv;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotalcab() {
        return totalcab;
    }

    public void setTotalcab(String totalcab) {
        this.totalcab = totalcab;
    }

    public List<Instructionlist> getInstructionlist() {
        return instructionlist;
    }

    public void setInstructionlist(List<Instructionlist> instructionlist) {
        this.instructionlist = instructionlist;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}