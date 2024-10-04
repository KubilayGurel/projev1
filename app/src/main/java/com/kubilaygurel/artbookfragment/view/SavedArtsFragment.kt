package com.kubilaygurel.artbookfragment.view

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.kubilaygurel.artbookfragment.R
import com.kubilaygurel.artbookfragment.databinding.FragmentGaleryBinding
import com.kubilaygurel.artbookfragment.databinding.FragmentSavedArtsBinding
import com.kubilaygurel.artbookfragment.model.ArtlistDataBase
import com.kubilaygurel.artbookfragment.roomdb.artListDao


class SavedArtsFragment : Fragment() {

    private lateinit var artListDao: artListDao
    private lateinit var db: ArtlistDataBase
    private var artId: Int? = null
    private lateinit var _binding: FragmentSavedArtsBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {

        db = Room.databaseBuilder(requireContext(),ArtlistDataBase::class.java,"ArtList")
            .allowMainThreadQueries()
            .build()
        artListDao = db.ArtlistDao()
        arguments?.let {
            artId = it.getInt("artId")
        }
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedArtsBinding.inflate(inflater, container, false)
        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        artId?.let { id ->
            val art = artListDao.getArtById(id)
            binding.artNameTextView.text = art.artname
            binding.artistNameTextView.text = art.artistname
            binding.artYearTextView.text = art.year

            art.image?.let { byteArray ->
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                binding.savedMageView.setImageBitmap(bitmap)
    }

        super.onViewCreated(view, savedInstanceState)
    }

    }
    }

