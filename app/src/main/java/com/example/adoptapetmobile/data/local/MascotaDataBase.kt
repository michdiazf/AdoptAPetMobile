package com.example.adoptapetmobile.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.adoptapetmobile.data.model.Mascota

@Database(
    entities = [Mascota::class],
    version = 1,
    exportSchema = false
)
abstract class MascotaDataBase : RoomDatabase() {

    abstract fun mascotaDao(): MascotaDao

    companion object {
        @Volatile
        private var INSTANCE: MascotaDataBase? = null

        fun getDatabase(context: Context): MascotaDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MascotaDataBase::class.java,
                    "mascota_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}