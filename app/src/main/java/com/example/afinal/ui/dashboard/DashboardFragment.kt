package com.example.afinal.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.AppDatabase
import com.example.afinal.databinding.FragmentDashboardBinding
import com.example.afinal.firstEntity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var dashboardViewModel: DashboardViewModel

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

        return root
    }

    private fun addContact(it: View) {
        val name = binding.edtName.text.toString()
        val phone = binding.edtPhone.text.toString()
        val email = binding.edtEmail.text.toString()

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val contact = firstEntity(name = name, phone = phone, email = email)
        val database = context?.let { AppDatabase.getInstance(it) }

        CoroutineScope(Dispatchers.IO).launch{
            database?.userDao()?.insert(contact)
            Snackbar.make(it, "Contact added", Snackbar.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
