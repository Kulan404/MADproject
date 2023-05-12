package com.example.maddreamjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val seeker = findViewById<Button>(R.id.SEEKER)
        seeker.setOnClickListener {
            val Intent = Intent(this,LoginActivity::class.java)
            startActivity(Intent)
        }

        val recruiter = findViewById<Button>(R.id.RECRUITER)
        recruiter.setOnClickListener {
            val Intent = Intent(this,recruiter_menu::class.java)
            startActivity(Intent)
        }


    }
}