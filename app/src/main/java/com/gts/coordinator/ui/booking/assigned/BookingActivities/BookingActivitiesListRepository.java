package com.gts.coordinator.ui.booking.assigned.BookingActivities;



import android.app.Application;

import androidx.lifecycle.LiveData;


import com.gts.coordinator.Model.ContractorData.BookingActivities.BookingActivitiesList;
import com.gts.coordinator.roomDB.MyDao;
import com.gts.coordinator.roomDB.MyDatabase;
import com.gts.coordinator.service.MyBookingService;

import java.util.List;


public class BookingActivitiesListRepository {

    private LiveData<List<BookingActivitiesList>> mBookingList;


    public BookingActivitiesListRepository(Application application) {

      mBookingList = MyBookingService.bookingActivitiesList;

    }


    LiveData<List<BookingActivitiesList>> getBookingActivitiesList()
    {
        return mBookingList;
    }




}
