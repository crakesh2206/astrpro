package com.rj.astro.androidRecyclerView;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.rj.astro.R;

import java.util.ArrayList;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> implements Filterable {
    private ArrayList<RequestsAndNotiModel> mArrayList;
    private ArrayList<RequestsAndNotiModel> mFilteredList;

    public RequestsAdapter(ArrayList<RequestsAndNotiModel> arrayList) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row_for_request, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.tv_name.setText("@"+mFilteredList.get(i).getUsername());
        viewHolder.tv_name.setTypeface(null, Typeface.BOLD);
        viewHolder.tv_version.setText(mFilteredList.get(i).getQuestion());

    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<RequestsAndNotiModel> filteredList = new ArrayList<>();

                    for (RequestsAndNotiModel requestsAndNotiModel : mArrayList) {

                        if (requestsAndNotiModel.getUsername().toLowerCase().contains(charString) || requestsAndNotiModel.getQuestion().toLowerCase().contains(charString) || requestsAndNotiModel.getUser_id().toLowerCase().contains(charString)) {

                            filteredList.add(requestsAndNotiModel);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<RequestsAndNotiModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_version;
        public ViewHolder(View view) {
            super(view);

            tv_name = (TextView)view.findViewById(R.id.tv_name);
            tv_version = (TextView)view.findViewById(R.id.tv_version);


        }
    }

}