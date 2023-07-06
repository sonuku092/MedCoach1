package com.medical.medcoach.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.medical.medcoach.Adapter.Model;
import com.medical.medcoach.LoginRegisterActivity;
import com.medical.medcoach.R;
import com.medical.medcoach.databinding.FragmentProfileBinding;
import com.medical.medcoach.getOTPActivity;

import java.util.concurrent.TimeUnit;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    TextView UserName, UserEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        UserName=view.findViewById(R.id.profile_name1);
        UserEmail=view.findViewById(R.id.useremail1);
        UserEmail.setText("Sonu Kumar");

        binding = FragmentProfileBinding.inflate(inflater,container,false);

        binding.logoutBtn.setOnClickListener(v->{

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(),LoginRegisterActivity.class));
            getActivity().finish();
        });

     return binding.getRoot();
    }

}