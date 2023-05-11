package com.example.maddreamjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbRef = FirebaseDatabase.getInstance().getReference("user_login")

        email = findViewById(R.id.editTextTextPersonName6)
        password = findViewById(R.id.editTextTextPersonName7)
        loginButton = findViewById(R.id.login_button)
        signUpButton = findViewById(R.id.sign_up_button)


        loginButton.setOnClickListener {
            val userName = email.text.toString()
            val password = password.text.toString()

            // Validate user inputs
            if (userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val loginId = dbRef.push().key!!

            val loginData = Login(loginId, userName, password)
            dbRef.child(loginId).setValue(loginData)
                .addOnSuccessListener {
                    // Perform login logic here
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                    // Switch to the UserRegister activity
                    //val intent = Intent(this, seeker_menu::class.java)
                    val intent = Intent(this@LoginActivity, seeker_menu::class.java)
                    startActivity(intent)
                    finish()
                }

                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save login data", Toast.LENGTH_SHORT).show()
                }
        }
        //register button binding
        signUpButton.setOnClickListener {
            //before login in validate data

            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    data class Login(
        val userId: String,
        val email: String,
        val password: String
    )
}




//
//
//package com.example.dreamjobapp
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.database.*
//
//class LoginActivity : AppCompatActivity() {
//
//    private lateinit var emailEditText: EditText
//    private lateinit var passwordEditText: EditText
//    private lateinit var loginButton: Button
//    private lateinit var signUpButton: Button
//
//    private lateinit var dbRef: DatabaseReference
//    private lateinit var valueEventListener: ValueEventListener
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//
//        dbRef = FirebaseDatabase.getInstance().getReference("user_login")
//        emailEditText = findViewById(R.id.editTextTextPersonName6)
//        passwordEditText = findViewById(R.id.editTextTextPersonName7)
//        loginButton = findViewById(R.id.login_button)
//        signUpButton = findViewById(R.id.sign_up_button)
//
//        loginButton.setOnClickListener {
//            val userName = emailEditText.text.toString()
//            val password = passwordEditText.text.toString()
//
//            // Validate user inputs
//            if (userName.isEmpty() || password.isEmpty()) {
//                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            // Query Firebase for the user's login data
//            valueEventListener = object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    var found = false
//                    for (childSnapshot in snapshot.children) {
//                        val loginData = childSnapshot.getValue(Login::class.java)
//                        if (loginData != null && loginData.email == userName && loginData.password == password) {
//                            // User found, perform login logic here
//                            found = true
//                            Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
//
//                            // Switch to the UserRegister activity
//                            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                            break
//                        }
//                    }
//                    if (!found) {
//                        Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(this@LoginActivity, "Failed to read login data", Toast.LENGTH_SHORT).show()
//                }
//            }
//            dbRef.addListenerForSingleValueEvent(valueEventListener)
//        }
//
//        //register button binding
//        signUpButton.setOnClickListener {
//            // before login in validate data
//            val intent = Intent(this, RegistrationActivity::class.java)
//            startActivity(intent)
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        // Remove the ValueEventListener to avoid memory leaks
//        dbRef.removeEventListener(valueEventListener)
//    }
//
//    data class Login(
//        val userId: String = "",
//        val email: String = "",
//        val password: String = ""
//    )
//
//}
//
