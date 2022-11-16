package com.example.studentinfo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studentinfo.Database.StudentDataController;
import com.example.studentinfo.Home;
import com.example.studentinfo.Model.StudentInfo;
import com.example.studentinfo.R;
import com.example.studentinfo.Register;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.studentInfo> {
    Context context;

    public class studentInfo extends RecyclerView.ViewHolder {
        TextView username, email, phonenumber, age;
        Button edit,delete;

        public studentInfo(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username2);
            email = itemView.findViewById(R.id.email2);
            phonenumber = itemView.findViewById(R.id.phonenumber2);
            age = itemView.findViewById(R.id.age2);
            edit=itemView.findViewById(R.id.edit);
            delete=itemView.findViewById(R.id.delete);

        }
    }

    public StudentAdapter(Context context) {
        this.context = context;
    }


    @Override
    public studentInfo onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.studentinfo,parent,false);
        return new studentInfo(itemview);

    }

    @Override
    public void onBindViewHolder(studentInfo holder, int position) {
        final StudentInfo student =StudentDataController.getInstance().allUsers.get(position);
        holder.username.setText(student.getUsername());
        holder.phonenumber.setText(student.getPhoneno());
        holder.age.setText(student.getAge());
        holder.email.setText(student.getEmail());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentDataController.getInstance().currentUser=null;
                StudentDataController.getInstance().currentUser=StudentDataController.getInstance().allUsers.get(holder.getAdapterPosition());
                context.startActivity(new Intent(context, Register.class));
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentDataController.getInstance().currentUser=null;
                StudentDataController.getInstance().currentUser=StudentDataController.getInstance().allUsers.get(holder.getAdapterPosition());
                StudentDataController.getInstance().deleteSingleUserData( StudentDataController.getInstance().currentUser);
                StudentDataController.getInstance().fetchUserData();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(StudentDataController.getInstance().allUsers.size()>0){
            return StudentDataController.getInstance().allUsers.size();
        }else{
            return 0;
        }
    }

}
