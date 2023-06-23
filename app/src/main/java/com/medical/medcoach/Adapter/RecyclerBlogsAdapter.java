package com.medical.medcoach.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medical.medcoach.Blogs;
import com.medical.medcoach.R;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class RecyclerBlogsAdapter extends RecyclerView.Adapter<RecyclerBlogsAdapter.ViewHolder> {
    Context context;
    ArrayList<Blogs> arrayList;
    public RecyclerBlogsAdapter(Context context, ArrayList<Blogs> arrayList){
        this.context=context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.blog_row, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Blogimg.setImageResource(arrayList.get(position).img);
        holder.Title.setText(arrayList.get(position).Title);
        holder.Author.setText(arrayList.get(position).Author);
        holder.Date.setText(arrayList.get(position).Date);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Title, Author, Date;
        ImageView Blogimg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.title);
            Author =itemView.findViewById(R.id.author);
            Date = itemView.findViewById(R.id.date);
            Blogimg = itemView.findViewById(R.id.blogimg);
        }
    }
}
