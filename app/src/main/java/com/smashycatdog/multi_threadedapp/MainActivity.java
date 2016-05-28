package com.smashycatdog.multi_threadedapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> arrayAdapter;
    private List<String> numberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        numberList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.simple_list_view, numberList);

        final ListView listView = (ListView) findViewById(R.id.coolListView);
        if (listView != null) {
            listView.setAdapter(arrayAdapter);
        }
    }

    public void createList(View view) {
        System.out.println("Creating list");
        Event event = new Event() {
            @Override
            public void handleEventCallback(Object... objects) {
                if (objects[0] instanceof Integer) {
                    updateProgressBar((Integer) objects[0]);
                }
            }
        };
        WriteThread writeThread = new WriteThread(this, event);
        Thread thread = new Thread(writeThread);
        thread.start();
    }

    public void updateProgressBar(int progress) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (progressBar != null) {
            Double percent = progress / 10d;
            progressBar.setProgress((int) (percent * 100));
        }
    }

    public void loadList(View v) {
        updateProgressBar(0);
        Event event = new Event() {
            @Override
            public void handleEventCallback(Object... o) {
                if (o[0] instanceof Integer) {
                    updateProgressBar((Integer) o[0]);
                }
                arrayAdapter.notifyDataSetChanged();
            }
        };
        FileReadTask readTask = new FileReadTask(this, event, numberList);
        readTask.execute("numbers.txt");
    }

    public void clearList(View v) {
        updateProgressBar(0);
        numberList.clear();
        arrayAdapter.notifyDataSetChanged();
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
