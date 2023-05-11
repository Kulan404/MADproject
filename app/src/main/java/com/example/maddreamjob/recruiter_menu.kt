package com.example.maddreamjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView

class recruiter_menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruiter_menu)

        //post job
        val postjobcard = findViewById<CardView>(R.id.POSTJOB_CARD)
        postjobcard.setOnClickListener {
            val Intent = Intent(this,insert_job::class.java)
            startActivity(Intent)
        }

        //history
        val historycard = findViewById<CardView>(R.id.HISTORY_CARD)
        historycard.setOnClickListener {
            val Intent = Intent(this,fetchingJob::class.java)
            startActivity(Intent)
        }

        //feedback
        val feedbackcard = findViewById<CardView>(R.id.FEEDBACK_CARD)
        feedbackcard.setOnClickListener {
            val Intent = Intent(this,Feedbackpage::class.java)
            startActivity(Intent)
        }

    }
}