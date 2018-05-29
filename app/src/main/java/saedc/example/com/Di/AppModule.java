package saedc.example.com.Di;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Stack;

import saedc.example.com.Model.Database.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {
    private Application application;


    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Application providesApplication() {
        return application;
    }

    @Singleton
    @Provides
    AppDatabase provideAppDatabase(Application application) {

        return Room.databaseBuilder(application, AppDatabase.class, "app_database")
//                .addMigrations(MIGRATION_1_2)


                .allowMainThreadQueries().addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Log.d("AppModule", "onCreate: ");
                        String query = "INSERT INTO SPENDING_GROUP VALUES ('1', 'Food'), ('2', 'Bills'), " +
                                "('3', 'Occasions'), ('4', 'Premium'),('5', 'kids'), ('6', 'travel'), " +
                                "('7', 'Transportation'), ('8', 'Other'),('9', 'Shopping'), ('10', 'debt'), " +
                                "('11', 'healthcare'), ('12', 'Fixes')";
                        db.execSQL(query);
                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                        Log.d("AppModule", "onOpen: ");


                    }
                })
                .build();


    }

//        static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//// Since we didn't alter the table, there's nothing else to do here.
//        }
//    };


}
