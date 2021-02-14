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
import com.kabirnayeem99.paymentpaid.utils.CustomUtils
import kotlinx.android.synthetic.main.list_item_payment.view.*
import java.util.*

class PaymentAdapter : RecyclerView.Adapter<PaymentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var diffCallBack = object : DiffUtil.ItemCallback<Int>() {


        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_payment,
                parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (differ.currentList.isNotEmpty()) {
            holder.itemView.apply {
                tvPaymentMonthListItemPayment.text = CustomUtils.getCurrentMonthName(position)
                tvPaymentAmountListItemPayment.text = differ.currentList[position].toString()
            }
            Log.d(TAG, "onBindViewHolder: ${differ.currentList.toString()}")
        }
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    companion object {
        private const val TAG = "PaymentAdapter"
    }
}