package com.gts.coordinator.ui.booking.assigned.BookingActivities;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gts.coordinator.Model.ContractorData.BookingActivities.BookingActivitiesList;

import java.util.List;

public class BookingActivitiesListViewModel extends AndroidViewModel {


    private BookingActivitiesListRepository bookingActivitiesListRepository;
    private LiveData<List<BookingActivitiesList>> mLiveData;


    public BookingActivitiesListViewModel(@NonNull Application application) {
        super(application);
        bookingActivitiesListRepository = new BookingActivitiesListRepository(application);
        mLiveData = bookingActivitiesListRepository.getBookingActivitiesList();

    }

    public LiveData<List<BookingActivitiesList>> getBookingActivitiesList()
    {
        return mLiveData;
    }

}
