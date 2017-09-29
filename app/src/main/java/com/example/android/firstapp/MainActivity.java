package com.example.android.firstapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import android.os.StrictMode;

public class MainActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context=this;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = (Button) findViewById(R.id.mybutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TextView view = (TextView) findViewById(R.id.hahahehe);
                FoodTruck[] test = TestQueries.findMyFoodTrucks("Los Angeles, CA", 0, 20);
                //view.setText(test[0].getName());

                /*ArrayAdapter<FoodTruck> adapter = new ArrayAdapter<FoodTruck>(
                        context,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1, test);

                ListView lv = (ListView) findViewById(R.id.mylistview);
                lv.setAdapter(adapter);*/

                // Create the adapter to convert the array to views
                FoodTruckAdapter adapter = new FoodTruckAdapter(context, test);
                // Attach the adapter to a ListView
                ListView listView = (ListView) findViewById(R.id.mylistview);
                listView.setAdapter(adapter);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


