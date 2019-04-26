package com.example.sathish.rar;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DetailsActivity extends AppCompatActivity {
    TextView nameDetailTextView,descriptionDetailTextView,dateDetailTextView,categoryDetailTextView;
    ImageView teacherDetailImageView;
    private void initializeWidgets(){
        nameDetailTextView= findViewById(R.id.nameDetailTextView);
        descriptionDetailTextView= findViewById(R.id.descriptionDetailTextView);
        dateDetailTextView= findViewById(R.id.dateDetailTextView);
        categoryDetailTextView= findViewById(R.id.categoryDetailTextView);
        teacherDetailImageView=findViewById(R.id.teacherDetailImageView);
    }
    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }
    private String getRandomCategory(){
        String[] categories={"ZEN","BUDHIST","YOGA"};
        Random random=new Random();
        int index=random.nextInt(categories.length-1);
        return categories[index];
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initializeWidgets();
//RECEIVE DATA FROM ITEMSACTIVITY VIA INTENT
        Intent i=this.getIntent();
        String name=i.getExtras().getString("NAME_KEY");
        String description=i.getExtras().getString("DESCRIPTION_KEY");
        String imageURL=i.getExtras().getString("IMAGE_KEY");
//SET RECEIVED DATA TO TEXTVIEWS AND IMAGEVIEWS
        nameDetailTextView.setText(name);
        descriptionDetailTextView.setText(description);
        dateDetailTextView.setText("DATE: "+getDateToday());
        categoryDetailTextView.setText("CATEGORY: "+getRandomCategory());
        Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
             }});



        Picasso.get()
                .load(imageURL)
                .placeholder(R.drawable.avatar)
                .fit()
                .centerCrop()
                .into(teacherDetailImageView);
    }

}
