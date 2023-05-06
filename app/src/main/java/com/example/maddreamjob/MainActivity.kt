package com.example.feedbakpages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the button view
        val feedbackButton: Button = findViewById(R.id.feedback_button)
        val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference()

        // Set up the button click listener
        feedbackButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Feedbackpage::class.java)
            startActivity(intent)
        })
    }
}
