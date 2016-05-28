package com.smashycatdog.multi_threadedapp;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;

/**
 * Created by justinwaite on 5/28/16.
 */
public class WriteThread implements Runnable {

    private Context context;
    private int status;
    private Event event;

    public WriteThread(Context context, Event event) {
        this.context = context;
        this.event = event;
    }

    @Override
    public void run() {
        try {
            File file = new File(context.getFilesDir(), "numbers.txt");

            FileWriter fileWriter = new FileWriter(file);
            for (status = 1; status < 11; status++) {
                fileWriter.write(Integer.toString(status) + "\n");
                event.handleEventCallback(status);
                Thread.sleep(250);
            }
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
