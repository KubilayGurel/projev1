package com.kubilaygurel.artbookfragment.view

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog.show
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
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
import java.io.ByteArrayOutputStream


class GaleryFragment : Fragment() {

    private lateinit var db: ArtlistDataBase
    private lateinit var artlistDao: artListDao
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLaunncher: ActivityResultLauncher<String>
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
        }
        binding.imageView.setOnClickListener {
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

    private fun makeSmallerBitmap(image: Bitmap, maximumSize: Int): Bitmap {

        var width = image.width
        var height = image.height
        val bitmapRatio: Double = width.toDouble() / height.toDouble()

        if (bitmapRatio > 1) {

            width = maximumSize
            val scaledHeight = width / bitmapRatio
            height = scaledHeight.toInt()

        } else {
            width = maximumSize
            val scaledWith = height * bitmapRatio
            width = scaledWith.toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGaleryBinding.inflate(inflater, container, false)
        return binding.root

    }


    fun selectImage(view: View) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        android.Manifest.permission.READ_MEDIA_IMAGES
                    )
                ) {
                    Snackbar.make(view, "Galeri için izin ver!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("İZİN VER", View.OnClickListener {
                            permissionLaunncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                        }).show()

                } else {
                    permissionLaunncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)


                }

            } else {
                val intentToGalery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGalery)

            }

        } else {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    Snackbar.make(view, "Galeri için izin ver!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("İZİN VER", View.OnClickListener {
                            permissionLaunncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        }).show()

                } else {
                    permissionLaunncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)

                }

            } else {
                val intentToGalery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGalery)

            }

        }

    }

    private fun registerLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val intentFromResult = result.data
                    if (intentFromResult != null) {
                        val imageData = intentFromResult.data



                        try {
                            if (Build.VERSION.SDK_INT >= 28) {
                                val source = ImageDecoder.createSource(
                                    requireContext().contentResolver,
                                    imageData!!
                                )
                                ImageDecoder.decodeBitmap(source)
                                binding.imageView.setImageBitmap(imageData)
                            } else {
                                MediaStore.Images.Media.getBitmap(
                                    requireContext().contentResolver,
                                    imageData
                                )
                            }
                            binding.imageView.setImageBitmap(imageData)


                        } catch (e: Exception) {
                            e.printStackTrace()
                        }


                    }
                }

            }
        permissionLaunncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->

                if (result) {

                    val intentToGalery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intentToGalery)


                } else {
                    Toast.makeText(requireContext(), "İZİN GEREKLİ", Toast.LENGTH_LONG).show()
                }
            }
    }
}