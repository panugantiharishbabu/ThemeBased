package com.example.sathish.rar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.squareup.picasso.Picasso.get;

public  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{
    private Context mContext;
    Teacher currentTeacher;
    RecyclerViewHolder holder;
    private List<Teacher> teachers;
    private OnItemClickListener mListener;
    public RecyclerAdapter(Context context, List<Teacher> uploads,OnItemClickListener listener) {
        mContext = context;
        teachers = uploads;
        mListener=listener;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_model, parent, false);

        return new RecyclerViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder1, int position) {
         currentTeacher = teachers.get(position);
         holder=holder1;
        Log.d("Recycle", "Inside BindView Recycle Adapter");
        holder.nameTextView.setText(currentTeacher.getName());
        holder.descriptionTextView.setText(currentTeacher.getDescription());
        holder.dateTextView.setText(getDateToday());
        Log.d("Recycle", "here");
        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable(){
            @Override
            public void run() {
                Picasso.get()
                        .load(currentTeacher.getImageUrl()
                        ).placeholder(R.drawable.avatar)
                        .into(holder.teacherImageView, new Callback() {
                            @Override
                            public void onSuccess() {

                                Log.d("Image Load","Success");

                            }

                            @Override
                            public void onError(Exception e) {
                                Log.d("Image Load",e.toString());

                            }
                        });


            }


                });
}
           // private Teacher get(Context mContext) {
   //     return null;
 //   }

    @Override
    public int getItemCount() {
        return teachers.size();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        TextView nameTextView,descriptionTextView,dateTextView;
        ImageView teacherImageView;
        RecyclerViewHolder(View itemView) {

            super(itemView);
            Log.d("Recycle","after here teacher imageview");
            nameTextView =itemView.findViewById ( R.id.nameTextView );
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            teacherImageView = itemView.findViewById(R.id.teacherImageView);
            Log.d("Recycle","after here teacher imageview");
            itemView.setOnClickListener(this);
          itemView.setOnCreateContextMenuListener(this);
        }
        @Override
        public void onClick(View v) {
            Log.d("Listener","Hi"+mListener);

            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem showItem = menu.add( Menu.NONE, 1, 1, "Show");
            MenuItem deleteItem = menu.add(Menu.NONE, 2, 2, "Delete");
            showItem.setOnMenuItemClickListener(this);
            deleteItem.setOnMenuItemClickListener(this);
        }
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Log.d("Listener","Hi1");
            Log.d("Listener","Hi"+mListener);

            if (mListener != null) {
                Log.d("Listener","Hi2");
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (item.getItemId()) {
                        case 1:
                            Log.d("Listener","Hi1");
                            mListener.onShowItemClick(position);
                            Log.d("Listener","Hi2");
                            return true;
                        case 2:
                            mListener.onDeleteItemClick(position);
                            Log.d("Listener","Hi3");
                            return true;
                    }
                }
            }
            Log.d("Listener","Hi5");
            return false;
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onShowItemClick(int position);
        void onDeleteItemClick(int position);
    }


    private String getDateToday(){
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        return dateFormat.format(date);
    }
}
