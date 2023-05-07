package com.example.loginpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var  viewProfileButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference()

        loginButton = findViewById(R.id.login_button)
        registerButton = findViewById(R.id.register_button)
        viewProfileButton=findViewById(R.id.btn_profile)

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, registerPage::class.java)
            startActivity(intent)
        }

        viewProfileButton.setOnClickListener{
            val intent=Intent(this,UserProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
