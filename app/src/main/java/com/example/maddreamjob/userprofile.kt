package com.example.maddreamjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class userprofile : AppCompatActivity() {

    private lateinit var dbRef : DatabaseReference
    private lateinit var userId: String

    private lateinit var compUser : TextView
    private lateinit var compMail : TextView
    private lateinit var compNumber : TextView
    private lateinit var compLoc : TextView
    private lateinit var compAge : TextView
    private lateinit var compGender : TextView
    private lateinit var compPassword : TextView
    private lateinit var compUpdate : Button
    private lateinit var compDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userprofile)

        compUser = findViewById(R.id.compUserName)
        compMail = findViewById(R.id.compEmail)
        compNumber = findViewById(R.id.compContactNo)
        compLoc = findViewById(R.id.compLocation)
        compAge = findViewById(R.id.compAge)
        compGender = findViewById(R.id.compGender2)
        compPassword = findViewById(R.id.compPassword)
        compUpdate = findViewById(R.id.compUpdateBtn)
        compDelete = findViewById(R.id.compDeleteBtn)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        // Retrieve the feedbackId from the intent
        userId = intent.getStringExtra("user") ?: ""

        // Retrieve the details from the database and display it
        dbRef.child(userId).get().addOnSuccessListener { snapshot ->
            val StoreModel = snapshot.getValue(storeModel::class.java)
            StoreModel?.let {
//
                compUser.text = it.user
                compMail.text = it.userEmail
                compNumber.text = it.userNumber
                compLoc.text = it.userLocation
                compGender.text = it.userGender
                compAge.text = it.userAge
                compPassword.text = it.userPassword



            }
        }.addOnFailureListener{err->
            Toast.makeText(this, "Failed to retrieve feedback. Error: ${err.message}", Toast.LENGTH_SHORT).show()
        }

        compDelete.setOnClickListener {
            confirmDelete()
        }

        compUpdate.setOnClickListener {
            val intent = Intent(this, updateprofile::class.java)
            intent.putExtra("user", userId)
            startActivity(intent)
        }

    }

    private fun confirmDelete() {
        showDeleteConfirmationDialog()
    }

    private fun showDeleteConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Are you sure you want to delete the profile?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            deleteCompProfile()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun deleteCompProfile() {
        dbRef.child(userId).removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Profile deleted successfully", Toast.LENGTH_SHORT).show()
                finish() // Close the Updates activity
            }
            .addOnFailureListener { err ->
                Toast.makeText(this, "Failed to delete profile. Error: ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }

}