package com.example.loginpage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.maddreamjob.R
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserProfileActivity : AppCompatActivity() {

    private lateinit var deleteButton: Button
    private lateinit var editButton: Button

    private lateinit var dbref: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val appName=getString(R.string.app_name)
        title=appName
        FirebaseApp.initializeApp(this)
        //dbref = FirebaseDatabase.getInstance().getReference("profile")
        FirebaseDatabase.getInstance().getReference("profile")
        //get data from register
        val intent=intent
        val name=intent.getStringExtra("name")
        val email=intent.getStringExtra("email")
        val phoneNumber=intent.getStringExtra("phoneNumber")
        val gender=intent.getStringExtra("gender")
        val age=intent.getStringExtra("age")

        //set
        val nameView: TextView = findViewById(R.id.name_edit_text)
        val emailView: TextView = findViewById(R.id.email_edit_text)
        val phoneNumberView: TextView = findViewById(R.id.editTextPhone)
        val genderView: TextView = findViewById(R.id.edt_gender)
        val ageView: TextView = findViewById(R.id.edt_age)

        nameView.text = "Name: $name"
        email?.let {
            if (it.isNotEmpty()) {
                emailView.text = "Email: $email"
            } else {
                emailView.text = "Email: Not provided"
            }
        }
        phoneNumberView.text = "PhoneNumber: $phoneNumber"
        genderView.text = "Gender: $gender"
        ageView.text = "Age: $age"

        // Set up button click listeners
        deleteButton = findViewById(R.id.btn_delete)
        editButton = findViewById(R.id.btn_edit)

        deleteButton.setOnClickListener {
            // Delete profile from Firebase Realtime Database
            deleteProfile(email)
        }

        editButton.setOnClickListener {
            val intent = Intent(this, ProfileEditpage::class.java)
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("phoneNumber", phoneNumber)
            intent.putExtra("gender", gender)
            intent.putExtra("age", age)
            startActivity(intent)
        }
    }

    private fun deleteProfile(email: String?) {
        email?.let {
            // Delete profile from Firebase Realtime Database
            dbref.reference.child("profile").orderByChild("email").equalTo(it)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (ds in snapshot.children) {
                            ds.ref.removeValue()
                            Toast.makeText(
                                this@UserProfileActivity,
                                "Profile deleted",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        // Finish the activity after deleting the profile
                        finish()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@UserProfileActivity,
                            "Failed to delete profile: ${error.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
}
