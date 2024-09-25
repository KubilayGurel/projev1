package com.kubilaygurel.artbookfragment.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kubilaygurel.artbookfragment.model.ArtList


@Dao
interface artListDao {

    @Query("SELECT * FROM ArtList")
    fun getAll() : List<ArtList>

    @Insert
    fun insert(artList: ArtList)

    @Delete
    fun delete(artList: ArtList)
}