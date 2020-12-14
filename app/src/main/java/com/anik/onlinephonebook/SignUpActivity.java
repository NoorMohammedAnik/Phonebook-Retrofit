package com.anik.onlinephonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class SignUpActivity extends AppCompatActivity {



    EditText etxtName, etxtCell, etxtPassword;
    Button btnSignUp;
    private ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etxtName = findViewById(R.id.etxt_name);
        etxtCell = findViewById(R.id.etxt_cell);
        etxtPassword = findViewById(R.id.etxt_password);

        btnSignUp = findViewById(R.id.btn_signup);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //taking values
                String name = etxtName.getText().toString();
                String cell = etxtCell.getText().toString();
                String password = etxtPassword.getText().toString();
                //validation
                if (name.isEmpty())
                {
                    etxtName.setError("Name can not be empty! ");
                    etxtName.requestFocus();

                }

                else if (cell.length()!=11)
                {
                    etxtCell.setError("Invalid cell!");
                    etxtCell.requestFocus();

                }


                else if (password.isEmpty())
                {
                    etxtPassword.setError("Password can not be empty! ");
                    etxtPassword.requestFocus();

                }

                else
                {
                    //call signup method
                    sign_up(name,cell,password);
                }

            }
        });


    }


    //signup method
    private void sign_up(String name,String cell,String password) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Contacts> call = apiInterface.signUp(name, cell, password);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);

                }

                else if (value.equals("exists"))
                {
                    loading.dismiss();
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();


                }


                else {
                    loading.dismiss();
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {

                loading.dismiss();
                Toast.makeText(SignUpActivity.this, "Error! " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
