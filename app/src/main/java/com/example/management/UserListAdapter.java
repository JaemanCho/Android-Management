package com.example.management;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;

    public UserListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.user, null);

        TextView userId = (TextView) v.findViewById(R.id.useId);
        TextView userPassword = (TextView) v.findViewById(R.id.usePassword);
        TextView userName = (TextView) v.findViewById(R.id.userName);
        TextView userAge = (TextView) v.findViewById(R.id.useAge);

        userId.setText(userList.get(position).getUserId());
        userPassword.setText(userList.get(position).getUserPassword());
        userName.setText(userList.get(position).getUserName());
        userAge.setText(userList.get(position).getUserAge());

        v.setTag(userList.get(position).getUserId());
        return v;
    }
}
