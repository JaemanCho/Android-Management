package com.example.management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView idText = (TextView) findViewById(R.id.idText);
        TextView passwordText = (TextView) findViewById(R.id.passwordText);
        TextView welcomeMassage = (TextView) findViewById(R.id.welcomeMessage);
        Button managementButton = (Button) findViewById(R.id.managementButton);
        Intent intent = getIntent();

        String userID = intent.getStringExtra("userID");
        String userPassword = intent.getStringExtra("userPassword");
        String message = "Welcome! " + userID;

        idText.setText(userID);
        passwordText.setText(userPassword);
        welcomeMassage.setText(message);

        if(!userID.equals("admin")) {
            managementButton.setVisibility(View.GONE);
        }

        managementButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });
    }

    // AsyncTask<Params, Progress, Result>
    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;

        // 初期化
        @Override
        protected void onPreExecute() {
            target = "http://10.0.2.2/List.php";
        }

        // 実行
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);

                //connectionのインスタンス
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                //リクエストのメソッドを指定
                httpURLConnection.setRequestMethod("GET");

                //通信開始
                httpURLConnection.connect();

                //　レスポンスコードを戻る
                int responseCode = httpURLConnection.getResponseCode();

                // レスポンスコードを判断する、OKであれば、進める
                if(responseCode == HttpURLConnection.HTTP_OK){

                    // 通信に成功した
                    // テキストを取得する
                    InputStream inputStream= httpURLConnection.getInputStream();

                    String encoding = httpURLConnection.getContentEncoding();

                    if(null == encoding){
                        encoding = "UTF-8";
                    }

                    StringBuffer stringBuilder = new StringBuffer();

                    final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, encoding);
                    final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String line = null;

                    // 1行ずつテキストを読み込む
                    while((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    // 　クローズ
                    bufferedReader.close();
                    inputStreamReader.close();
                    inputStream.close();

                    // アウトプット
                    Log.d("result=============:", stringBuilder.toString());

                    return stringBuilder.toString().trim();
                }
            } catch (Exception e) {
                Log.d("debug", "HttpURLConnection error");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        // 実行後
        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(MainActivity.this, ManagementActivity.class);
            intent.putExtra("userList", result);
            MainActivity.this.startActivity(intent);
        }
    }
}
