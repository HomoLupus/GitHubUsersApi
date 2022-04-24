package com.example.githubusersapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class UsersRecyclerViewAdapter extends RecyclerView.Adapter<UsersRecyclerViewAdapter.UsersRecyclerViewHolder> {

    private List<User> userList;

    public UsersRecyclerViewAdapter(List<User> userList) {
        this.userList = userList;
    }

    public class UsersRecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView text_name, text_id;
        public ImageView imageView;


        public UsersRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            text_name = (TextView) itemView.findViewById(R.id.text_name);
            text_id = (TextView) itemView.findViewById(R.id.text_id);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

        }
    }

    @NonNull
    @Override
    public UsersRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // stwowrz nowy obiekt, wypelnij go (co, gdzie, nwm)
        View contactView = inflater.inflate(R.layout.row,parent,false);

        UsersRecyclerViewHolder viewHolder = new UsersRecyclerViewHolder(contactView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull UsersRecyclerViewAdapter.UsersRecyclerViewHolder holder, int position) {
        User user = userList.get(position);

        TextView text_name = holder.text_name;
        TextView text_id = holder.text_id;
        ImageView imageView = holder.imageView;

        text_name.setText(user.login);
        text_id.setText(String.valueOf(user.id));

        setImage(user.avatar_url,imageView);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setImage(String url, ImageView imageView){

        AndroidNetworking.get(url)
                .setTag("imageRequestTag")
                .setPriority(Priority.MEDIUM)
                .setBitmapMaxHeight(999)
                .setBitmapMaxWidth(999)
                .setBitmapConfig(Bitmap.Config.ARGB_8888)
                .build()
                .getAsBitmap(new BitmapRequestListener() {
                    @Override
                    public void onResponse(Bitmap bitmap) {

                        imageView.setImageBitmap(bitmap);

                    }
                    @Override
                    public void onError(ANError error) {

                    }
                });

    }


}
