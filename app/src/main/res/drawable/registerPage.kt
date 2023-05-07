package com.example.loginpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference

class registerPage : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var genderEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button

    private lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)


        dbref = FirebaseDatabase.getInstance().getReference("register")

        nameEditText = findViewById(R.id.name_edit_text)
        emailEditText = findViewById(R.id.email_edit_text)
        phoneNumberEditText = findViewById(R.id.editTextPhone)
        genderEditText = findViewById(R.id.edt_gender)
        ageEditText = findViewById(R.id.edt_age)
        passwordEditText = findViewById(R.id.password_edit_text)
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text)
        registerButton = findViewById(R.id.register_button)

        registerButton.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString().toInt()
            val gender = genderEditText.text.toString()
            val age = ageEditText.text.toString().toInt()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            // Validate user inputs
            if (name.isEmpty() || email.isEmpty() ||   gender.isEmpty() ||  password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save registration data to Firebase database
            val user = User(name, email, phoneNumber, gender, age, password, confirmPassword)
            dbref.child(name).setValue(user)

            // Show success message
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

            //open profile
            startActivity(Intent(this,UserProfileActivity::class.java))
            finish()
        }

        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            // Create an Intent to start the LoginActivity
            val intent = Intent(this, LoginActivity::class.java)

            // Pass any data you want to the LoginActivity using the putExtra() method
            val name = nameEditText.text.toString()
            intent.putExtra("name", name)

            val email = emailEditText.text.toString()
            intent.putExtra("email", email)

            val password = passwordEditText.text.toString()
            intent.putExtra("password", password)

            // Start the LoginActivity
            startActivity(intent)
        }
    }

}

data class User(val name: String, val email: String, val phoneNumber: Int, val gender: String, val age: Int, val password: String, val confirmPassword: String)
