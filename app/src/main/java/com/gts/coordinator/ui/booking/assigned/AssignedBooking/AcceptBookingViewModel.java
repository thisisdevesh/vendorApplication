package com.gts.coordinator.ui.booking.assigned.AssignedBooking;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gts.coordinator.Model.ContractorData.acceptBooking.AssignList;


import java.util.List;

public class AcceptBookingViewModel extends AndroidViewModel {
    private static final String TAG = "MyTag";
    private AcceptBookingRepository bookingRepository;
    LiveData<List<AssignList>> assignBookingList;


    public AcceptBookingViewModel(@NonNull Application application) {
        super(application);
        bookingRepository = new AcceptBookingRepository(application);

    }
    public LiveData<List<AssignList>> getAssignBooking(){
        assignBookingList = new MutableLiveData<List<AssignList>>();
        assignBookingList = bookingRepository.getBooking();
        return assignBookingList;
    }

}
