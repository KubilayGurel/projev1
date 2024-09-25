package com.kubilaygurel.artbookfragment.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.room.Room
import com.kubilaygurel.artbookfragment.R
import com.kubilaygurel.artbookfragment.model.ArtlistDataBase
import com.kubilaygurel.artbookfragment.roomdb.artListDao

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.addart_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        navController.navigate(R.id.action_homeFragment_to_galeryFragment)
        return super.onOptionsItemSelected(item)
    }


    }

