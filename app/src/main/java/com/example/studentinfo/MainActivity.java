package com.example.studentinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.studentinfo.Database.StudentDataController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StudentDataController.getInstance().fillContext(getApplicationContext());
        StudentDataController.getInstance().fetchUserData();
        actions();
    }

    private void actions() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(StudentDataController.getInstance().allUsers.size()>0) {
                    startActivity(new Intent(MainActivity.this, Home.class));
                }else{
                    startActivity(new Intent(MainActivity.this, Register.class));
                }
            }
        },1000);
    }
}