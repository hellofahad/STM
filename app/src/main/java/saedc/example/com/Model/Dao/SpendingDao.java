package saedc.example.com.Model.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

import saedc.example.com.Model.Entity.RawSpending;
import saedc.example.com.Model.Pojo.LinechartPojo;
import saedc.example.com.Model.Pojo.PiechartPojo;
import saedc.example.com.Model.Pojo.Spending;


@Dao
public interface SpendingDao {

    @Query("SELECT date,quantity FROM SPENDING  ORDER BY SPENDING.DATE DESC")
    List<LinechartPojo> Linechartdata();

    @Query("SELECT date,quantity FROM SPENDING  ORDER BY SPENDING.DATE DESC ")
    LiveData<List<LinechartPojo>> changecolorpercent();

    @Query("SELECT * FROM SPENDING,SPENDING_GROUP WHERE SPENDING.GROUP_ID = SPENDING_GROUP.GROUP_ID AND date BETWEEN :dayst AND :dayet  ORDER BY SPENDING.DATE DESC")
    LiveData<List<Spending>> getSpendingsWithGroups(Date dayst,Date dayet);

    @Query("SELECT SUM(QUANTITY)as maxcount,group_name FROM SPENDING,SPENDING_GROUP WHERE SPENDING.GROUP_ID = SPENDING_GROUP.GROUP_ID GROUP BY SPENDING_GROUP.GROUP_ID ORDER BY maxcount DESC LIMIT 1")
    LiveData<List<Spending>> getMaxGroub();

    @Query("SELECT SUM(QUANTITY)as maxcount,group_name FROM SPENDING,SPENDING_GROUP WHERE SPENDING.GROUP_ID = SPENDING_GROUP.GROUP_ID AND date BETWEEN :dayst AND :dayet  GROUP BY SPENDING_GROUP.GROUP_ID ORDER BY maxcount DESC ")
    List<PiechartPojo> pichartcategory(Date dayst,Date dayet);

    @Query("SELECT SUM(QUANTITY)as maxcount,group_name FROM SPENDING INNER JOIN SPENDING_GROUP ON SPENDING.GROUP_ID = SPENDING_GROUP.GROUP_ID  GROUP BY SPENDING_GROUP.GROUP_ID  ")
    List<PiechartPojo> pichartcategoryByMonth();
//
    @Query("SELECT SUM(QUANTITY)as maxcount,group_name FROM SPENDING,SPENDING_GROUP WHERE SPENDING.GROUP_ID = SPENDING_GROUP.GROUP_ID GROUP BY SPENDING_GROUP.GROUP_ID ORDER BY maxcount DESC LIMIT 4")
    LiveData<List<PiechartPojo>> CstegoryNameWithTotalList();

    @Query("SELECT * FROM SPENDING INNER JOIN SPENDING_GROUP ON SPENDING.GROUP_ID = SPENDING_GROUP.GROUP_ID ORDER BY SPENDING.DATE DESC")
    List<Spending> getSpendingsdate();

    @Query("SELECT * FROM SPENDING,SPENDING_GROUP WHERE SPENDING.GROUP_ID = SPENDING_GROUP.GROUP_ID and SPENDING_GROUP.group_name=:CategoryName ORDER BY SPENDING.DATE DESC")
    List<Spending> chartdetail(String CategoryName);

    @Query("SELECT strftime('%Y-%m', SPENDING.DATE),* FROM SPENDING ORDER BY SPENDING.DATE ")
    List<Spending> getSpendingsdatejustmonths();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSpending(RawSpending s);



    @Query("SELECT SUM(QUANTITY) FROM SPENDING")
    LiveData<Double> getTotalSpendingQuantity();

    @Query("SELECT SUM(QUANTITY) FROM SPENDING WHERE  date BETWEEN :dayst AND :dayet")
    LiveData<Double> getTotalSpendingPriceForPieChart(Date dayst,Date dayet);

    @Query("SELECT SUM(QUANTITY) FROM SPENDING")
    Double getTotalSpendingQuantit();

    @Query("SELECT SUM(QUANTITY) FROM SPENDING where SPENDING.group_id=:groubId")
    LiveData<Double> getTotalSpendingQuantity1(int groubId);

    @Query("SELECT SUM(QUANTITY) FROM SPENDING,SPENDING_GROUP WHERE SPENDING.GROUP_ID = SPENDING_GROUP.GROUP_ID GROUP BY SPENDING_GROUP.GROUP_ID ")
    List<Double> getTotalByGroub();

    @Query("DELETE FROM SPENDING WHERE ID = :id")
    void deleteSpending(int id);


}
