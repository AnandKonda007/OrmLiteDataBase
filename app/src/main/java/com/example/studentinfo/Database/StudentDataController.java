package com.example.studentinfo.Database;

import android.content.Context;
import android.util.Log;

import com.example.studentinfo.Model.StudentInfo;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDataController extends OrmLiteBaseActivity {
    public DataBaseHelper helper;
    public ArrayList<StudentInfo> allUsers = new ArrayList<>();
    public  StudentInfo currentUser;
    public static StudentDataController controller;

    public static  StudentDataController getInstance() {
        if (controller == null) {
            controller = new StudentDataController();
        }
        return controller;
    }


    public void  fillContext(Context context1)
    {

        Log.e("DBStatus","Fill Context Called");
        helper = new DataBaseHelper(context1);
    }


    //insert the userdata into user table
    public boolean insertUserData(StudentInfo userdata) {
        try {
            helper.getUserDao().create(userdata);
            fetchUserData();
            Log.e("fetch", ""+allUsers);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Fetching all the user data
    public ArrayList<StudentInfo> fetchUserData() {
        allUsers = null;
        allUsers = new ArrayList<>();

        try {
            allUsers = (ArrayList) helper.getUserDao ().queryForAll ();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.e("fetching", "user data fetched successfully"+allUsers.size());

        return allUsers;
    }



    //updating the userdata
    public void updateUserData( StudentInfo user) {
        try {
            UpdateBuilder<StudentInfo, Integer> updateBuilder = helper.getUserDao().updateBuilder();
            updateBuilder.updateColumnValue ( "username",user.getUsername ());
            updateBuilder.updateColumnValue("password", user.getPassword ());
            updateBuilder.updateColumnValue("dob", user.getDob ());
            updateBuilder.updateColumnValue("phoneno", user.getPhoneno ());
            updateBuilder.updateColumnValue("age", user.getAge ());
            updateBuilder.updateColumnValue("gender",user.getGender());
            updateBuilder.where().eq("email", user.getEmail ());

            updateBuilder.update();
            Log.e("update data", "updated the data successfully");
            Log.e("new user id", "" + user.getUsername () + "" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Deleting all users in database
    public void deleteUserData(ArrayList<StudentInfo> user) {
        try {
            helper.getUserDao().delete(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Deleting all users in database
    public void deleteSingleUserData(StudentInfo user) {
        try {
            helper.getUserDao().delete(user);
            Log.e("delete data", "delete the data successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public StudentInfo getUserObjectForEmail(String email){

        if(allUsers.size() > 0 ) {
            for (int l = 0; l < allUsers.size(); l++) {
                StudentInfo obUser = allUsers.get(l);
                if (obUser.getUsername().equals(email)) {
                    return obUser;
                }
            }
        }
        return  null;
    }
}

