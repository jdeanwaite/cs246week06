package com.smashycatdog.multi_threadedapp;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * Created by justinwaite on 5/28/16.
 */
public class FileReadTask extends AsyncTask<String, Integer, Boolean>
{
    private Context context;
    private Event event;
    private List<String> numbers;

    public FileReadTask(Context context, Event event, List<String> numbers) {
        this.context = context;
        this.event = event;
        this.numbers = numbers;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        File file = new File(context.getFilesDir(), params[0]);
        if (file.exists() && file.canRead()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    numbers.add(line);
                    publishProgress(numbers.size());
                    Thread.sleep(250);
                }
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        event.handleEventCallback(values);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        event.handleEventCallback(numbers.size());
    }
}
