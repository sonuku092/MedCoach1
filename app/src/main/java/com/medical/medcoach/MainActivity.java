package com.medical.medcoach;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.medical.medcoach.Adapter.AdapterViewPager;
import com.medical.medcoach.Fragment.BlogFragment;
import com.medical.medcoach.Fragment.HomeFragment;
import com.medical.medcoach.Fragment.ProfileFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    BottomNavigationView bottomNavigationView;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager2 = findViewById(R.id.viewPager);

        bottomNavigationView=findViewById(R.id.bottomNav);

        fragmentArrayList.add(new HomeFragment());
        fragmentArrayList.add(new BlogFragment());
        fragmentArrayList.add(new ProfileFragment());

        AdapterViewPager adapterViewPager =new AdapterViewPager(this,fragmentArrayList);
        //set adapter
        viewPager2.setAdapter(adapterViewPager);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.blog);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.profile);
                        break;
                }
                super.onPageSelected(position);
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id==R.id.home) {
                    viewPager2.setCurrentItem(0);
                }
                else if (id==R.id.blog) {
                    viewPager2.setCurrentItem(1);
                }
                else if (id==R.id.profile)
                    viewPager2.setCurrentItem(2);

                return true;
            }
        });

    }

}
