package com.rj.astro.androidRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.rj.astro.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserListDataAdapter extends RecyclerView.Adapter<UserListDataAdapter.ViewHolder> implements Filterable {
    private final Context context;
    private ArrayList<UserData> mArrayList;
    private ArrayList<UserData> mFilteredList;

    public UserListDataAdapter(ArrayList<UserData> arrayList, Context context) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlistrow, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.tv_uname.setText("@"+mFilteredList.get(i).getUsername());
        viewHolder.tv_email.setText(mFilteredList.get(i).getEmail());
        viewHolder.tv_mobile.setText(mFilteredList.get(i).getMobile());
        viewHolder.tv_dob.setText(mFilteredList.get(i).getDob());
        viewHolder.tv_dot.setText(mFilteredList.get(i).getDot());
        viewHolder.tv_birthplace.setText(mFilteredList.get(i).getBirthplace());
        String gender = mFilteredList.get(i).getGender();
        if(gender.equals("Male")){
            Picasso.with(context).load(R.drawable.ml).into(viewHolder.gender);
        }else{
            Picasso.with(context).load(R.drawable.fml).into(viewHolder.gender);
        }

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

                    ArrayList<UserData> filteredList = new ArrayList<>();

                    for (UserData userData : mArrayList) {

                        if (userData.getUsername().toLowerCase().contains(charString) || userData.getUser_id().toLowerCase().contains(charString) || userData.getBirthplace().toLowerCase().contains(charString)) {

                            filteredList.add(userData);
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
                mFilteredList = (ArrayList<UserData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_uname,tv_email,tv_mobile,tv_dob,tv_dot,tv_birthplace;
        ImageView gender;
        public ViewHolder(View view) {
            super(view);

            tv_uname = (TextView)view.findViewById(R.id.tv_u_name);
            tv_email = (TextView)view.findViewById(R.id.tv_u_email);
            tv_mobile = (TextView)view.findViewById(R.id.tv_u_mobile);
            gender = (ImageView) view.findViewById(R.id.imageView8);
            tv_dob = (TextView)view.findViewById(R.id.tv_u_dob);
            tv_dot = (TextView)view.findViewById(R.id.tv_u_dot);
            tv_birthplace = (TextView)view.findViewById(R.id.tv_u_place);


        }
    }

}