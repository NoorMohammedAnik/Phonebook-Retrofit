package com.anik.onlinephonebook.remote;


import com.anik.onlinephonebook.Constant;
import com.anik.onlinephonebook.model.Contacts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("retrofit/POST/readcontacts.php")
    Call<List<Contacts>> getContacts();

    @FormUrlEncoded
    @POST("phonebook/addcontact.php")
    public Call<Contacts> insertUser(
            @Field("name") String name,
            @Field("cell") String cell);


    //for signup
    @FormUrlEncoded
    @POST("phonebook/signup.php")
     Call<Contacts> signUp(
            @Field(Constant.KEY_NAME) String name,
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_PASSWORD) String password);



    //for login
    @FormUrlEncoded
    @POST("phonebook/login.php")
    Call<Contacts> login(
            @Field(Constant.KEY_CELL) String cell,
            @Field(Constant.KEY_PASSWORD) String password);


    @FormUrlEncoded
    @POST("phonebook/editcontact.php")
    public Call<Contacts> editUser(
            @Field("id") String id,
            @Field("name") String name,
            @Field("cell") String cell);


    @FormUrlEncoded
    @POST("phonebook/deletecontact.php")
    Call<Contacts> deleteUser(
            @Field("id") int id
    );




    //for live data search
    @GET("phonebook/getcontacts.php")
    Call<List<Contacts>> getContact(
            @Query("key") String keyword
    );

}