package com.kubilaygurel.artbookfragment.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.kubilaygurel.artbookfragment.databinding.FragmentGaleryBinding
import com.kubilaygurel.artbookfragment.model.ArtList
import com.kubilaygurel.artbookfragment.model.ArtlistDataBase
import com.kubilaygurel.artbookfragment.roomdb.artListDao


class GaleryFragment : Fragment() {

    private lateinit var db : ArtlistDataBase
    private  var artlistDao : artListDao()

    private var _binding: FragmentGaleryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(requireContext(), ArtlistDataBase::class.java, "Artlist")
            .allowMainThreadQueries()
            .build()
        artlistDao = db.ArtlistDao()


    }


    fun save (view: View){
        val artlist = ArtList(binding.artistNameText.text.toString(),binding.artNameText.text.toString(),binding.artYearText.text.toString())
        artlistDao.insert(artlist)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGaleryBinding.inflate(inflater, container, false)
        return binding.root

    }

    }