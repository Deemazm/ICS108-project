package com.example.connect2;
import javafx.scene.image.Image;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;


public class User implements Comparable<User>{
    private final String userName;
    private String status = "No current status";
    private String imagePath = "noImage.png";
    public TreeSet<String> setOfFriends = new TreeSet<>();


    public User(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return status;
    }
    public void setUserImage(String imagePath){

        this.imagePath = imagePath;
    }

    public Image getUserImage(){
        if (imagePath != null) {
            return new Image(this.imagePath);
        }
        return null;
    }


    // we override toString as a (csv) file in order to extract the user information easily
    @Override
    public String toString(){
        StringBuilder friendsAsString = new StringBuilder();

        if(!setOfFriends.isEmpty()) {
            for (String friend : setOfFriends) {
                friendsAsString.append(friend).append(",");
            }
        }
        return  userName + "," + status + ","+ imagePath + "," + friendsAsString;
    }



    @Override
    public int compareTo(User user2) {
        return this.userName.compareTo(user2.userName);
    }



}