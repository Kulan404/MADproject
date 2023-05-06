package com.example.feedbakpages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class feedbackview : AppCompatActivity() {

    private lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedbackview)

        dbref = FirebaseDatabase.getInstance().getReference("feedback")

        // Intent started this activity and the feedback data
        val intent = intent
        val name = intent.getStringExtra("name")
        val feedback = intent.getStringExtra("feedback")
        val email = intent.getStringExtra("email")
        val feedbackId = intent.getStringExtra("id")

        // Find TextViews in the layout and display feedback data
        val nameView: TextView = findViewById(R.id.name_view)
        val feedbackView: TextView = findViewById(R.id.feedback_view)
        val emailView: TextView = findViewById(R.id.email_view)

        nameView.text = "Name: $name"
        feedbackView.text = "Feedback: $feedback"

        if (email != null && email.isNotEmpty()) {
            emailView.text = "Email: $email"
        } else {
            emailView.text = "Email: Not provided"
        }

        // Set up the button click listeners
        val deleteButton: Button = findViewById(R.id.delete_button)
        val editButton: Button = findViewById(R.id.edit_button)

        deleteButton.setOnClickListener {
            if (feedbackId != null) {
                dbref.child(feedbackId).removeValue()
            }
            finish()
        }

        editButton.setOnClickListener {
            val intent = Intent(this, FeedbackEdit::class.java)
            intent.putExtra("name", name)
            intent.putExtra("feedback", feedback)
            intent.putExtra("email", email)


            startActivity(intent)
        }
    }

    data class Feedback(val name: String, val feedback: String, val email: String)
}
