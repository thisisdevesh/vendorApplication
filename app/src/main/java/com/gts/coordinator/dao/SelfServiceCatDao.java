package com.gts.coordinator.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by GTS Developer on 27-May-2017 @ 15:29
 */

public class SelfServiceCatDao
{
  private Calendar date ;
  String  actualCategory;
  String time;

  private ArrayList<CatAttemptDetails> pkgAttempts;
//  private PkgAttemptDetails attemptDetails;

  public SelfServiceCatDao(Calendar date ,String actualCategory)
  {
    this.date = date;
    this.actualCategory = actualCategory;
    this.time =time;
    pkgAttempts = new ArrayList<>();
  }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setActualCategory(String actualCategory) {
    this.actualCategory = actualCategory;
  }

  public String getActualCategory() {
    return actualCategory;
  }

  public void addAttempt(CatAttemptDetails pkgAttemptDetails)
  {
    pkgAttempts.add(pkgAttemptDetails);
  }

  public Calendar getDate() {
    return date;
  }

  public ArrayList<CatAttemptDetails> getCatAttempts()
  {
    return pkgAttempts;
  }

  public static class CatAttemptDetails
  {
    private int attempt;
    private Calendar time;
    private ArrayList<String> packages;

    public CatAttemptDetails(int attempt, Calendar time) {
      this.attempt = attempt;
      this.time = time;
//      this.packages = packages;
      packages = new ArrayList<>();
    }

    public void addPackage(String pkgName)
    {
      packages.add(pkgName);
    }

    public int getAttempt() {
      return attempt;
    }

    public Calendar getTime() {
      return time;
    }

    public ArrayList<String> getPackages() {
      return packages;
    }
  }

}
