 package com.example.sathish.rar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

 public class Main5Activity extends AppCompatActivity {
    private EditText passwordEmail;
    private Button resetPassword;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        passwordEmail = (EditText)findViewById(R.id.editText6);
        resetPassword = (Button)findViewById(R.id.button3);
        firebaseAuth = FirebaseAuth.getInstance();
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = passwordEmail.getText().toString().trim();
                if(useremail.equals("")){
                    Toast.makeText(Main5Activity.this,"please enter your register email ID",Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Main5Activity.this,"password reset email sent!",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Main5Activity.this,Main2Activity.class));
                            }
                            else{
                                Toast.makeText(Main5Activity.this,"Error in sending password",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
