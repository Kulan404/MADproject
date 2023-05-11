package com.example.maddreamjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class seeker_menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeker_menu)

        //seeker profile
        val seekerprofile = findViewById<CardView>(R.id.PROFILE_CARD)
        seekerprofile.setOnClickListener {
            val Intent = Intent(this,userprofile::class.java)
            startActivity(Intent)
        }

        //logout
        val logout = findViewById<CardView>(R.id.LOGOUT_CARD)
        logout.setOnClickListener {
            val Intent = Intent(this,LoginActivity::class.java)
            startActivity(Intent)
        }
    }
}