package com.gts.coordinator.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.gts.coordinator.ui.booking.assigned.AssignedBooking.AssignedBookingFragment;
import com.gts.coordinator.ui.booking.assigned.BookingActivities.BookingActivitiesFragment;
import com.gts.coordinator.ui.booking.assigned.UnassignedBooking.BookingAssignFragment;

public class PageAdepter extends FragmentStateAdapter {
    public PageAdepter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new BookingAssignFragment();
            case 1:
                return new AssignedBookingFragment();
            case 2:
                return new BookingActivitiesFragment();

        }
        return new BookingAssignFragment();
    }
    @Override
    public int getItemCount() {
        return 3;
    }
}
