package com.kabirnayeem99.paymentpaid.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kabirnayeem99.paymentpaid.R
import kotlinx.android.synthetic.main.list_item_settings.view.*

class ProfileSettingsItem(val id: Int, val name: String, val value: String)

class ProfileSettingsAdapter : RecyclerView.Adapter<ProfileSettingsAdapter.ViewHolder>() {

    /*
     This call back informs about the changes
    */
    private var differCallBack: DiffUtil.ItemCallback<ProfileSettingsItem> =
            object : DiffUtil.ItemCallback<ProfileSettingsItem>() {
                override fun areItemsTheSame(oi: ProfileSettingsItem, ni: ProfileSettingsItem) = oi.id == ni.id

                override fun areContentsTheSame(oi: ProfileSettingsItem, ni: ProfileSettingsItem) = oi == ni
            }

    /*
    The AsyncListDiffer can consume the values from a LiveData of
    List and present the data simply for an adapter.
    It computes differences in list contents via DiffUtil on a background thread
    as new List are received.
   */
    var differ = AsyncListDiffer(this, differCallBack)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_settings, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(differ.currentList[position]) {
                itemView.tvSettingsName.text = name
                itemView.tvSettingsValue.text = value
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}