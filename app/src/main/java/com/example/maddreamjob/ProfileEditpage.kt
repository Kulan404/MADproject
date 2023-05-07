package com.example.loginpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.maddreamjob.R
import com.google.firebase.database.FirebaseDatabase

class ProfileEditpage : AppCompatActivity() {

    private lateinit var dbref: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profileeditpage)


        val intent=intent
        val name=intent.getStringExtra("name")
        val email=intent.getStringExtra("email")
        val phoneNumber=intent.getStringExtra("phoneNumber")
        val gender=intent.getStringExtra("gender")
        val age=intent.getStringExtra("age")

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
    }
}