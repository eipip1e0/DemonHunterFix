package com.facebook.stream;

import android.app.Activity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileIO {
    public static void write(Activity activity, String data, String fileName) throws IOException {
        BufferedWriter bf = new BufferedWriter(new FileWriter(activity.openFileOutput(fileName, 0).getFD()));
        bf.write(data);
        bf.flush();
        bf.close();
    }

    public static String read(Activity activity, String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(activity.openFileInput(fileName)));
        StringBuilder sb = new StringBuilder();
        while (br.ready()) {
            sb.append(br.readLine());
        }
        return sb.toString();
    }
}
