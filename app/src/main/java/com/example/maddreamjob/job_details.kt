package com.example.maddreamjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class job_details : AppCompatActivity() {

    private lateinit var tvJobId: TextView
    private lateinit var tvJobType: TextView
    private lateinit var tvJobCompany: TextView
    private lateinit var tvJobPosition: TextView
    private lateinit var tvJobRequirements: TextView
    private lateinit var tvJobSalary: TextView
    private lateinit var tvJobContact: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("jobId").toString(),
                intent.getStringExtra("jobType").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("jobId").toString()
            )
        }
    }

    private fun initView() {
        tvJobId = findViewById(R.id.tvJobId)
        tvJobType = findViewById(R.id.tvJobType)
        tvJobCompany = findViewById(R.id.tvJobCompany)
        tvJobPosition = findViewById(R.id.tvJobPosition)
        tvJobRequirements = findViewById(R.id.tvJobRequirements)
        tvJobSalary = findViewById(R.id.tvJobSalary)
        tvJobContact = findViewById(R.id.tvJobContact)


        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvJobId.text = intent.getStringExtra("jobId")
        tvJobType.text = intent.getStringExtra("jobType")
        tvJobCompany.text = intent.getStringExtra("jobCompany")
        tvJobPosition.text = intent.getStringExtra("jobPosition")
        tvJobRequirements.text = intent.getStringExtra("jobRequirement")
        tvJobSalary.text = intent.getStringExtra("jobSalary")
        tvJobContact.text = intent.getStringExtra("jobContact")


    }

    private fun openUpdateDialog(
        jobId: String,
        jobType: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.job_update, null)

        mDialog.setView(mDialogView)

        val upJobType = mDialogView.findViewById<EditText>(R.id.upJobType)
        val upJobCompany = mDialogView.findViewById<EditText>(R.id.upJobCompany)
        val upJobPosition = mDialogView.findViewById<EditText>(R.id.upJobPosition)
        val upJobRequirements = mDialogView.findViewById<EditText>(R.id.upJobRequirements)
        val upJobSalary = mDialogView.findViewById<EditText>(R.id.upJobSalary)
        val upJobContact = mDialogView.findViewById<EditText>(R.id.upJobContact)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        upJobType.setText(intent.getStringExtra("jobType").toString())
        upJobCompany.setText(intent.getStringExtra("jobCompany").toString())
        upJobPosition.setText(intent.getStringExtra("jobPosition").toString())
        upJobRequirements.setText(intent.getStringExtra("jobRequirement").toString())
        upJobSalary.setText(intent.getStringExtra("jobSalary").toString())
        upJobContact.setText(intent.getStringExtra("jobContact").toString())

        mDialog.setTitle("Updating $jobType Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateJobData(
                jobId,
                upJobType.text.toString(),
                upJobCompany.text.toString(),
                upJobPosition.text.toString(),
                upJobRequirements.text.toString(),
                upJobSalary.text.toString(),
                upJobContact.text.toString()
            )

            Toast.makeText(applicationContext, "Job Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvJobType.text = upJobType.text.toString()
            tvJobCompany.text = upJobCompany.text.toString()
            tvJobPosition.text = upJobPosition.text.toString()
            tvJobRequirements.text = upJobRequirements.text.toString()
            tvJobSalary.text = upJobSalary.text.toString()
            tvJobContact.text = upJobContact.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateJobData(
        id: String,
        type: String,
        company: String,
        position: String,
        requirements: String,
        salary: String,
        contact: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Jobs").child(id)
        val jobInfo = JobModel(id, type, company, position, requirements, salary, contact)
        dbRef.setValue(jobInfo)
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Jobs").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Job deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, fetchingJob::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
}