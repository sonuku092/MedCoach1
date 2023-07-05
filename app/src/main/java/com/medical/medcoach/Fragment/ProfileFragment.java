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
import com.medical.medcoach.LoginRegisterActivity;
import com.medical.medcoach.R;
import com.medical.medcoach.databinding.FragmentProfileBinding;
import com.medical.medcoach.getOTPActivity;

import java.util.concurrent.TimeUnit;

public class ProfileFragment extends Fragment {

    GoogleSignInAccount account;
//    Button logoutBtn;

    FragmentProfileBinding binding;

    DatabaseReference databaseReference;
    TextView UserName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        binding = FragmentProfileBinding.inflate(inflater,container,false);

        UserName=view.findViewById(R.id.profile_name);

        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child("+919999996262").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String Name = String.valueOf(dataSnapshot.child("Full Name").getValue());
                        UserName.setText(Name);

                    }else {
                        Toast.makeText(getActivity(), "User Not Exist", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
        binding.logoutBtn.setOnClickListener(v->{

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(),LoginRegisterActivity.class));
            getActivity().finish();
        });

     return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intivar();
    }

    private void intivar() {
        account = GoogleSignIn.getLastSignedInAccount(getContext());
    }


    }