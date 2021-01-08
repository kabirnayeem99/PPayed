package com.kabirnayeem99.paymentpaid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.models.WorkModel;
import com.kabirnayeem99.paymentpaid.utils.Utils;

import java.util.List;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {
    private final List<WorkModel> workList;

    public WorkAdapter(List<WorkModel> workList) {
        this.workList = workList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // when there is not view holder, it creates a new view holder,
        // which later will be updated from the bound data.

        // Here the layout inflater takes the work list item layout file
        // and turns it into a view
        // later these views are used to draw on screen
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_list_item,
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
        String workListItemPayment = String.format("%s", workList.get(position).getPayment());

        // binds the data for each of the work got from the db to the existing adapter based on the
        // screen time of the lists item.
        holder.workListItemTitle.setText(workListItemTitle);
        holder.workListItemDate.setText(workListItemDate);
        holder.workListItemPayment.setText(Utils.formatNumber(workListItemPayment));
    }

    @Override
    public int getItemCount() {

        // the size of the work list
        return workList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView workListItemTitle, workListItemDate, workListItemPayment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            workListItemTitle = itemView.findViewById(R.id.workListItemTitle);
            workListItemDate = itemView.findViewById(R.id.workListItemDate);
            workListItemPayment = itemView.findViewById(R.id.workListItemPayment);
        }
    }
}
