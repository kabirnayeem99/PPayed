package com.kabirnayeem99.paymentpaid.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.adapters.WorkAdapter
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel
import com.kabirnayeem99.paymentpaid.ui.activities.HomeActivity
import com.kabirnayeem99.paymentpaid.ui.activities.WorkDetailsActivity
import kotlinx.android.synthetic.main.fragment_works.*

class WorkFragment : Fragment(R.layout.fragment_works) {
    private lateinit var workAdapter: WorkAdapter
    private lateinit var workViewModel: WorkViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workViewModel = (activity as HomeActivity).workViewModel

        initRecyclerView()

        fabAddNewWorksWorks.setOnClickListener {
            val intent = Intent(activity, WorkDetailsActivity::class.java)
            startActivity(intent)
        }


        workAdapter.setOnItemClickListener { work ->
            val intent = Intent(activity, WorkDetailsActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("work", work)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }


    private fun initRecyclerView() {
        workAdapter = WorkAdapter()

        workViewModel.getAllWorks().observe(viewLifecycleOwner, { workList ->
            when (workList.isEmpty()) {
                true -> showLoading()
                false -> {
                    hideLoading()
                    workAdapter.differ.submitList(workList)

                }
            }
        })

        rvWorkListWorks.apply {
            adapter = workAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showLoading() {
        progressBarWork.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBarWork.visibility = View.INVISIBLE
    }


}