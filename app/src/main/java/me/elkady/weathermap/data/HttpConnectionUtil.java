package me.elkady.weathermap.data;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by mak on 6/17/17.
 */

public class HttpConnectionUtil {
    interface OnDataReceived {
        void onDataReceived(String data);
        void onError(Exception e);
    }

    public static void processRequest(final URL url, final OnDataReceived onDataReceived) {
        final AsyncTask<URL, Void, Result> task = new AsyncTask<URL, Void, Result>() {
            @Override
            protected Result doInBackground(URL... params) {
                InputStream stream = null;
                HttpURLConnection connection = null;
                Result result = new Result();

                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode != HttpsURLConnection.HTTP_OK) {
                        throw new IOException("HTTP error code: " + responseCode);
                    }
                    stream = connection.getInputStream();
                    Scanner s = new Scanner(stream);
                    StringBuilder sb = new StringBuilder();
                    while (s.hasNextLine()) {
                        sb.append(s.nextLine()).append("\n");
                    }
                    result.result = sb.toString();
                } catch (IOException e) {
                    result.exception = e;
                } finally {
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (IOException e) { }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }

                return result;
            }

            @Override
            protected void onPostExecute(Result result) {
                if(result.exception != null) {
                    onDataReceived.onError(result.exception);
                } else {
                    onDataReceived.onDataReceived(result.result);
                }
            }
        };

        task.execute(url);
    }

    private static class Result {
        String result;
        Exception exception;
    }
}
