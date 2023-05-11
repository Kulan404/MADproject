package com.example.maddreamjob

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FeedbackEdit : AppCompatActivity() {

    // Declare variables for EditText views and feedback ID
    private lateinit var nameEditText: EditText
    private lateinit var feedbackEditText: EditText
    private lateinit var emailEditText: EditText
    private var feedbackId: String = ""
    private lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_edit)

        // Initialize Firebase database reference
        dbref = FirebaseDatabase.getInstance().getReference("feedback")

        // Get the feedback data passed from the previous activity
        val intent = intent
        val name = intent.getStringExtra("name")
        val feedback = intent.getStringExtra("feedback")
        val email = intent.getStringExtra("email")
        feedbackId = intent.getStringExtra("id").toString()

        // Find EditText views in the layout and display feedback data
        nameEditText = findViewById(R.id.name_edit_text)
        feedbackEditText = findViewById(R.id.feedback_edit_text)
        emailEditText = findViewById(R.id.email_edit_text)

        nameEditText.setText(name)
        feedbackEditText.setText(feedback)
        emailEditText.setText(email)

        // Set up the button click listener
        val saveButton: Button = findViewById(R.id.save_button)
        saveButton.setOnClickListener {
            updateFirebase()
            val intent = Intent(this, feedbackview::class.java)
            intent.putExtra("name", nameEditText.text.toString())
            intent.putExtra("feedback", feedbackEditText.text.toString())
            intent.putExtra("email", emailEditText.text.toString())
            intent.putExtra("id", feedbackId)
            startActivity(intent)

            // Clear the EditText views
            nameEditText.text.clear()
            feedbackEditText.text.clear()
            emailEditText.text.clear()
        }

        // Set up the cancel button click listener
        val cancelButton: Button = findViewById(R.id.cancel_button)
        cancelButton.setOnClickListener {
            finish() // Navigate back to previous activity
        }
    }

    private fun updateFirebase() {
        // Get the values from the EditText views
        val name = nameEditText.text.toString().trim()
        val feedback = feedbackEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()

        // Check if the name and feedback are not empty
        if (TextUtils.isEmpty(name)) {
            nameEditText.error = "Name is required"
            return
        }

        if (TextUtils.isEmpty(feedback)) {
            feedbackEditText.error = "Feedback is required"
            return
        }

        // Update the feedback in the Firebase database
        val feedbackData = Feedback(name, feedback, email)

        if (feedbackId.isNotEmpty()) {
            dbref.child(feedbackId).setValue(feedbackData)
                .addOnSuccessListener {
                    // Handle success
                }
                .addOnFailureListener { e ->
                    // Handle error
                    Toast.makeText(
                        this,
                        "Error updating feedback: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    data class Feedback(val name: String, val feedback: String, val email: String)

}
