package com.kabirnayeem99.paymentpaid.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import kotlinx.android.synthetic.main.list_item_work.view.*

class WorkAdapter : RecyclerView.Adapter<WorkAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var differCallBack: DiffUtil.ItemCallback<Work> = object : DiffUtil.ItemCallback<Work>() {
        override fun areItemsTheSame(oldItem: Work, newItem: Work): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Work, newItem: Work): Boolean {
            return oldItem == newItem
        }

    }
    var differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkAdapter.ViewHolder {
        return ViewHolder(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.list_item_work, parent, false)
        )
    }

    private var onItemClickListener: ((Work) -> Unit)? = null

    override fun onBindViewHolder(holder: WorkAdapter.ViewHolder, position: Int) {
        val work = differ.currentList[position]

        val workListItemTitle = work.name
        val workListItemDate = "${work.date}-${work.month}-${work.year}"
        Log.d(TAG, "onBindViewHolder: $workListItemDate")
        val workListItemStudentName = work.studentName
        val workListItemPayment: String = work.payment

        holder.itemView.apply {
            tvWorkNameListItemWork.text = workListItemTitle
            tvStudentNameListItemWork.text = workListItemStudentName
            tvPaymentAmountListItemWork.text = workListItemPayment
            tvDateListItemWork.text = workListItemDate

            setOnClickListener { onItemClickListener?.let { it(work) } }

        }
    }


    /**
     * This method returns the size of the work list
     * @return int
     */
    override fun getItemCount(): Int {
        return differ.currentList.size

    }

    fun setOnItemClickListener(listener: (Work) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        private const val TAG = "WorkAdapter"
    }
}