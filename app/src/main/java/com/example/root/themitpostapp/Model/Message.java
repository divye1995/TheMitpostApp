package com.example.root.themitpostapp.Model;

import java.io.Serializable;

/**
 * Created by root on 31/3/16.
 */
public class Message implements Serializable {

    String message;
    public Message(){}

    public Message(String message){
        this.message=message;
    }
    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message=message;
    }
}
