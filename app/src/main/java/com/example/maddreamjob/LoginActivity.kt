package com.example.loginpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.maddreamjob.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference


class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button


    private lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val appName = getString(R.string.app_name)
        title = appName
        dbref = FirebaseDatabase.getInstance().getReference("login")

        usernameEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginButton = findViewById(R.id.login_button)
        signUpButton = findViewById(R.id.sign_up_button)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            startActivity(Intent(this,UserProfileActivity::class.java))
            finish()

            // Validate user inputs
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            val loginId = dbref.push().key!!

            val loginData = Login(username, password)
            dbref.child(loginId).setValue(loginData)
                .addOnSuccessListener {
                    // Perform login logic here
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save login data", Toast.LENGTH_SHORT).show()
                }
        }

        //register button binding
        signUpButton.setOnClickListener {
            //before login in validate data


            // Switch to the Registration activity
            val intent = Intent(this, registerPage::class.java)
            startActivity(intent)
        }

        }



}


data class Login(
    val email: String,
    val password: String
)
