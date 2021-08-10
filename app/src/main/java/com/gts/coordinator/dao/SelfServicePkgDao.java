package com.gts.coordinator.dao;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by GTS Developer on 22-May-2017 @ 11:45
 */

public class SelfServicePkgDao {
  //  Calendar datetime;
//  private Date date;
  private Calendar date ;
  //  String planType,time;
//  int attempt;
  private ArrayList<PkgAttemptDetails> pkgAttempts;
//  private PkgAttemptDetails attemptDetails;

  public SelfServicePkgDao(Calendar date)
  {
    this.date = date;
    pkgAttempts = new ArrayList<>();
  }

  public void addAttempt(PkgAttemptDetails pkgAttemptDetails)
  {
    pkgAttempts.add(pkgAttemptDetails);
  }

  public Calendar getDate() {
    return date;
  }

  public ArrayList<PkgAttemptDetails> getPkgAttempts()
  {
    return pkgAttempts;
  }

  public static class PkgAttemptDetails
  {
    private int attempt;
    private Calendar time;
    private ArrayList<String> packages;

    public PkgAttemptDetails(int attempt, Calendar time) {
      this.attempt = attempt;
      this.time = time;
//      this.packages = packages;
      packages = new ArrayList<>();
    }

    public void addPackage(String pkgName) {
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
