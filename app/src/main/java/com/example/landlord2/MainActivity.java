package com.example.landlord2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String url = "https://learn.operatoroverload.com/rental/";

    OkHttpClient client = new OkHttpClient();

    List<String> spnr2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String item = (String) spinner.getItemAtPosition(spinner.getSelectedItemPosition());
//                Toast.makeText(getApplicationContext(),item,Toast.LENGTH_LONG).show();

                Request request = new Request.Builder()
                        .url(url+item)
                        .build();

                client.newCall(request).enqueue(new Callback() {

                    TextView textView = findViewById(R.id.textView);
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                        Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("Error in failure is ->", "error"+e.getMessage());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                        if(!response.isSuccessful()){

                            Log.d("response code ->", ""+response.code());

                        }
//                        Log.d("RESPONSE -> ",response.body().string());
                        final String res = response.body().string();


                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Handle UI here
                                if(res.matches("Cannot Get")){
                                    textView.setText("Page not found");
                                }else{
                                    textView.setText(res);
                                }

//                                for(int i =0 ;i<res.length();i++){
//                                    spnr2.add(res);
//                                }
                            }
                        });
//                        textView.setText(response.body().string());

                        Request request2 = new Request.Builder()
                                .url(url+item)
                                .build();

                        client.newCall(request2).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                            }
                        });
                    }
                });



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        spinner2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//            }
//        });


// Create an ArrayAdapter using the string array and a default spinner layout


//        adapter for spinner1
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


//        adapter for spinner2
        ArrayAdapter<String> arradptr = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spnr2);
        arradptr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arradptr);

    }
}
