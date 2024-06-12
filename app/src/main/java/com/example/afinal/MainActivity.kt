package com.example.afinal

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.afinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //正在研究存資料
        val edtName = findViewById<TextView>(R.id.et_name)
        val edtPhone = findViewById<TextView>(R.id.et_phone)
        val edtEmail = findViewById<TextView>(R.id.et_email)

        findViewById<Button>(R.id.btn_add_contact).setOnClickListener {
            val sharePref = getPreferences(MODE_PRIVATE) ?: return@setOnClickListener
            with(sharePref.edit()){
                putString("Name",edtName.text.toString()) //儲存Name
                putString("Phone_Number",edtPhone.text.toString()) //儲存Phone_Number
                putString("Email",edtEmail.text.toString()) //儲存Email
                apply() //先這樣 (較commit快)
            }
        }
        /*

        findViewById<Button>(R.id.btnSave).setOnClickListener {
                    val sharedPref = getPreferences(MODE_PRIVATE) ?: return@setOnClickListener
                                with (sharedPref.edit()) {
                                putString("userName", edtName.text.toString())
                                putFloat("rating", ratingBar.rating)
                                apply()
                                }


        */
    }
}