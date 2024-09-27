package com.kubilaygurel.artbookfragment.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.kubilaygurel.artbookfragment.R
import com.kubilaygurel.artbookfragment.databinding.FragmentGaleryBinding
import com.kubilaygurel.artbookfragment.model.ArtList
import com.kubilaygurel.artbookfragment.model.ArtlistDataBase
import com.kubilaygurel.artbookfragment.roomdb.artListDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers



class GaleryFragment : Fragment() {

    private lateinit var db : ArtlistDataBase
    private lateinit var artlistDao : artListDao

    private var _binding: FragmentGaleryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(requireContext(), ArtlistDataBase::class.java, "Artlist")
            .allowMainThreadQueries()
            .build()
        artlistDao = db.ArtlistDao()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener{
            save()
        }
    }

    fun save (){
        val artList = ArtList(
            binding.artistNameText.text.toString(),
            binding.artNameText.text.toString(),
            binding.artYearText.text.toString()
        )
        artlistDao.insert(artList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        findNavController().navigate(R.id.action_galeryFragment_to_homeFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGaleryBinding.inflate(inflater, container, false)
        return binding.root

    }

    }