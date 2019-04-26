package com.example.sathish.rar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Main4Activity extends AppCompatActivity {
     private EditText userName,userPassword,userEmail;
     private Button regButton;
     private TextView userLogin;
     private ImageView userProfilePic;
     private FirebaseAuth firebaseAuth;
     private static int PICK_IMAGE = 123;
     String email,name,password;
     private FirebaseStorage firebaseStorage;
     private StorageReference storageReference;

     Uri imagePath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData()!=null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                userProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();


        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();
       // StorageReference myRef1 = storageReference.child(firebaseAuth.getUid());

        userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");//application/* audio/* pdf/* doc/*
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select image"),PICK_IMAGE);
            }
        });


        regButton. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    //database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                sendEmailVerification();
                            }
                            else{
                                Toast.makeText(Main4Activity.this, "registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        userLogin.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View v) {
                startActivity(new Intent(Main4Activity.this,Main2Activity.class));
            }
        });
    }
    private void setupUIViews(){
        userName = (EditText)findViewById(R.id.editText3);
        userPassword = (EditText)findViewById(R.id.editText5);
        userEmail = (EditText)findViewById(R.id.editText4);
        regButton = (Button)findViewById(R.id.button);
        userLogin = (TextView)findViewById(R.id.textView4);
        userProfilePic = (ImageView)findViewById(R.id.imageView3);
    }

    private Boolean validate(){
        Boolean result = false;

         name = userName.getText().toString();
         password = userPassword.getText().toString();
         email = userEmail.getText().toString();
        if(name.isEmpty() || password.isEmpty() || email.isEmpty()||imagePath == null){
            Toast.makeText(this, "please enter all the details", Toast.LENGTH_SHORT).show();
            }
            else {
            result = true;
        }
        return result;
    }
    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData(); 
                        Toast.makeText(Main4Activity.this,"succesfully registered,verification mail sent",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(Main4Activity.this,Main2Activity.class));
                    }
                    else{
                        Toast.makeText(Main4Activity.this,"verification mail hasn't been sent!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");//User Id/Images/profile_pic.png
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Main4Activity.this,"Upload Failed",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Main4Activity.this,"Upload Succesful",Toast.LENGTH_SHORT).show();
            }
        });
        UserProfile userProfile = new UserProfile(name,email);
        myRef.setValue(userProfile);
    }
}
