package com.kubilaygurel.artbookfragment.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.collection.emptyLongSet
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.kubilaygurel.artbookfragment.R
import com.kubilaygurel.artbookfragment.databinding.FragmentGaleryBinding
import com.kubilaygurel.artbookfragment.model.ArtList
import com.kubilaygurel.artbookfragment.model.ArtlistDataBase
import com.kubilaygurel.artbookfragment.roomdb.artListDao


class GaleryFragment : Fragment() {

    private lateinit var db: ArtlistDataBase
    private lateinit var artlistDao: artListDao
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    private lateinit var permissionLaunncher :  ActivityResultLauncher<String>
    var selectedBitmap = null

    private var _binding: FragmentGaleryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(requireContext(), ArtlistDataBase::class.java, "ArtList")
            .allowMainThreadQueries()
            .build()
        artlistDao = db.ArtlistDao()
        registerLauncher()
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
        binding.imageView.setOnClickListener{
            selectImage(view)
        }
    }

    private fun save() {
        val artlist = ArtList(
            binding.artistNameText.text.toString(),
            binding.artNameText.text.toString(),
            binding.artYearText.text.toString()
        )
        artlistDao.insert(artlist).subscribe()
        findNavController().navigate(R.id.action_galeryFragment_to_homeFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGaleryBinding.inflate(inflater, container, false)
        return binding.root

    }



    fun selectImage(view: View){

        if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view,"Galeri için izin ver!",Snackbar.LENGTH_INDEFINITE).setAction("İZİN VER",View.OnClickListener {
                    permissionLaunncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }).show()

            }else{
                permissionLaunncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)

            }

        }else{
            val intentToGalery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGalery)

        }



        }

        private fun registerLauncher(){
            activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
                if (result.resultCode == RESULT_OK){
                    val intentFromResult = result.data
                    if (intentFromResult != null){
                       val imageData = intentFromResult.data
                       // binding.imageView.setImageURI(imageData)


                        try {
                            if (Build.VERSION.SDK_INT >= 28){
                            val source = ImageDecoder.createSource(requireContext().contentResolver,imageData!!)
                                ImageDecoder.decodeBitmap(source)
                            binding.imageView.setImageBitmap(selectedBitmap)
                            }else{
                                MediaStore.Images.Media.getBitmap(requireContext().contentResolver,imageData)
                            }
                            binding.imageView.setImageBitmap(selectedBitmap)


                        }catch (e: Exception){
                            e.printStackTrace()
                        }


                    }
                }

            }
            permissionLaunncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->

                if (result){

                    val intentToGalery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intentToGalery)


                }else{
                    Toast.makeText(requireContext(), "İZİN GEREKLİ", Toast.LENGTH_LONG).show()
                }
            }
        }
    }