package com.gts.coordinator.ui.booking.assigned.AssignedBooking;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.gts.coordinator.Model.ContractorData.acceptBooking.AssignList;
import com.gts.coordinator.Model.ContractorData.assignBooking.BookingList;
import com.gts.coordinator.roomDB.MyDao;
import com.gts.coordinator.roomDB.MyDatabase;
import com.gts.coordinator.service.MyBookingService;


import java.util.List;

public class AcceptBookingRepository {
   private MyDao myDao;
   private LiveData<List<AssignList>> mBookingList;

   AcceptBookingRepository(Application application){
      /* MyDatabase db = MyDatabase.getInstance(application);
       myDao = db.myDao();
       mBookingList = myDao.getAcceptBooking();*/
      mBookingList = MyBookingService.assignedBookingList;

   }

   LiveData<List<AssignList>> getBooking(){return mBookingList;}



}