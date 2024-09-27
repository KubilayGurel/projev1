package com.kubilaygurel.artbookfragment.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.kubilaygurel.artbookfragment.R
import com.kubilaygurel.artbookfragment.adapter.ArtListAdapter
import com.kubilaygurel.artbookfragment.databinding.FragmentGaleryBinding
import com.kubilaygurel.artbookfragment.databinding.FragmentHomeBinding
import com.kubilaygurel.artbookfragment.databinding.RecyclerRowBinding
import com.kubilaygurel.artbookfragment.model.ArtList
import com.kubilaygurel.artbookfragment.model.ArtlistDataBase
import com.kubilaygurel.artbookfragment.roomdb.artListDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

private lateinit var binding: FragmentHomeBinding
private lateinit var artListAdapter: ArtListAdapter
private lateinit var artListDao: artListDao

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Room.databaseBuilder(
            requireContext(),
            ArtlistDataBase::class.java,
            "ArtList"
        ).build()
        lifecycleScope.launch {

        }

        artListAdapter = ArtListAdapter( artListDao.getAll().)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = artListAdapter


    }



}