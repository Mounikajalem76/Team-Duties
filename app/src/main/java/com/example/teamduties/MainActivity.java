package com.example.teamduties;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    EditText editText_username,editText_password;
    Button button_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
       editText_username=(EditText) findViewById(R.id.username);
       editText_password=(EditText) findViewById(R.id.password);
       button_signin=(Button) findViewById(R.id.SignIN);

    }
    public Boolean validateUsername(){
        String username=editText_username.getText().toString();
        if (username.isEmpty()){
            editText_password.setError("Username is Empty");
            return false;
        }else {
            editText_username.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String password=editText_password.getText().toString();
        if (password.isEmpty()){
            editText_password.setError("Password is Empty");
            return false;
        }else {
            editText_password.setError(null);
            return true;
        }
    }
    public void checkUsers(){
        String userUsername=editText_username.getText().toString().trim();
        String userPassword=editText_password.getText().toString().trim();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase=reference.orderByChild("username").equalTo(userUsername);


        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    editText_username.setError(null);
                    String passwordFromDB=snapshot.child(userUsername).child("password").getValue(String.class);
                    if (!Objects.equals(passwordFromDB,userPassword)){
                        editText_username.setError(null);

                    }else {
                        editText_password.setError("Invalid Creditials");
                        editText_password.requestFocus();
                    }
                }else {
                    editText_username.setError("Invalid Username");
                    editText_username.
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}