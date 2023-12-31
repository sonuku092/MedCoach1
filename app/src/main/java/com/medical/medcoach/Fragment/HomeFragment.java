package com.medical.medcoach.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.medical.medcoach.R;
import com.medical.medcoach.cate_acupuncture;
import com.medical.medcoach.cate_ayurveda;
import com.medical.medcoach.cate_hypnotherapy;
import com.medical.medcoach.cate_meditation;
import com.medical.medcoach.cate_reiki;
import com.medical.medcoach.cate_yoga;
import com.medical.medcoach.databinding.ActivityMainBinding;


public class HomeFragment extends Fragment {

    private ScrollView scrollView1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RelativeLayout relativeLayout = view.findViewById(R.id.relativeLayout_ayurveda);

        relativeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), cate_ayurveda.class);
            startActivity(intent);
        });

        RelativeLayout relativeLayout1=view.findViewById(R.id.relativeLayout_meditation);
        relativeLayout1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), cate_meditation.class);
            startActivity(intent);
        });

        RelativeLayout relativeLayout2=view.findViewById(R.id.relativeLayout_acupunture);
        relativeLayout2.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(), cate_acupuncture.class);
            startActivity(intent);
        });

        RelativeLayout relativeLayout3=view.findViewById(R.id.relativeLayout_yoga);
        relativeLayout3.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(), cate_yoga.class);
            startActivity(intent);
        });

        RelativeLayout relativeLayout4=view.findViewById(R.id.relativeLayout_hypnotherapy);
        relativeLayout4.setOnClickListener(v->{
            Intent intent=new Intent(getActivity(), cate_hypnotherapy.class);
            startActivity(intent);
        });

        RelativeLayout relativeLayout5=view.findViewById(R.id.relativeLayout_reiki);
        relativeLayout5.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(), cate_reiki.class);
            startActivity(intent);
        });
        return view;

    }

}