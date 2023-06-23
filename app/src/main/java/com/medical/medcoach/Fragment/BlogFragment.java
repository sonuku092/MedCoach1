package com.medical.medcoach.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.medical.medcoach.Adapter.RecyclerBlogsAdapter;
import com.medical.medcoach.Blogs;
import com.medical.medcoach.R;
import com.medical.medcoach.cate_meditation;
import com.medical.medcoach.cont_blog;

import java.util.ArrayList;


public class BlogFragment extends Fragment {

    ArrayList<Blogs> blogsArrayList = new ArrayList<>();

    RecyclerView recyclerView;

    FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_blog, container, false);

        recyclerView= view.findViewById(R.id.recyclerv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        floatingActionButton=view.findViewById(R.id.floatingActionButton);

        blogsArrayList.add(new Blogs(R.drawable.img1, "Maditation", "Sonu","02/feb"));
        blogsArrayList.add(new Blogs(R.drawable.img1, "Maditation", "Sonu","02/feb"));
        blogsArrayList.add(new Blogs(R.drawable.img1, "Maditation", "Sonu","02/feb"));
        blogsArrayList.add(new Blogs(R.drawable.img1, "Maditation", "Sonu","02/feb"));
        RecyclerBlogsAdapter adapter = new RecyclerBlogsAdapter(getContext(),blogsArrayList);

        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.addblogs);

                EditText Titles = dialog.findViewById(R.id.title);
                EditText Contents = dialog.findViewById(R.id.contents);
                Button SaveBtn = dialog.findViewById(R.id.Save_Btn);
                Button cancel = dialog.findViewById(R.id.CancelBtn);

                SaveBtn.setOnClickListener(view1 -> {
                    if (!Titles.getText().toString().isEmpty()||!Contents.getText().toString().isEmpty()) {
                        String title = Titles.getText().toString();
                        String contents = Contents.getText().toString();
                    }
                });
                dialog.show();
            }
        });


        return view;
    }

}