package com.anik.onlinephonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anik.onlinephonebook.model.Contacts;
import com.anik.onlinephonebook.remote.ApiClient;
import com.anik.onlinephonebook.remote.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorActivity extends AppCompatActivity {

    private ApiInterface apiInterface;

    private EditText etxtName, etxtCell;
    private Button btnSave, btnDelete;

    String getID;
    String getName;
    String getCell;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //received data
        Intent i = getIntent();
        getID = i.getStringExtra("id");
        getName = i.getStringExtra("name");
        getCell = i.getStringExtra("cell");

        etxtName = findViewById(R.id.et_name);
        etxtCell = findViewById(R.id.et_cell);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);


        condition(getID);


    }

    //for insert data
    private void insertUser() {

        String name = etxtName.getText().toString();
        String cell = etxtCell.getText().toString();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Contacts> call = apiInterface.insertUser(name, cell);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {
                String value = response.body().getValue();
                String message = response.body().getMassage();
                if (value.equals("success")) {
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {
                Toast.makeText(EditorActivity.this, "Error! " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void editUser() {

        String sid = getID;
        String sname = etxtName.getText().toString();
        String scell = etxtCell.getText().toString();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Contacts> call = apiInterface.editUser(sid, sname, scell);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {
                String value = response.body().getValue();
                String message = response.body().getMassage();
                if (value.equals("success")) {
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {
                Toast.makeText(EditorActivity.this, "Error! " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //check for id received or not
    private void condition(String id) {

        if (id == null) {

            btnDelete.setVisibility(View.INVISIBLE);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertUser();
                }
            });

        } else {

            etxtName.setText(getName);
            etxtCell.setText(getCell);

            Log.d("ID", getID);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editUser();
                }
            });


            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteUser();
                }
            });
        }
    }


    //for delete data
    private void deleteUser() {

        int id = Integer.parseInt(getID);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Contacts> call = apiInterface.deleteUser(id);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {
                String value = response.body().getValue();
                String message = response.body().getMassage();
                if (value.equals("success")) {
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {
                Toast.makeText(EditorActivity.this, "Error! " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}