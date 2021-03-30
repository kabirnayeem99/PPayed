package com.kabirnayeem99.paymentpaid.ui.fragments

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkemon.XMLtoPDF.PdfGenerator
import com.gkemon.XMLtoPDF.PdfGeneratorListener
import com.gkemon.XMLtoPDF.model.FailureResponse
import com.gkemon.XMLtoPDF.model.SuccessResponse
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.adapters.WorkAdapter
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel
import com.kabirnayeem99.paymentpaid.ui.activities.HomeActivity
import com.kabirnayeem99.paymentpaid.ui.activities.WorkDetailsActivity
import kotlinx.android.synthetic.main.fragment_works.*
import kotlin.properties.Delegates


class WorkFragment : Fragment(R.layout.fragment_works) {
    private lateinit var workAdapter: WorkAdapter
    private lateinit var workViewModel: WorkViewModel

    companion object {
        private const val TAG = "WorkFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        initRecyclerView()
        addNewWorkListener()
        updateWorkListener()

        generatePdf(view)
    }


    private fun checkPermission(): Boolean {
        return if (SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {

            var result by Delegates.notNull<Int>()
            var result1 by Delegates.notNull<Int>()

            context?.let { ctxt ->
                result = ContextCompat.checkSelfPermission(ctxt, READ_EXTERNAL_STORAGE)
                result1 = ContextCompat.checkSelfPermission(ctxt, WRITE_EXTERNAL_STORAGE)

            }
            result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
        }
    }


    private fun generatePdf(view: View) {
        if (checkPermission()) {
            Log.d(TAG, "generatePdf: generating pdf started")
            PdfGenerator.getBuilder()
                    .setContext(requireActivity())
                    .fromViewSource()
                    .fromView(view)
                    .setPageSize(PdfGenerator.PageSize.A4)
                    .setFileName("Test-PDF")
                    .setFolderName("Test-PDF-folder")
                    .openPDFafterGeneration(true)
                    .build(object : PdfGeneratorListener() {
                        override fun onFailure(failureResponse: FailureResponse) {
                            super.onFailure(failureResponse)
                        }

                        override fun showLog(log: String) {
                            super.showLog(log)
                        }

                        override fun onStartPDFGeneration() {
                            /*When PDF generation begins to start*/
                        }

                        override fun onFinishPDFGeneration() {
                            /*When PDF generation is finished*/
                        }

                        override fun onSuccess(response: SuccessResponse) {
                            super.onSuccess(response)
                        }
                    })
        }
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
            when (workList == null) {
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
//            itemAnimator = SlideInLeftAnimator()
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