package com.gts.coordinator.ui.booking.assigned.UnassignedBooking;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.gts.coordinator.Model.ContractorData.assignBooking.BookingList;
import com.gts.coordinator.roomDB.MyDao;
import com.gts.coordinator.roomDB.MyDatabase;
import com.gts.coordinator.service.MyBookingService;

import java.util.List;

public class BookingRepository {
   private MyDao myDao;
   private LiveData<List<BookingList>> mBookingList;


   BookingRepository(Application application){
    /*   MyDatabase db = MyDatabase.getInstance(application);
       myDao = db.myDao();
       mBookingList = myDao.getBookingList();*/
       mBookingList = MyBookingService.unassignedBookingList;
   }

   LiveData<List<BookingList>> getBooking(){return mBookingList;}




}