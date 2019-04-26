package com.example.sathish.rar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends Activity {
    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView Info;
    private int counter = 3;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);


        Name = (EditText) findViewById(R.id.editText);
        Password = (EditText) findViewById(R.id.editText2);
        Login = (Button) findViewById(R.id.button4);
        Info = (TextView) findViewById(R.id.textView6);
        userRegistration = (TextView) findViewById(R.id.textView7);
        forgotPassword = (TextView) findViewById(R.id.textView3);
        Info.setText("No of attempts remaining: 3");

        Name.addTextChangedListener(loginTextWatcher);
        Password.addTextChangedListener(loginTextWatcher);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            finish();
            startActivity(new Intent(Main2Activity.this, Main3Activity.class));
        }


             Login.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     validate(Name.getText().toString(), Password.getText().toString());
                 }

             });


             userRegistration.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     startActivity(new Intent(Main2Activity.this, Main4Activity.class));
                 }
             });
             forgotPassword.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     startActivity(new Intent(Main2Activity.this, Main5Activity.class));
                 }
             });
         }


   private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String usernameInput = Name.getText().toString().trim();
            String passwordInput = Password.getText().toString().trim();

            Login.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void validate(String userName, String userPassword) {
        progressDialog.setMessage("until you verified");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    //Toast.makeText(Main2Activity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    checkEmailVerification();
                } else {
                    Toast.makeText(Main2Activity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No of attempts remaining: " + counter);
                    progressDialog.dismiss();
                    if (counter == 0) {
                        Login.setEnabled(false);

                    }
                }
            }
        });

    }

    private void checkEmailVerification() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        if (emailflag) {
            finish();
            startActivity(new Intent(Main2Activity.this, Main3Activity.class));
        } else {
            Toast.makeText(this, "verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }

    }
}

   /*private Boolean valid(String s, String toString){
        Boolean res = false;
       userinput = Name.getText().toString();
       passwordinput = Password.getText().toString();
       if(userinput.isEmpty() || passwordinput.isEmpty()){
           Toast.makeText(this, "please enter all the details", Toast.LENGTH_SHORT).show();
       }

       else {
            res = true;
        }
        return res;
    }
}*/
