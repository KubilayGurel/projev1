package com.kubilaygurel.artbookfragment.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class ArtList(

    @ColumnInfo( "artistisim")
    var artistname : String,


    @ColumnInfo("artname")
    var artname: String,

    @ColumnInfo("year")
    var year : String

) {

   @PrimaryKey(autoGenerate = true)
    var id = 0

}