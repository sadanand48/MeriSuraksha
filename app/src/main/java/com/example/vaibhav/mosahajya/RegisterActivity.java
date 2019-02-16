package com.example.vaibhav.mosahajya;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    Button register;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    EditText phone;
    HashMap<String,String> hm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register=(Button)findViewById(R.id.submit);
        phone=(EditText)findViewById(R.id.phone_number);
        hm=new HashMap<String, String>();
        firebaseDatabase=FirebaseDatabase.getInstance();
       reference=firebaseDatabase.getReference();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ph=phone.getText().toString();
                FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Log.d("success", "Successful");
                                Toast.makeText(RegisterActivity.this, "successful", Toast.LENGTH_SHORT).show();
                            }
                        });
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( RegisterActivity.this, new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String mToken = instanceIdResult.getToken();
                        Log.e("Token",mToken);
                        hm.put(ph,mToken);
                        reference.child("Emergency Contacts").push().setValue(hm);
                        Toast.makeText(getApplicationContext(),"Succesfully registered" ,Toast.LENGTH_LONG).show();
                        finish();

                    }
                });

            }
        });
    }
}
