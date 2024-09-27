package com.kubilaygurel.artbookfragment.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kubilaygurel.artbookfragment.model.ArtList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable


@Dao
interface ArtListDao {

    @Query("SELECT * FROM ArtList")
    fun getAll() : Flowable<List<ArtList>>

    @Insert
    fun insert(artList: ArtList) : Completable

    @Delete
    fun delete(artList: ArtList) : Completable
}