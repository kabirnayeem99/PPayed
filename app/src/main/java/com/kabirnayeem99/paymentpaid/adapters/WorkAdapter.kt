package com.kabirnayeem99.paymentpaid.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.other.Utils
import kotlinx.android.synthetic.main.list_item_work.view.*
import java.util.*

/**
 * The <h1>WorkRecyclerView</h1> needs this [WorkAdapter]
 * to populate the views in each row with the [Work] data.
 */
class WorkAdapter : RecyclerView.Adapter<WorkAdapter.ViewHolder>(), Filterable {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /*
    Callback for calculating the differences between
    the newly created list and the already existed list.
     */
    private var differCallBack: DiffUtil.ItemCallback<Work> = object : DiffUtil.ItemCallback<Work>() {
        override fun areItemsTheSame(oldItem: Work, newItem: Work): Boolean {
            return oldItem.documentId == newItem.documentId
        }

        override fun areContentsTheSame(oldItem: Work, newItem: Work): Boolean {
            return oldItem == newItem
        }

    }

    /*
    Helper for computing the difference between
    two lists via DiffUtil on a background thread.
     */
    var differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkAdapter.ViewHolder {
        return ViewHolder(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.list_item_work, parent, false)
        )
    }

    // creates a null on item click listener
    private var onItemClickListener: ((Work) -> Unit)? = null

    override fun onBindViewHolder(holder: WorkAdapter.ViewHolder, position: Int) {
        val work = differ.currentList[position]

        val workListItemTitle = work.name
        val workListItemDate = "${work.day}-${Utils.padMonth(work.month)}-${work.year}"
        val workListItemStudentName = work.studentName
        val workListItemPayment: String = work.payment.toString()

        holder.itemView.apply {
            tvWorkNameListItemWork.text = workListItemTitle
            tvStudentNameListItemWork.text = workListItemStudentName
            tvPaymentAmountListItemWork.text = workListItemPayment
            tvDateListItemWork.text = workListItemDate

            // pass the onItemClickListener the exact work object that was pressed on
            setOnClickListener { onItemClickListener?.let { it(work) } }

        }
    }


    override fun getItemCount(): Int {
        return differ.currentList.size

    }

    /**
     * Register a callback to be invoked when the work tile is clicked.
     *
     * @param listener The callback that will run
     */
    fun setOnItemClickListener(listener: (Work) -> Unit) {
        onItemClickListener = listener
    }


    override fun getFilter(): Filter {
        return filter
    }

    private var filter: Filter = object : Filter() {
        /*
         Invoked in a worker thread to filter the data according
         to the constraint. Results computed by the filtering
         operation are returned as a Filter
        */
        override fun performFiltering(searchEntry: CharSequence?): FilterResults {
            val filteredWorks: MutableList<Work> = mutableListOf()


            if (searchEntry == null || searchEntry.isEmpty() || searchEntry.length <= 1) {
                filteredWorks.addAll(differ.currentList)
                Log.d(TAG, "performFiltering: $filteredWorks")
            } else {
                val searchEntryPattern = searchEntry.toString().toLowerCase(Locale.getDefault()).trim()
                for (work in differ.currentList) {
                    if (work.name.toLowerCase(Locale.ROOT)
                                    .contains(searchEntryPattern) || work.studentName.toLowerCase(Locale.getDefault())
                                    .contains(searchEntryPattern)
                    ) {
                        filteredWorks.add(work)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredWorks
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            differ.submitList(results?.values as List<Work>)
        }
    }

    companion object {
        private const val TAG = "WorkAdapter"
    }
}