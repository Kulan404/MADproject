package com.example.maddreamjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Feedbackpage : AppCompatActivity() {

    private lateinit var feedbackText: EditText
    private lateinit var emailText: EditText
    private lateinit var nameText: EditText

    private lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedbackpage)

        dbref = FirebaseDatabase.getInstance().getReference("feedback")

        feedbackText = findViewById(R.id.feedback_text)
        emailText = findViewById(R.id.email_text)
        nameText = findViewById(R.id.name_text)

        val sendButton = findViewById<Button>(R.id.send_button)
        sendButton.setOnClickListener {
            val feedback = feedbackText.text.toString().trim()
            val email = emailText.text.toString().trim()
            val name = nameText.text.toString().trim()

            if (feedback.isEmpty()) {
                feedbackText.error = "Please enter your feedback"
                feedbackText.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailText.error = "Please enter a valid email address"
                emailText.requestFocus()
                return@setOnClickListener
            }

            val feedbackId = dbref.push().key!!
            val feedbackData = Feedback(name, feedback, email)
            dbref.child(feedbackId).setValue(feedbackData)

            // Navigate to FeedbackViewActivity
            val intent = Intent(this, feedbackview::class.java)
            intent.putExtra("name", name)
            intent.putExtra("feedback", feedback)
            intent.putExtra("email", email)
            startActivity(intent)

            // Clear the input fields
            feedbackText.setText("")
            emailText.setText("")
            nameText.setText("")

            Toast.makeText(this, "Feedback sent!", Toast.LENGTH_SHORT).show()
        }
    }

    data class Feedback(val name: String, val feedback: String, val email: String)
}
