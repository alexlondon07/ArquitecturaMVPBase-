package io.github.alexlondon07.arquitecturamvpbase.model;

import android.location.*;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by alexlondon07 on 10/3/17.
 */

public class Customer implements Serializable{

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("surname")
    @Expose
    private String surname;

    @SerializedName("phoneList")
    @Expose
    private ArrayList<PhoneList> phoneList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ArrayList<PhoneList> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(ArrayList<PhoneList> phoneList) {
        this.phoneList = phoneList;
    }
}
