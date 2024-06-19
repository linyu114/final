package com.example.afinal.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.AppDatabase
import com.example.afinal.R
import com.example.afinal.databinding.FragmentDashboardBinding
import com.example.afinal.firstEntity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import java.io.File
import java.io.IOException
import java.util.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var dashboardViewModel: DashboardViewModel
    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnAddContact.setOnClickListener {
            addContact(it)
        }
        binding.btnAddPhoto.setOnClickListener {
            openGallery()
        }
        return root
    }

    private fun addContact(view: View) {
        val name = binding.edtName.text.toString()
        val phone = binding.edtPhone.text.toString()
        val email = binding.edtEmail.text.toString()

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || imageUri == null) {
            Toast.makeText(context, "請輸入所需資料", Toast.LENGTH_SHORT).show()
            return
        }

        val photoPath = imageUri.toString() // 將 Uri 轉換為 String 來保存到資料庫中
        val contact = firstEntity(name = name, phone = phone, email = email, photoPath = photoPath)
        val database = context?.let { AppDatabase.getInstance(it) }

        CoroutineScope(Dispatchers.IO).launch {
            database?.userDao()?.insert(contact)
            withContext(Dispatchers.Main) {
                Snackbar.make(view, "新增成功!", Snackbar.LENGTH_SHORT).show()
            }
        }
        clearInputFields()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            binding.imageViewPhoto.setImageURI(imageUri)
        }
    }

    private fun clearInputFields() {
        binding.edtName.text.clear()
        binding.edtPhone.text.clear()
        binding.edtEmail.text.clear()
        binding.imageViewPhoto.setImageResource(R.drawable.emptyline) // Reset to default image
        imageUri = null // Reset imageUri
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
