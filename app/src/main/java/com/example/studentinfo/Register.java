package com.example.studentinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
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
    RadioButton genderMale,genderFemale;
    Button submit;
    boolean passwordVisible;
    boolean isAllFieldsChecked = false;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String gender= "Male";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        actions();
        passwordVisibleActions();
    }

    private void passwordVisibleActions() {

        password.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password.getSelectionEnd();
                        if (passwordVisible) {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility_off, 0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;

                        } else {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility, 0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;

                        }
                        password.setSelection(selection);
                        return true;

                    }

                }
                return false;
            }
        });
    }

    private void actions() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        Dob = findViewById(R.id.Dob);
        dobActions();
        age = findViewById(R.id.age);
        phoneno = findViewById(R.id.phonenumber);
        genderActions();

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
            if(info.getGender().equals("Male")){
                genderMale.setChecked(true);
                genderFemale.setChecked(false);
            }else{
                genderFemale.setChecked(true);
                genderMale.setChecked(false);
            }

            // genderButton.setChecked(true);


        }

        registerActions();


    }
    private void genderActions() {
        genderGroup = findViewById(R.id.gender);
        genderMale=findViewById(R.id.Male);
        genderFemale=findViewById(R.id.Female);
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.Male) {
                    gender="Male";
                } else if (checkedId == R.id.Female) {
                    gender="Female";
                }
            }
        });

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
                    studentInfo.setDob(Dob.getText().toString());
                    studentInfo.setPassword(password.getText().toString());
                    studentInfo.setGender(gender);

                    if (StudentDataController.getInstance().currentUser != null) {
                        StudentDataController.getInstance().currentUser = studentInfo;
                        StudentDataController.getInstance().updateUserData(StudentDataController.getInstance().currentUser);
                        Toast.makeText(getApplicationContext(), "update data successfully", Toast.LENGTH_SHORT).show();
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
        }
        else if (phoneno.length() == 0) {
            phoneno.setError("phoneno is required");
            return false;
        }
        return true;
    }

}