package com.gts.coordinator.ui.booking.assigned.UnassignedBooking;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gts.coordinator.Model.ContractorData.assignBooking.BookingList;


import java.util.List;

public class BookingViewModel extends AndroidViewModel {
   private BookingRepository bookingRepository;
   private LiveData<List<BookingList>> mBookingList;


    public BookingViewModel(@NonNull Application application) {
        super(application);
        bookingRepository = new BookingRepository(application);
        mBookingList = bookingRepository.getBooking();
    }
    public LiveData<List<BookingList>> getBooking(){
        return mBookingList;
    }


}
