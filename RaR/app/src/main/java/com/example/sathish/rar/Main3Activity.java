package com.example.sathish.rar;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Main3Activity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //  private Button logout;
        Button admin = findViewById(R.id.button9);
        Button user = findViewById(R.id.button2);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Main3Activity.this, Main8Activity.class));
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Main3Activity.this, ItemsActivity.class));
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();

      //  logout = (Button)findViewById(R.id.button2);
       // logout.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View v) {
       //       Logout();
       //     }
        //});

  // }
   // private void  Logout(){
    //  firebaseAuth.signOut();
    //   finish();
     //   startActivity(new Intent(Main3Activity.this,Main2Activity.class));
   }
    @Override
   public boolean onCreateOptionsMenu(Menu menu){
       getMenuInflater().inflate(R.menu.menu,menu);
       return true;
   }
   @Override
    public  boolean onOptionsItemSelected (MenuItem item){
         switch(item.getItemId()){
             case  R.id.logoutMenu:{
                 firebaseAuth.signOut();
                 finish();
                 startActivity(new Intent(Main3Activity.this,Main2Activity.class));
                 break;
             }
             case R.id.profileMenu:
                 startActivity(new Intent(Main3Activity.this,Main6Activity.class));
                 break;
         }
         return super.onOptionsItemSelected(item);
   }

}
