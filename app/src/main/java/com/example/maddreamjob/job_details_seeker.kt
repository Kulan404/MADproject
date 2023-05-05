package com.example.maddreamjob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class job_details_seeker : AppCompatActivity() {

    private lateinit var tvJobId: TextView
    private lateinit var tvJobType: TextView
    private lateinit var tvJobCompany: TextView
    private lateinit var tvJobPosition: TextView
    private lateinit var tvJobRequirements: TextView
    private lateinit var tvJobSalary: TextView
    private lateinit var tvJobContact: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_details_seeker)

        initView()
        setValuesToViews()
    }

    private fun initView() {
        tvJobId = findViewById(R.id.tvJobId)
        tvJobType = findViewById(R.id.tvJobType)
        tvJobCompany = findViewById(R.id.tvJobCompany)
        tvJobPosition = findViewById(R.id.tvJobPosition)
        tvJobRequirements = findViewById(R.id.tvJobRequirements)
        tvJobSalary = findViewById(R.id.tvJobSalary)
        tvJobContact = findViewById(R.id.tvJobContact)



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
}