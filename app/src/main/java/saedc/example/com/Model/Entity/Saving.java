package saedc.example.com.Model.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import saedc.example.com.Model.Database.Converters;

/**
 * Created by saedc on 08/02/18.
 */
@Entity(tableName = "saving")
public class Saving implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "name")
    private String savingname;


    @ColumnInfo(name = "item_price")
    private Double item_price;

    @ColumnInfo(name = "item_saveing")
    private Double item_saveing;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "enddate")
    private Date end_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSavingname() {
        return savingname;
    }

    public void setSavingname(String savingname) {
        this.savingname = savingname;
    }

    public Double getItem_price() {
        return item_price;
    }

    public void setItem_price(Double item_price) {
        this.item_price = item_price;
    }

    public Double getItem_saveing() {
        return item_saveing;
    }

    public void setItem_saveing(Double item_saveing) {
        this.item_saveing = item_saveing;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
