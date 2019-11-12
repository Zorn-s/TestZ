package ru.app.generatorp;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {User.class,Setting.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract SettingsDao settingsDao();
}
