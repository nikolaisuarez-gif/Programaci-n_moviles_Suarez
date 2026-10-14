package com.tecsup.cafecampus.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tecsup.cafecampus.data.local.dao.ProductoDao
import com.tecsup.cafecampus.data.local.entity.Producto

@Database(
    entities = [Producto::class],
    version = 1,
    exportSchema = false
)
abstract class CafeDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao

    companion object {
        @Volatile
        private var INSTANCE: CafeDatabase? = null

        fun getDatabase(context: Context): CafeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CafeDatabase::class.java,
                    "cafe_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
