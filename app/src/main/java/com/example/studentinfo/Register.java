package com.example.studentinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.studentinfo.Database.StudentDataController;
import com.example.studentinfo.Model.StudentInfo;

import java.util.Calendar;

public class Register extends AppCompatActivity {
    EditText email, password, username, Dob, age, phoneno;
    RadioGroup genderGroup;
    RadioButton genderButton;

    Button submit;
    boolean isAllFieldsChecked = false;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        actions();
    }

    private void actions() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        Dob = findViewById(R.id.Dob);
        dobActions();
        age = findViewById(R.id.age);
        phoneno = findViewById(R.id.phonenumber);
        genderGroup = findViewById(R.id.gender);

        int selectedId = genderGroup.getCheckedRadioButtonId();
        genderButton = findViewById(selectedId);

        submit = findViewById(R.id.submit);


        if (StudentDataController.getInstance().currentUser != null) {
            StudentInfo info = StudentDataController.getInstance().currentUser;
            email.setEnabled(false);
            email.setText(info.getEmail());
            username.setText(info.getUsername());
            age.setText(info.getAge());
            password.setText(info.getPassword());
            Dob.setText(info.getDob());
            phoneno.setText(info.getPhoneno());
           // genderButton.setChecked(true);


        }

        registerActions();


    }

    private void dobActions() {
        Dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void registerActions() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    StudentInfo studentInfo = new StudentInfo();
                    studentInfo.setEmail(email.getText().toString());
                    studentInfo.setUsername(username.getText().toString());
                    studentInfo.setPhoneno(phoneno.getText().toString());
                    studentInfo.setAge(age.getText().toString());

                    int selectedId = genderGroup.getCheckedRadioButtonId();
                    genderButton = findViewById(selectedId);

                    studentInfo.setGender(genderButton.getText().toString());


                    studentInfo.setPassword(password.getText().toString());
                    if (StudentDataController.getInstance().currentUser != null) {
                        StudentDataController.getInstance().currentUser = studentInfo;
                        StudentDataController.getInstance().updateUserData(StudentDataController.getInstance().currentUser);
                        Toast.makeText(getApplicationContext(), "update data successfullyu", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, Home.class);
                        startActivity(intent);
                    } else {
                        if (StudentDataController.getInstance().insertUserData(studentInfo)) {
                            Intent intent = new Intent(Register.this, Home.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "not storing the data", Toast.LENGTH_SHORT).show();
                        }
                    }


                } else {
                    Toast.makeText(Register.this, "Please enter Required Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private boolean CheckAllFields() {
        if (email.length() == 0 || !email.getText().toString().trim().matches(emailPattern)) {
            email.setError("Please Enter validate email");
            return false;
        }

        if (password.length() == 0) {
            password.setError("This field is required");
            return false;
        }

        if (username.length() == 0) {
            username.setError("username is required");
            return false;
        }

        if (Dob.length() == 0) {
            Dob.setError("Date of birth is required");
            return false;
        }
        if (age.length() == 0) {
            age.setError("country is required");
            return false;
        } else if (phoneno.length() == 0) {
            phoneno.setError("phoneno is required");
            return false;
        }
        return true;
    }

}