package com.anik.onlinephonebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.anik.onlinephonebook.model.Contacts;
import com.anik.onlinephonebook.remote.ApiClient;
import com.anik.onlinephonebook.remote.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter adapter;
    private List<Contacts> contactsList;
    private ApiInterface apiInterface;

    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        fetchData("");

    }

    public void fetchData(String key){
        Call<List<Contacts>> call = apiInterface.getContact(key);
        call.enqueue(new Callback<List<Contacts>>() {
            @Override
            public void onResponse(Call<List<Contacts>> call, Response<List<Contacts>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                contactsList = response.body();
                adapter = new Adapter(MainActivity.this, contactsList);
                recyclerView.setAdapter(adapter);


                adapter.notifyDataSetChanged();//for search
            }

            @Override
            public void onFailure(Call<List<Contacts>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    //for menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchData( query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchData( newText);
                return false;
            }
        });
        return true;
    }



    //menu item selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add:
               startActivity(new Intent(this, EditorActivity.class));
                return true;

            case R.id.logout:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //when activity is resumed this method is called
    @Override
    protected void onResume() {
        super.onResume();
        fetchData( "");
    }
}