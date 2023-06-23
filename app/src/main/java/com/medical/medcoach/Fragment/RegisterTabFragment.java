package com.medical.medcoach.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
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
    int flag;


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
                Toast.makeText(getActivity(), "Password Not Strong.", Toast.LENGTH_SHORT).show();
                reg_password.requestFocus();
            } else if (!passwordTxt.equals(cpasswordTxt)) {
                Toast.makeText(getActivity(), "Password Not Matching.", Toast.LENGTH_SHORT).show();
                c_password.requestFocus();
            } else {

//                if (!isEmail(phoneTxt)){
//                    firebaseFirestore.collection("Users")
//                        .whereEqualTo("Number",phoneTxt)
//                        .get()
//                        .addOnCompleteListener(task -> {
//                            if (task.isSuccessful()){
//                                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
//                                    String number1 = (String) documentSnapshot.get("Number");
//                                }
//                                Toast.makeText(getContext(), "Already Have Acc", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(e -> {
//
//                        });
                if(!Patterns.EMAIL_ADDRESS.matcher(phoneTxt).matches()){
                    FirebaseUser  firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                    String phone=firebaseUser.getPhoneNumber();
                    Toast.makeText(getContext(), phone, Toast.LENGTH_SHORT).show();
                    if(!phone.equals(phoneTxt)) {
                        // Firestore Database;
                        Map<String, Object> user = new HashMap<>();
                        user.put("First Name", fullnameTxt);
                        user.put("Password", passwordTxt);
                        user.put("Number", phoneTxt);
                        firebaseFirestore.collection("Users")
                                .add(user)
                                .addOnSuccessListener(documentReference -> startActivity(new Intent(getActivity(), SplashActivity.class)));
                    }else{
                        reg_phone.setError("User is already created");
                        reg_phone.requestFocus();
                    }
                }else {
                    Map<String, Object> user = new HashMap<>();
                    user.put("First Name", fullnameTxt);
                    user.put("Password", passwordTxt);
                    user.put("Email", phoneTxt);

                    firebaseFirestore.collection("Users")
                        .whereEqualTo("Email",phoneTxt)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                    String pass = (String) documentSnapshot.get("Password");
                                    Toast.makeText(getContext(), pass, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                            .addOnFailureListener(e -> {

                            });
                    Toast.makeText(getContext(), "Create new acc", Toast.LENGTH_SHORT).show();
                    firebaseFirestore.collection("Users")
                            .add(user)
                            .addOnSuccessListener(documentReference -> {

                            })
                            .addOnFailureListener(e -> Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show());

                }


//                    firebaseFirestore.collection("Users")
//                            .whereEqualTo("Email",phoneTxt)
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    if (task.isSuccessful()){
//                                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
//                                            String name = (String) documentSnapshot.get("First Name");
//                                            String pass = (String) documentSnapshot.get("Password");
//                                            Toast.makeText(getContext(), pass, Toast.LENGTH_SHORT).show();
//                                        }
//                                    }else {
//                                        Toast.makeText(getContext(), "Create new acc", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });




//                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            //Check if email is not register
//                            if (snapshot.hasChild("+91"+phoneTxt)){
//                                Toast.makeText(getActivity(), "Phone No is already registered", Toast.LENGTH_SHORT).show();
//                                reg_phone.requestFocus();
//                            } else {
//                                PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                                        "+91" + phoneTxt,
//                                        60,
//                                        TimeUnit.SECONDS,
//                                        getActivity(),
//                                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                                            @Override
//                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
//
//                                            }
//
//                                            @Override
//                                            public void onVerificationFailed(@NonNull FirebaseException e) {
//                                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                                            }
//
//                                            @Override
//                                            public void onCodeSent(@NonNull String Sendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                                Intent intent = new Intent(getActivity(),getOTPActivity.class);
//                                                intent.putExtra("phoneTxt",phoneTxt);
//                                                intent.putExtra("backend",Sendotp);
//                                                intent.putExtra("FullName",fullnameTxt);
//                                                intent.putExtra("Password",passwordTxt);
//                                                startActivity(intent);
//                                            }
//                                        }
//                                );
//                                //sending data to Firebase
//
//
////                                mAuth.createUserWithEmailAndPassword(phoneTxt, passwordTxt)
////                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
////                                            @Override
////                                            public void onComplete(@NonNull Task<AuthResult> task) {
////                                                if (task.isSuccessful()) {
////                                                         databaseReference.child("Users").child(phoneTxt).child("Full Name").setValue(fullnameTxt);
////                                                         databaseReference.child("Users").child(phoneTxt).child("Password").setValue(passwordTxt);
////                                                    // Sign in success, update UI with the signed-in user's information
//////                                                    FirebaseUser user = mAuth.getCurrentUser();
////
////                                                    Toast.makeText(getActivity(), "Account Created.",
////                                                            Toast.LENGTH_SHORT).show();
////
////                                                    Intent intent = new Intent(getActivity(),MainActivity.class);
////                                                    startActivity(intent);
////
////                                                } else {
////                                                    // If sign in fails, display a message to the user.
////                                                    Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
////                                                }
////                                            }
////                                        });
//
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
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