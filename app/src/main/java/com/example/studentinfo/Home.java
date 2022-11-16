package com.example.studentinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.studentinfo.Adapter.StudentAdapter;
import com.example.studentinfo.Database.StudentDataController;
import com.example.studentinfo.Model.StudentInfo;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    public StudentAdapter studentAdapter;
   // List<StudentInfo> studentInfoDB = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recycler_view);
        StudentDataController.getInstance().fetchUserData();
        studentAdapter = new StudentAdapter( this);
        recyclerView.setAdapter(studentAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(Home.this));
        studentAdapter.notifyDataSetChanged();

    }
}