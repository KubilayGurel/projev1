package com.kubilaygurel.artbookfragment.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kubilaygurel.artbookfragment.model.ArtList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single


@Dao
interface artListDao {

    @Query("SELECT * FROM ArtList")
    fun getAll() : Flowable<List<ArtList>>

    @Query("SELECT * FROM ArtList WHERE id = :artId")
    fun getArtById(artId: Int): ArtList



    @Insert
    fun insert(artList: ArtList) : Completable

    @Update
    fun update(artList: ArtList)

    @Delete
    fun delete(artList: ArtList) : Completable
}