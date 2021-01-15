package com.kabirnayeem99.paymentpaid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.data.db.entities.Work;
import com.kabirnayeem99.paymentpaid.utils.CustomUtils;

import java.util.ArrayList;
import java.util.List;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {
    private List<Work> workList = new ArrayList<>();


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // when there is not view holder, it creates a new view holder,
        // which later will be updated from the bound data.

        // Here the layout inflater takes the work list item layout file
        // and turns it into a view
        // later these views are used to draw on screen
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_work,
                parent, false);

        // a new view holder is created based on the view of work list item.
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // binds the new data to the view holder without creating new view holder to reduce cpu
        // as well as the memory usage.

        // gets the data from the database, i.e. work title, submission date and payment amount.
        String workListItemTitle = workList.get(position).getName();
        String workListItemDate = workList.get(position).getDate();
        String workListItemStudentName = workList.get(position).getStudentName();
        String workListItemPayment = String.format("%s", workList.get(position).getPayment());

        // binds the data for each of the work got from the db to the existing adapter based on the
        // screen time of the lists item.
        holder.tvWorkName.setText(workListItemTitle);
        holder.tvDate.setText(workListItemDate);
        holder.tvStudentName.setText(workListItemStudentName);
        holder.tvPayment.setText(CustomUtils.formatNumber(workListItemPayment));
    }

    @Override
    public int getItemCount() {

        // the size of the work list
        return workList.size();
    }

    public void setWorkList(List<Work> allWorks) {
        this.workList = allWorks;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvWorkName, tvDate, tvPayment, tvStudentName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWorkName = itemView.findViewById(R.id.tv_work_name_list_item_work);
            tvDate = itemView.findViewById(R.id.tv_date_list_item_work);
            tvPayment = itemView.findViewById(R.id.tv_payment_amount_list_item_work);
            tvStudentName = itemView.findViewById(R.id.tv_student_name_list_item_work);
        }
    }
}
