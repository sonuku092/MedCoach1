package com.medical.medcoach.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.medical.medcoach.R;
import com.medical.medcoach.SplashActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class RegisterTabFragment extends Fragment {
    //create Database
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    TextInputEditText Name, reg_phone, reg_password , c_password;
    Button register_btn, reset_btn;
    int flag = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_tab, container, false);

        Name=view.findViewById(R.id.Name);
        reg_phone=view.findViewById(R.id.phoneno);
        reg_password=view.findViewById(R.id.regpassword);
        c_password=view.findViewById(R.id.cpassword);

        register_btn=view.findViewById(R.id.Reg_Btn);
        reset_btn=view.findViewById(R.id.Res_Btn);

        mAuth=FirebaseAuth.getInstance();

        register_btn.setOnClickListener(v -> {
            //Get data from the XML design page
            final String fullnameTxt, phoneTxt, passwordTxt, cpasswordTxt;
            fullnameTxt = String.valueOf(Name.getText());
            phoneTxt = String.valueOf(reg_phone.getText());
            passwordTxt = String.valueOf(reg_password.getText());
            cpasswordTxt = String.valueOf(c_password.getText());

            //Check field is empty
            if (fullnameTxt.isEmpty()||phoneTxt.isEmpty()||passwordTxt.isEmpty()||cpasswordTxt.isEmpty()){
                Toast.makeText(getActivity(), "Please fill all Fields.", Toast.LENGTH_SHORT).show();
            } else if (!isValidPassword(passwordTxt)) {
                reg_password.setError("Weak Password");
                reg_password.requestFocus();
            } else if (!passwordTxt.equals(cpasswordTxt)) {
                c_password.setError("Not Match");
                c_password.requestFocus();
            } else {

                if (!isEmail(phoneTxt)){

                    firebaseFirestore.collection("Users")
                        .whereEqualTo("Number",phoneTxt)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                    String number1 = (String) documentSnapshot.get("Number");
                                    if (!number1.isEmpty()){
                                        flag=1;
                                    }
                                }
                            }
                        }).addOnFailureListener(e -> {

                        });
                    if(!(flag ==1)){
                        Map<String, Object> user = new HashMap<>();
                        user.put("First Name", fullnameTxt);
                        user.put("Password", passwordTxt);
                        user.put("Number", phoneTxt);
                        firebaseFirestore.collection("Users")
                                .add(user)
                                .addOnSuccessListener(documentReference -> {

                                });
                        flag=0;
                    }
                }else {


                    firebaseFirestore.collection("Users")
                                    .whereEqualTo("Email",phoneTxt)
                                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                        String number1 = (String) documentSnapshot.get("Number");
                                        if (!number1.isEmpty()){
                                            flag=1;
                                            }
                                    }
                                }
                            }).addOnFailureListener(e -> {
                            });
                    if (!(flag ==1)) {
                        Map<String, Object> user = new HashMap<>();
                        user.put("First Name", fullnameTxt);
                        user.put("Password", passwordTxt);
                        user.put("Email", phoneTxt);
                        firebaseFirestore.collection("Users")
                                .add(user)
                                .addOnSuccessListener(documentReference -> {
                                    getActivity().onBackPressed();
                                }).addOnFailureListener(e -> {
                                });
                        flag=0;
                    }

                }

            }

        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name.setText("");
                reg_phone.setText("");
                reg_password.setText("");
                c_password.setText("");
                Name.requestFocus();
            }
        });


        return view;
    }

    Pattern lowerCase= Pattern.compile("^.*[a-z].*$");
    Pattern upperCase=Pattern.compile("^.*[A-Z].*$");
    Pattern number = Pattern.compile("^.*[0-9].*$");
    Pattern special_Chara = Pattern.compile("^.*[^a-zA-Z0-9].*$");
    private Boolean isValidPassword(String password){
        if(password.length()<8) {
            return false;
        }
        if(!lowerCase.matcher(password).matches()) {
            return false;
        }
        if(!upperCase.matcher(password).matches()) {
            return false;
        }
        if(!number.matcher(password).matches()) {
            return false;
        }
        if(!special_Chara.matcher(password).matches()) {
            return false;
        }
        return true;
    }

    private  Boolean isEmail(String Email){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(Email).matches()){
            return false;
        }
        return true;
    }

}