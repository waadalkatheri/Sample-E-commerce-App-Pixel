package com.example.pixelshop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pixelshop.data.local.models.FavoriteProduct
import com.example.pixelshop.data.local.models.UserCart

@Database(entities = [UserCart::class, FavoriteProduct::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao
}