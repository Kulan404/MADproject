package com.example.maddreamjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class seeker_menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeker_menu)

        //Jobs
        val jobscard = findViewById<CardView>(R.id.JOBS_CARD)
        jobscard.setOnClickListener {
            val Intent = Intent(this,fetchingJobSeeker::class.java)
            startActivity(Intent)
        }
    }
}