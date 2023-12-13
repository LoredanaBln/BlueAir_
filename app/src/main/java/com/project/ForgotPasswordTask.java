package com.project;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ForgotPasswordTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String email = params[0];
        String serverUrl = "http://your_server_url/forgot-password";

        try {
            URL url = new URL(serverUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(("email=" + URLEncoder.encode(email, "UTF-8")).getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }

                bufferedReader.close();
                return response.toString();
            } else {
                return "Error: " + responseCode;
            }
        } catch (IOException e) {
            Log.e("ForgotPasswordTask", "Error: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // Handle the result, update UI, show a toast, etc.
        Log.d("ForgotPasswordTask", "Result: " + result);
        // You can display a Toast or update UI based on the result
    }
}
