package com.dubinsky.room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editName, editEmail;
    RecyclerView recyclerView;
    UserAdapter adapter;
    List<User> list;
    static Handler handler;
    int position;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        list = new ArrayList<>();
        adapter = new UserAdapter(this, list);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        handler = new Handler(){
            public void handleMessage(@NonNull Message message){
                adapter.list = list;
                editName.setText("");
                editEmail.setText("");
                adapter.notifyDataSetChanged();
            }
        };

        Thread dbThread = new Thread(new Runnable() {
            @Override
            public void run() {
                list = App.db.userDao().readAll();
                handler.sendEmptyMessage(0);
            }
        });
        dbThread.start();
    }

    public void onAddClick(View view){
        Thread dbThread = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.id = App.db.userDao().count()+1;
                user.name = editName.getText().toString();
                user.email = editEmail.getText().toString();
                App.db.userDao().insert(user);
                list.add(user);
                handler.sendEmptyMessage(0);
            }
        });
        dbThread.start();
    }

    public void onReadClick(View view){
        Thread dbThread = new Thread(new Runnable() {
            @Override
            public void run() {
                list.clear();
                list = App.db.userDao().readAll();
                for (User user: list) {
                    Log.d("Contacts", "ID = " + (user.id+1)
                            + ", name = " + user.name + ", email = " + user.email);
                }
                handler.sendEmptyMessage(0);
            }
        });
        dbThread.start();
    }

    public void onUpdateClick(View view){
        Thread dbThread = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = App.db.userDao().readUser(position);
                if (user != null){
                    user.name = editName.getText().toString();
                    user.email = editEmail.getText().toString();
                    App.db.userDao().update(user);
                    list.set(user.id, user);
                } else {
                    user = new User();
                    user.id = App.db.userDao().count();
                    user.name = editName.getText().toString();
                    user.email = editEmail.getText().toString();
                    App.db.userDao().insert(user);
                    list.add(user);
                }
                handler.sendEmptyMessage(0);
            }
        });
        dbThread.start();
    }


    public void onDeleteClick(View view){
        Thread dbThread = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = App.db.userDao().readUser(position);
                    user.name = editName.getText().toString();
                    user.email = editEmail.getText().toString();
                    App.db.userDao().delete(user);
                    list.set(user.id, user);
                    onReadClick(null);
                handler.sendEmptyMessage(0);
            }
        });
        dbThread.start();
    }


    public void onClearClick(View view){
        Thread dbThread = new Thread(new Runnable() {
            @Override
            public void run() {
                App.db.userDao().clear();
                list.clear();
                handler.sendEmptyMessage(0);
            }
        });
        dbThread.start();
    }
}