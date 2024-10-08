package com.kubilaygurel.artbookfragment.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kubilaygurel.artbookfragment.roomdb.artListDao

@Database(entities = [ArtList::class], version = 1)
abstract class ArtlistDataBase : RoomDatabase() {
    abstract fun ArtlistDao(): artListDao
}