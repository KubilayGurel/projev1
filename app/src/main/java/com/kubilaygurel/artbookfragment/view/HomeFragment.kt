package com.kubilaygurel.artbookfragment.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.kubilaygurel.artbookfragment.adapter.ArtListAdapter
import com.kubilaygurel.artbookfragment.databinding.FragmentHomeBinding
import com.kubilaygurel.artbookfragment.model.ArtlistDataBase
import com.kubilaygurel.artbookfragment.roomdb.ArtListDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var artListAdapter: ArtListAdapter
    private lateinit var artListDao: ArtListDao
    private var disposable: Disposable? = null
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
        artListDao = db.ArtlistDao()

        disposable = artListDao.getAll()
            .subscribeOn(Schedulers.io()) // Veriyi IO thread'de çekiyoruz
            .observeOn(AndroidSchedulers.mainThread()) // Sonucu UI thread'de işliyoruz
            .subscribe(
                { artList ->
                    // Adapter'ı güncelle
                    artListAdapter = ArtListAdapter(artList)
                    Log.d("YUSUFAYDIN",artList[0].artname.toString())
                    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.recyclerView.adapter = artListAdapter
                },
                { error ->
                    error.printStackTrace()
                }
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Disposable temizleniyor
        disposable?.dispose()
    }



}