package com.example.maddreamjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class fetchingJob : AppCompatActivity() {

    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var jobList: ArrayList<JobModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching_job)

        jobRecyclerView = findViewById(R.id.rvJob)
        jobRecyclerView.layoutManager = LinearLayoutManager(this)
        jobRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        jobList = arrayListOf<JobModel>()

        getJobsData()
    }

    private fun getJobsData() {


        //empRecyclerView.visibility = View.GONE
        //tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Jobs")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                jobList.clear()
                if (snapshot.exists()){
                    for (jobSnap in snapshot.children){
                        val jobData = jobSnap.getValue(JobModel::class.java)
                        jobList.add(jobData!!)
                    }
                    val mAdapter = jobAdapter(jobList)
                    jobRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : jobAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@fetchingJob, job_details::class.java)

                            //put extras
                            intent.putExtra("jobId", jobList[position].jobId)
                            intent.putExtra("jobType", jobList[position].jobType)
                            intent.putExtra("jobCompany", jobList[position].jobCompany)
                            intent.putExtra("jobPosition", jobList[position].jobPosition)
                            intent.putExtra("jobRequirement", jobList[position].jobRequirement)
                            intent.putExtra("jobSalary", jobList[position].jobSalary)
                            intent.putExtra("jobContact", jobList[position].jobContact)
                            startActivity(intent)
                        }

                    })

                    //empRecyclerView.visibility = View.VISIBLE
                    //tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}