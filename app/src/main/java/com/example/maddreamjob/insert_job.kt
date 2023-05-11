package com.example.maddreamjob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class insert_job : AppCompatActivity() {

    private lateinit var insertType: EditText
    private lateinit var insertCompany: EditText
    private lateinit var insertPosition: EditText
    private lateinit var insertRequirement: EditText
    private lateinit var insertSalary: EditText
    private lateinit var insertContact: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_job)

        insertType = findViewById(R.id.insert_type)
        insertCompany = findViewById(R.id.insert_company)
        insertPosition = findViewById(R.id.insert_position)
        insertRequirement = findViewById(R.id.insert_requirement)
        insertSalary = findViewById(R.id.insert_salary)
        insertContact = findViewById(R.id.insert_contact)
        btnSaveData = findViewById(R.id.btn_post)

        dbRef = FirebaseDatabase.getInstance().getReference("Jobs")

        btnSaveData.setOnClickListener {
            saveJobsData()
        }
    }

    private fun saveJobsData() {

        //getting values
        val jobType = insertType.text.toString()
        val jobCompany = insertCompany.text.toString()
        val jobPosition = insertPosition.text.toString()
        val jobRequirement = insertRequirement.text.toString()
        val jobSalary = insertSalary.text.toString()
        val jobContact = insertContact.text.toString()

        if (jobType.isEmpty()) {
            insertType.error = "Please enter job type"
        }
        if (jobCompany.isEmpty()) {
            insertCompany.error = "Please enter company name"
        }
        if (jobPosition.isEmpty()) {
            insertPosition.error = "Please enter job position"
        }
        if (jobRequirement.isEmpty()) {
            insertRequirement.error = "Please enter job requirement"
        }
        if (jobSalary.isEmpty()) {
            insertSalary.error = "Please enter salary"
        }
        if (jobContact.isEmpty()) {
            insertContact.error = "Please enter job contact"
        }

        val jobId = dbRef.push().key!!

        val job = JobModel(jobId, jobType , jobCompany, jobPosition, jobRequirement, jobSalary, jobContact)

        dbRef.child(jobId).setValue(job)
            .addOnCompleteListener {
                Toast.makeText(this, "Job inserted successfully", Toast.LENGTH_LONG).show()

                insertType.text.clear()
                insertCompany.text.clear()
                insertPosition.text.clear()
                insertRequirement.text.clear()
                insertSalary.text.clear()
                insertContact.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}