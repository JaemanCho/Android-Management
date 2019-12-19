package com.example.management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManagementActivity<viod> extends AppCompatActivity {

    private ListView listView;
    private UserListAdapter adapter;
    private List<User> userList;
    private List<User> saveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        Intent intent = getIntent();

        listView = (ListView) findViewById(R.id.listView);
        userList = new ArrayList<User>();
        saveList = new ArrayList<User>();

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");

            int count = 0;
            String userId, userPassword, userName, userAge;

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);

                userId = object.getString("userID");
                userPassword = object.getString("userPassword");
                userName = object.getString("userName");
                userAge = object.getString("userAge");

                User user = new User(userId, userPassword, userName, userAge);
                if (!userId.equals("admin")) {
                    userList.add(user);
                    saveList.add(user);
                }

                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new UserListAdapter(getApplicationContext(), userList, this, saveList);
        listView.setAdapter(adapter);

        EditText search = (EditText) findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // 文字が変わるたびに呼ばれる
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                searchUser(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void searchUser(String search) {
        // すべて削除
        userList.clear();
        for (int i = 0; i < saveList.size(); i++) {
            if (saveList.get(i).getUserId().contains(search)) {
                // 該当Userを追加
                userList.add(saveList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
