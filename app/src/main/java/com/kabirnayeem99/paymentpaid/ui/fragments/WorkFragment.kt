package com.kabirnayeem99.paymentpaid.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.adapters.WorkAdapter
import com.kabirnayeem99.paymentpaid.data.db.WorkDatabase
import com.kabirnayeem99.paymentpaid.data.repositories.WorkRepository
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel
import com.kabirnayeem99.paymentpaid.ui.WorkViewModelProviderFactory
import com.kabirnayeem99.paymentpaid.ui.activities.WorkDetailsActivity
import com.kabirnayeem99.paymentpaid.utils.Resource
import kotlinx.android.synthetic.main.fragment_works.*

class WorkFragment : Fragment(R.layout.fragment_works) {
    lateinit var workAdapter: WorkAdapter
    lateinit var workViewModel: WorkViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val workRepository = WorkRepository(WorkDatabase(requireActivity()))

        val workViewModelProviderFactory = WorkViewModelProviderFactory(workRepository)
        workViewModel = ViewModelProvider(requireActivity(), workViewModelProviderFactory).get(WorkViewModel::class.java)

        initRecyclerView()

        fabAddNewWorksWorks.setOnClickListener {
            val intent = Intent(activity, WorkDetailsActivity::class.java)
            startActivity(intent)
        }
    }


    private fun initRecyclerView() {
        workAdapter = WorkAdapter()

        workViewModel.allWorks.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> showLoading()
                is Resource.Success -> {
                    workAdapter.differ.submitList(resource.data)
                    hideLoading()
                }
                is Resource.Error -> showError()
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

    private fun showError() {
        errorImageWork.visibility = View.VISIBLE
    }

    companion object
}