package com.example.maddreamjob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class updateprofile : AppCompatActivity() {

    private lateinit var userId: String
    private lateinit var dbRef: DatabaseReference

    private lateinit var updateUsername: EditText
    private lateinit var updateEmail: EditText
    private lateinit var updateContactsNo: EditText
    private lateinit var updateLocation: EditText
    private lateinit var updateAge: EditText
    private lateinit var updateGender: EditText
    private lateinit var updatePassword: EditText
    private lateinit var updateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updateprofile)

        updateUsername = findViewById(R.id.upUsername)
        updateEmail = findViewById(R.id.upEmail)
        updateContactsNo = findViewById(R.id.upContactsNo)
        updateLocation = findViewById(R.id.upLocation)
        updateAge = findViewById(R.id.upAge)
        updateGender = findViewById(R.id.upGender)
        updatePassword = findViewById(R.id.upPassword)
        updateBtn = findViewById(R.id.upUpdateBtn)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        // Retrieve the userId from the intent
        userId = intent.getStringExtra("user") ?: ""
        Log.d("userId", userId)

        // Retrieve the details from the database and display them
        dbRef.child(userId).get().addOnSuccessListener { snapshot ->
            val StoreModel = snapshot.getValue(storeModel::class.java)
            StoreModel?.let {
                updateUsername.setText(it.user.toString())
                updateEmail.setText(it.userEmail.toString())
                updateContactsNo.setText(it.userNumber.toString())
                updateLocation.setText(it.userLocation.toString())
                updateAge.setText(it.userAge.toString())
                updateGender.setText(it.userGender.toString())
                updatePassword.setText(it.userPassword.toString())
            }
        }.addOnFailureListener { err ->
            Toast.makeText(
                this,
                "Failed to retrieve user details. Error: ${err.message}",
                Toast.LENGTH_SHORT
            ).show()
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
        val upAge = updateAge.text.toString()
        val upGender = updateGender.text.toString()
        val upPassword = updatePassword.text.toString()

        // Validate input fields
        if (upUsername.isEmpty()) {
            updateUsername.error = "Please enter a username"
            return
        }
        if (upEmail.isEmpty()) {
            updateEmail.error = "Please enter an email address"
            return
        }
        if (upContactsNo.isEmpty()) {
            updateContactsNo.error = "Please enter a contact number"
            return
        }
        if (upLocation.isEmpty()) {
            updateLocation.error = "Please enter a location"
            return
        }
        if (upAge.isEmpty()) {
            updateAge.error = "Please enter an age"
            return
        }
        if (upGender.isEmpty()) {
            updateGender.error = "Please enter a gender"
            return
        }
        if (upPassword.isEmpty()) {
            updatePassword.error = "Please enter a password"
            return
        }

        // Update the user details in the database
        val updatedUser = storeModel(
            upUsername,
            upEmail,
            upContactsNo,
            upLocation,
            upAge,
            upGender,
            upPassword
        )
        dbRef.child(userId).setValue(updatedUser).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()

                // Fetch the updated data from Firebase and display it
                dbRef.child(userId).get().addOnSuccessListener { snapshot ->
                    val StoreModel = snapshot.getValue(storeModel::class.java)
                    StoreModel?.let {
                        updateUsername.setText(it.user.toString())
                        updateEmail.setText(it.userEmail.toString())
                        updateContactsNo.setText(it.userNumber.toString())
                        updateLocation.setText(it.userLocation.toString())
                        updateAge.setText(it.userAge.toString())
                        updateGender.setText(it.userGender.toString())
                        updatePassword.setText(it.userPassword.toString())
                    }
                }.addOnFailureListener { err ->
                    Toast.makeText(
                        this,
                        "Failed to retrieve user details. Error: ${err.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Failed to update profile. Error: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
