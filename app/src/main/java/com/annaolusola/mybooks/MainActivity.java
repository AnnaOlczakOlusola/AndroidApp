package com.annaolusola.mybooks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        resultTextView = findViewById(R.id.resultTextView);
    }

    public void getBook(View view) {
        try {
            DownloadTask task = new DownloadTask();
            String encodedBookTitle = editText.getText().toString();

            task.execute("http://booksdemoapplication.azurewebsites.net/api/book/" + encodedBookTitle);

            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),R.string.Error_message,Toast.LENGTH_SHORT).show();
        }
    }

    public class DownloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            String message = "";
            try {
               JSONObject jsonObject = new JSONObject(s);

                    String author = jsonObject.getString("author");
                    String publisher = jsonObject.getString("publisher");
                    String storeUrl = jsonObject.getString("storeUrl");
                    double price = jsonObject.getDouble("price");



                    if (!author.equals("") && !publisher.equals("") && !storeUrl.equals("")) {
                        message += getApplicationContext().getString(R.string.author) + " " + author + "\n" + getApplicationContext().getString(R.string.publisher) + " " + publisher + "\n" +
                                getApplicationContext().getString(R.string.storeUrl) + " " + storeUrl + "\n" + getApplicationContext().getString(R.string.price) + price + "\r\n";
                    }
                } catch (JSONException e1) {
                e1.printStackTrace();
                Toast.makeText(getApplicationContext(),R.string.Error_message,Toast.LENGTH_SHORT).show();
            } catch (Exception e) {

                Toast.makeText(getApplicationContext(),R.string.Error_message,Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }


            if (!message.equals("")) {
                    resultTextView.setText(message);
                } else {
                    Toast.makeText(getApplicationContext(),R.string.Error_message,Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

