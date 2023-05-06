package com.example.dreamjobapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class updateprofile : AppCompatActivity() {

    private lateinit var userId: String
    private lateinit var dbRef: DatabaseReference

    private lateinit var updateUsername : EditText
    private lateinit var updateEmail : EditText
    private lateinit var updateContactsNo : EditText
    private lateinit var updateLocation : EditText
    private lateinit var updateDescription : EditText
    private lateinit var updateBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updateprofile)

        updateUsername = findViewById(R.id.upUsername)
        updateEmail = findViewById(R.id.upEmail)
        updateContactsNo = findViewById(R.id.upContactsNo)
        updateLocation = findViewById(R.id.upLocation)
        updateDescription = findViewById(R.id.upDescription)
        updateBtn = findViewById(R.id.upUpdateBtn)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        // Retrieve the feedbackId from the intent
        userId = intent.getStringExtra("user") ?: ""

        // Retrieve the details from the database and display it
        dbRef.child(userId).get().addOnSuccessListener { snapshot ->
            val StoreModel = snapshot.getValue(storeModel::class.java)
            StoreModel?.let {
                updateUsername.setText(it.user)
                updateEmail.setText(it.userEmail)
                updateContactsNo.setText(it.userNumber)
                updateLocation.setText(it.userLocation)
                updateDescription.setText(it.userDesc)
            }
        }.addOnFailureListener{err->
            Toast.makeText(this, "Failed to retrieve feedback. Error: ${err.message}", Toast.LENGTH_SHORT).show()
        }

        updateBtn.setOnClickListener {
            updateProfile()
        }
    }

    private fun updateProfile() {
        val upUsername = updateUsername.text.toString()
        val upEmail = updateEmail.text.toString()
        val upContactsNo = updateContactsNo.text.toString()
        val upLocation = updateLocation.text.toString()
        val upDescription = updateDescription.text.toString()

        if(upUsername.isEmpty()){
            updateUsername.error = "Please fill this field"
        }
        if(upEmail.isEmpty()){
            updateEmail.error = "Please fill this field"
        }
        if(upContactsNo.isEmpty()){
            updateContactsNo.error = "Please fill this field"
        }
        if(upLocation.isEmpty()){
            updateLocation.error = "Please fill this field"
        }
        if(upDescription.isEmpty()){
            updateDescription.error = "Please fill this field"
        }

        // Update the user details in the database
        val updatedUser = storeModel(upUsername, upEmail, upContactsNo, upLocation, upDescription)
        dbRef.child(userId).setValue(updatedUser).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to update profile. Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }
}