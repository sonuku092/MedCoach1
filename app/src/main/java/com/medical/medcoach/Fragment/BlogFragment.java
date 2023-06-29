package com.medical.medcoach.Fragment;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.medical.medcoach.Adapter.RecyclerBlogsAdapter;
import com.medical.medcoach.Blogs;
import com.medical.medcoach.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class BlogFragment extends Fragment {

    ArrayList<Blogs> blogsArrayList = new ArrayList<>();

    RecyclerView recyclerView;

    FloatingActionButton floatingActionButton;
    Calendar calendar = Calendar.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Uri ImageUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_blog, container, false);

        recyclerView= view.findViewById(R.id.recyclerv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        floatingActionButton=view.findViewById(R.id.floatingActionButton);

        blogsArrayList.add(new Blogs(R.drawable.uploadimg, "Satyaprem Ki Katha movie review: Imperfect but much-needed breath of fresh air from Bollywood, Kiara-Kartik shine bright", "Sonu","02/feb"));
        blogsArrayList.add(new Blogs(R.drawable.img3, "Satyaprem Ki Katha movie review: Imperfect but much-needed breath of fresh air from Bollywood, Kiara-Kartik shine bright", "Sonu","02/feb"));
        blogsArrayList.add(new Blogs(R.drawable.img3, "Satyaprem Ki Katha movie review: Imperfect but much-needed breath of fresh air from Bollywood, Kiara-Kartik shine bright", "Sonu","02/feb"));
        blogsArrayList.add(new Blogs(R.drawable.img1, "Reiki", "Sajan","Jun 29, 2023"));
        blogsArrayList.add(new Blogs(R.drawable.img1, "Ayurveda", "Tek","20/Jan"));
        blogsArrayList.add(new Blogs(R.drawable.img1, "Acupuncture", "Sushil","02/feb"));
        blogsArrayList.add(new Blogs(R.drawable.img1, "Yoga", "Arpit","10/Aug"));
        RecyclerBlogsAdapter adapter = new RecyclerBlogsAdapter(getContext(),blogsArrayList);

        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.addblogs);

                EditText Titles = dialog.findViewById(R.id.title);
                EditText Contents = dialog.findViewById(R.id.contents);
                EditText AuthorName = dialog.findViewById(R.id.AuthorName);
                TextView Date = dialog.findViewById(R.id.Date);
                Button SaveBtn = dialog.findViewById(R.id.Save_Btn);
                Button cancel = dialog.findViewById(R.id.CancelBtn);
                ImageView UploadImg = dialog.findViewById(R.id.uploadimg);
                UploadImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        StorageReference storageReference = firebaseStorage.getReference().child("Blog Images");
                        storageReference.putFile(ImageUrl)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(getContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                                    }
                                });
                    }
                });

                String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
                Date.setText(currentDate);

                SaveBtn.setOnClickListener(view1 -> {
                    if (!Titles.getText().toString().isEmpty()||!Contents.getText().toString().isEmpty()) {
                        String title = Titles.getText().toString();
                        String contents = Contents.getText().toString();
                        String author = AuthorName.getText().toString();

                        Map<String, Object> user = new HashMap<>();
                        user.put("Title", title);
                        user.put("Contents", contents);
                        user.put("Date", currentDate);
                        user.put("Author", author);
                        firebaseFirestore.collection("Blogs")
                                .add(user)
                                .addOnSuccessListener(documentReference -> {
                                    dialog.cancel();
                                }).addOnFailureListener(e -> {
                                });

                    }else {
                        Titles.setError("Should Not Empty");
                        Titles.requestFocus();
                    }
                });
                cancel.setOnClickListener(view1 -> {
                    dialog.dismiss();
                });
                dialog.show();
            }

        });
        return view;
    }

}