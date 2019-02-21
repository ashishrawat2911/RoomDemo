package com.ashish.roomdemo.adaptors;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.roomdemo.R;
import com.ashish.roomdemo.activities.EditActivity;
import com.ashish.roomdemo.constants.Constants;
import com.ashish.roomdemo.database.AppDatabase;
import com.ashish.roomdemo.database.AppExecutors;
import com.ashish.roomdemo.model.Person;

import java.util.List;

public class PersonAdaptor extends RecyclerView.Adapter<PersonAdaptor.MyViewHolder> {
    private Context context;
    private List<Person> mPersonList;

    public PersonAdaptor(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.person_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdaptor.MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(mPersonList.get(i).getName());
        myViewHolder.email.setText(mPersonList.get(i).getEmail());
        myViewHolder.number.setText(mPersonList.get(i).getNumber());
        myViewHolder.pincode.setText(mPersonList.get(i).getPincode());
        myViewHolder.city.setText(mPersonList.get(i).getCity());
    }

    @Override
    public int getItemCount() {
        if (mPersonList == null) {
            return 0;
        }
        return mPersonList.size();

    }

    public void setTasks(List<Person> personList) {
        mPersonList = personList;
        notifyDataSetChanged();
    }

    public List<Person> getTasks() {

        return mPersonList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, pincode, number, city;
        ImageView editImage;
        AppDatabase mDb;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mDb = AppDatabase.getInstance(context);
            name = itemView.findViewById(R.id.person_name);
            email = itemView.findViewById(R.id.person_email);
            pincode = itemView.findViewById(R.id.person_pincode);
            number = itemView.findViewById(R.id.person_number);
            city = itemView.findViewById(R.id.person_city);
            editImage = itemView.findViewById(R.id.edit_Image);
            editImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int elementId = mPersonList.get(getAdapterPosition()).getId();
                    Intent i = new Intent(context, EditActivity.class);
                    i.putExtra(Constants.UPDATE_Person_Id, elementId);
                    context.startActivity(i);
                }
            });
        }
    }
}
