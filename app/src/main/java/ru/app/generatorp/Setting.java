package ru.app.generatorp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Setting {

    public Setting(int id,String period,String age_min,String age_max) {
        this.id = id;
        this.period = period;
        this.age_min = age_min;
        this.age_max = age_max;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "period")
    private String period;

    @ColumnInfo(name = "age_min")
    private String age_min;

    @ColumnInfo(name = "age_max")
    private String age_max;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setPeriod(String period) {
        this.period = period;
    }

    public void setAge_min(String age_min) {
        this.age_min = age_min;
    }

    public void setAge_max(String age_max) {
        this.age_max = age_max;
    }


    public String getPeriod() {
        return period;
    }

    public String getAge_min() {
        return age_min;
    }

    public String getAge_max() {
        return age_max;
    }



}
