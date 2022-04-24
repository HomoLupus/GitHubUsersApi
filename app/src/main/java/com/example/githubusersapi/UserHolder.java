package com.example.githubusersapi;

import java.util.ArrayList;
import java.util.List;

public class UserHolder {

    private static volatile UserHolder instance = null;
    private ArrayList<User> userList;

    private UserHolder(ArrayList<User> data){
        this.userList = data;

    }


    public static synchronized UserHolder getInstance(ArrayList<User> userList){
        if(instance == null){
            instance = new UserHolder(userList);
            return instance;
        }else{
            return instance;
        }
    }

    public ArrayList<User> getUserList() {
        return userList;
    }
}


