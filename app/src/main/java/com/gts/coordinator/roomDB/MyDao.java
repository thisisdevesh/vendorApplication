package com.gts.coordinator.roomDB;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.gts.coordinator.Model.ContractorData.BookingActivities.BookingActivitiesList;
import com.gts.coordinator.Model.ContractorData.ContractorDetail.DriverList;
import com.gts.coordinator.Model.ContractorData.ContractorDetail.VendorList;
import com.gts.coordinator.Model.ContractorData.acceptBooking.AssignList;
import com.gts.coordinator.Model.ContractorData.assignBooking.BookingList;
import com.gts.coordinator.Model.getAll.Drivervendorlist;
import com.gts.coordinator.dao.Driver;

import java.util.List;

@Dao
public interface MyDao {
    //insert data
    //(onConflict = OnConflictStrategy.REPLACE) replace primary key
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBookingActivities(List<BookingActivitiesList> list);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBooking(List<BookingList> list);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAcceptBooking(List<AssignList> assignLists);
    // get data
    @Query("SELECT * FROM booking_activities_list")
    LiveData<List<BookingActivitiesList>> getBookingActivitiesList();
    @Query("SELECT * FROM assign_booking_list")
    LiveData<List<BookingList>> getBookingList();
    @Query("SELECT * FROM assign_booking")
    LiveData<List<AssignList>> getAcceptBooking();
   // delete particular cab number
   /* @Query("DELETE FROM assign_booking WHERE cabNumber")
    void deleteBooingId(String booking_id);*/
    //delete data
    @Query("DELETE FROM booking_activities_list")
    void deleteBookingActivitiesList();
    @Query("DELETE FROM assign_booking_list")
    void deleteBookingList();
    @Query("DELETE FROM assign_booking")
    void deleteAcceptBookingList();
    @Query("SELECT * FROM assign_booking_list")
    DataSource.Factory<Integer, BookingList> concertsByDate();
    //Driver List
    @Insert
    void insertDriver(List<DriverList> driverList);

    @Insert
    void insertDriverVen(List<Drivervendorlist> driverList);
    @Query("SELECT * FROM driver_vendor_list WHERE driverNo = :driverPhones")
    List<Drivervendorlist> getDriverDetailByPhoneNo(String driverPhones);
    @Query("SELECT * FROM driver_vendor_list WHERE driverName = :nameDriver")
    List<Drivervendorlist> getDriverDetailByName(String nameDriver);
    @Query("DELETE FROM driver_vendor_list")
    void deleteDriverList2();
    @Query("SELECT * FROM driver_vendor_list WHERE cabNo = :cabNumbers")
    List<Drivervendorlist> getDriverDetailByCabNo(String cabNumbers);
    //Vendor List
  //  List<VendorList> getVendorDetailByPhoneNo(String driverPhones);
    @Query("SELECT * FROM driver_vendor_list WHERE vendorNo = :vendor")
    List<Drivervendorlist> getVendorDetailByPhoneNo(String vendor);
    @Query("SELECT * FROM driver_vendor_list WHERE vendorName = :nameVendor")
    List<Drivervendorlist> getVendorDetailByName(String nameVendor);
    @Query("SELECT * FROM driver_vendor_list WHERE cabStatus = :statusCab")
    List<Drivervendorlist> getCabStatus(String statusCab);
    @Query("SELECT * FROM driver_vendor_list WHERE maintenance = :ms")
    List<Drivervendorlist> getMaintenaceStatus(Integer ms);
    @Query("SELECT * FROM driver_vendor_list WHERE deactivated = :da")
    List<Drivervendorlist> getDeactivateStatus(String da);
    @Query("SELECT * FROM driver_vendor_list")
    List<Drivervendorlist> getAllCab();

}
