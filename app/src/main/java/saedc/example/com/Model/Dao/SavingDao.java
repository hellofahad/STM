package saedc.example.com.Model.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import saedc.example.com.Model.Entity.Saving;
import saedc.example.com.Model.Pojo.MaxSaving;


@Dao
public interface SavingDao {

    @Query("SELECT * FROM saving")
    LiveData<List<Saving>> getsaving();

    @Query("SELECT item_price, name, round(((item_saveing * 100.0) / item_price),1) as max  FROM saving WHERE max < 100.0  ORDER BY max DESC  LIMIT 1")
    LiveData<MaxSaving> maxsaving();

    @Query("SELECT SUM(item_saveing) FROM saving LIMIT 1")
    Double getTotalSavingprice();

    @Query("DELETE FROM saving WHERE id = :id")
    void deleteSaving(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSaving(Saving s);
}
