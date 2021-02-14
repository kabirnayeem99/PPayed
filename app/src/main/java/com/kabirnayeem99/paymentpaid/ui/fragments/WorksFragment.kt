package com.kabirnayeem99.paymentpaid.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.adapters.WorkAdapter
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel
import com.kabirnayeem99.paymentpaid.ui.activities.WorkDetailsActivity
import kotlinx.android.synthetic.main.fragment_works.*

class WorksFragment : Fragment(R.layout.fragment_works) {
    lateinit var workAdapter: WorkAdapter
    lateinit var workViewModel: WorkViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workViewModel = ViewModelProviders.of(this).get(WorkViewModel::class.java)

        initRecyclerView()

        fabAddNewWorksWorks.setOnClickListener {
            val intent = Intent(activity, WorkDetailsActivity::class.java)
            startActivity(intent)
        }
    }


    private fun initRecyclerView() {
        workAdapter = WorkAdapter()

        workViewModel.allWorks.observe(viewLifecycleOwner, { works: List<Work> ->
            workAdapter.differ.submitList(works)
        })

        rvWorkListWorks.apply {
            adapter = workAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    companion object {
        private const val TAG = "WorksFragment"
    }
}