package com.example.dreamjobapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {

    private lateinit var userName: EditText
    private lateinit var email: EditText
    private lateinit var contactNo: EditText
    private lateinit var location: EditText
    private lateinit var description: EditText
    private lateinit var submitBtn: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        userName = findViewById(R.id.regUserName)
        email = findViewById(R.id.regEmail)
        contactNo = findViewById(R.id.regContactNo)
        location = findViewById(R.id.regLocation)
        description = findViewById(R.id.regDescription)
        submitBtn = findViewById(R.id.regSubmitBtn)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        submitBtn.setOnClickListener{
            saveUser()
        }
    }

    private fun saveUser() {
        val user = userName.text.toString()
        val userEmail = email.text.toString()
        val userNumber = contactNo.text.toString()
        val userLocation = location.text.toString()
        val userDesc = description.text.toString()

        if (user.isEmpty()){
            userName.error = "Please fill this field"
        }
        if (userEmail.isEmpty()){
            email.error = "Please fill this field"
        }
        if (userNumber.isEmpty()){
            contactNo.error = "Please fill this field"
        }
        if (userLocation.isEmpty()){
            location.error = "Please fill this field"
        }
        if (userDesc.isEmpty()){
            description.error = "Please fill this field"
        }

        val StoreModel = storeModel(user, userEmail, userNumber, userLocation, userDesc)

        dbRef.child(user).setValue(StoreModel)
            .addOnCompleteListener {

                // Clear inserted values
                userName.text.clear()
                email.text.clear()
                contactNo.text.clear()
                location.text.clear()
                description.text.clear()

                // Display success message
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show()

                // Redirect to companyProfile activity
                val intent = Intent(this, companyprofile::class.java)
                intent.putExtra("user", user)
                startActivity(intent)

            }.addOnFailureListener{err ->
                Toast.makeText(this, "Data inserted unsuccessfully. Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }
}