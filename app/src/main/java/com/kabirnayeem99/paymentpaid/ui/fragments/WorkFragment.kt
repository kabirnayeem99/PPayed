package com.kabirnayeem99.paymentpaid.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        setUpViewModel()
        initRecyclerView()
        addNewWorkListener()
        updateWorkListener()
    }

    /**
     * Sets up the [WorkViewModel] for this [WorkFragment]
     */
    private fun setUpViewModel() {
        workViewModel = (activity as HomeActivity).workViewModel
    }

    /**
     * This method, if a work in the list is pressed, will open a work editing activity
     */
    private fun updateWorkListener() {
        workAdapter.setOnItemClickListener { work ->
            val intent = Intent(activity, WorkDetailsActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("work", work)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    /**
     * This method, if floating action button is clicked
     * open a activity to add new work
     */
    private fun addNewWorkListener() {
        fabAddNewWorksWorks.setOnClickListener {
            val intent = Intent(activity, WorkDetailsActivity::class.java)
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

        val itemTouchHelper = ItemTouchHelper(setUpSwipeToDelete())
        itemTouchHelper.attachToRecyclerView(rvWorkListWorks)
    }

    private fun showLoading() {
        progressBarWork.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBarWork.visibility = View.INVISIBLE
    }

    /**
     * This method implements swiping right to delete a work
     * it extends the [ItemTouchHelper.SimpleCallback] method
     */
    private fun setUpSwipeToDelete(): ItemTouchHelper.SimpleCallback {

        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val currentItemPosition = viewHolder.adapterPosition
                val currentWork = workAdapter.differ.currentList[currentItemPosition]
                workViewModel.delete(currentWork)
            }

        }
    }


}