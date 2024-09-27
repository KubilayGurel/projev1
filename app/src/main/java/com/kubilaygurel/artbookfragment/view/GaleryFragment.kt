package com.kubilaygurel.artbookfragment.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.kubilaygurel.artbookfragment.databinding.FragmentGaleryBinding
import com.kubilaygurel.artbookfragment.model.ArtList
import com.kubilaygurel.artbookfragment.model.ArtlistDataBase
import com.kubilaygurel.artbookfragment.roomdb.ArtListDao


class GaleryFragment : Fragment() {

    private lateinit var db: ArtlistDataBase
    private lateinit var artlistDao: ArtListDao

    private var _binding: FragmentGaleryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(requireContext(), ArtlistDataBase::class.java, "ArtList")
            .allowMainThreadQueries()
            .build()
        artlistDao = db.ArtlistDao()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener {
            save()
        }
        binding.imageView.setOnClickListener {
            artlistDao.getAll()
            Log.d("test1", artlistDao.getAll().toString())
        }
    }

    fun save() {
        val artlist = ArtList(
            binding.artistNameText.text.toString(),
            binding.artNameText.text.toString(),
            binding.artYearText.text.toString()
        )
        artlistDao.insert(artlist).subscribe()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGaleryBinding.inflate(inflater, container, false)
        return binding.root

    }

}