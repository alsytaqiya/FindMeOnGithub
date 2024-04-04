package com.dicoding.picodiploma.findmeongithub.dataApp.localData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [YayUser::class],
    version = 1
)
abstract class DatabaseOfUser : RoomDatabase() {
    companion object {
        var instanceAccess: DatabaseOfUser? = null

        fun getDatabase(context: Context): DatabaseOfUser? {
            if (instanceAccess == null) {
                synchronized(DatabaseOfUser::class) {
                    instanceAccess = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseOfUser::class.java, "database_of_user"
                    ).build()
                }
            }
            return instanceAccess
        }
    }

    abstract fun daoOfYayUser(): DaoOfYayUser
}