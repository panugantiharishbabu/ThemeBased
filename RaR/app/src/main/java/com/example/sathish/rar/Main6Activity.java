 package com.example.sathish.rar;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

 public class Main6Activity extends AppCompatActivity {
private ImageView profilePic;
private TextView profileName,profileEmail;
private Button profileUpdate;
private Button changePassword;

private FirebaseAuth firebaseAuth;
private FirebaseDatabase firebaseDatabase;
private FirebaseStorage firebaseStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        profilePic = findViewById(R.id.imageView4);
        profileName = findViewById(R.id.textView5);
        profileEmail = findViewById(R.id.textView8);
        profileUpdate = findViewById(R.id.button5);
        changePassword = findViewById(R.id.button6);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePic);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                profileName.setText("Name: " + userProfile.getUserName());
                profileEmail.setText("Email: " + userProfile.getUserEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Main6Activity.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main6Activity.this,UpdateProfile.class));
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main6Activity.this,UpdatePassword.class));
            }
        });
    }
     @Override
     public boolean onOptionsItemSelected(MenuItem item) {

         switch (item.getItemId()){
             case android.R.id.home:
                 onBackPressed();
         }
         return super.onOptionsItemSelected(item);
     }
}
