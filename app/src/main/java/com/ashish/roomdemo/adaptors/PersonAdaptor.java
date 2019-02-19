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
    private List<Person> personList;

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
        myViewHolder.name.setText(personList.get(i).getName());
        myViewHolder.email.setText(personList.get(i).getEmail());
        myViewHolder.number.setText(personList.get(i).getNumber());
        myViewHolder.pincode.setText(personList.get(i).getPincode());
        myViewHolder.city.setText(personList.get(i).getCity());
    }

    @Override
    public int getItemCount() {
        if (personList == null) {
            return 0;
        }
        return personList.size();

    }

    public void setTasks(List<Person> personList) {
        if(personList.size()==0){
            this.personList=null;
        }else {
            this.personList=personList;
            notifyDataSetChanged();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, pincode, number, city;
        ImageView editImage, deleteImage;
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
                    int elementId = personList.get(getAdapterPosition()).getId();
                    Intent i = new Intent(context, EditActivity.class);
                    i.putExtra(Constants.UPDATE_Person_Id, elementId);
                    context.startActivity(i);
                }
            });
            deleteImage = itemView.findViewById(R.id.delete_person_image);
            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.personDao().delete(personList.get(getAdapterPosition()));
                        }
                    });
                }
            });
        }
    }
}
