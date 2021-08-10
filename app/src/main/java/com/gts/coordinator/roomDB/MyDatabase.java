package com.gts.coordinator.roomDB;

        import android.content.Context;

        import androidx.room.Database;
        import androidx.room.Room;
        import androidx.room.RoomDatabase;

        import com.gts.coordinator.Model.ContractorData.BookingActivities.BookingActivitiesList;
        import com.gts.coordinator.Model.ContractorData.ContractorDetail.DriverList;
        import com.gts.coordinator.Model.ContractorData.acceptBooking.AssignList;
        import com.gts.coordinator.Model.ContractorData.assignBooking.BookingList;
        import com.gts.coordinator.Model.getAll.Drivervendorlist;

@Database(entities = {BookingList.class, AssignList.class, DriverList.class, Drivervendorlist.class, BookingActivitiesList.class},version = 20,exportSchema = false)
public abstract class MyDatabase  extends RoomDatabase {
    private static MyDatabase myDatabase;
    public abstract MyDao myDao();
    public static MyDatabase getInstance(Context context){
        if (myDatabase==null){
            myDatabase = Room.databaseBuilder(context,MyDatabase.class,"partnerapp.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return myDatabase;
    }

}//9634458092
