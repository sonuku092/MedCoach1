package com.medical.medcoach.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.medical.medcoach.LoginRegisterActivity;
import com.medical.medcoach.MainActivity;
import com.medical.medcoach.R;
import com.medical.medcoach.getOTPActivity;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class LoginTabFragment extends Fragment {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    TextInputEditText log_email, log_password;
    Button login_btn;
    FirebaseAuth mAuth;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInAccount googleSignInAccount;
    GoogleSignInClient googleSignInClient;
    SignInButton signInButton;
    int counter=3;


//    @Override
//    public void onStart() {
//        // Check if user is signed in (non-null) and update UI accordingly.
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            Intent intent= new Intent(getActivity(),MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
////            finish();
//        } else {
//            sign();
//        }
//    }

    private void sign() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent ,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==100){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                mAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_tab, container, false);

        log_email=view.findViewById(R.id.Login_Email);
        log_password=view.findViewById(R.id.Login_Password);

        login_btn=view.findViewById(R.id.Login_Btn);

        mAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(log_email.getText());
                password = String.valueOf(log_password.getText());

                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
                {
                    Toast.makeText(getActivity(),"Enter email & Password",Toast.LENGTH_SHORT).show();
                    counter--;
                } else if (email.length()==10) {
                    firebaseFirestore.collection("Users")
                                    .whereEqualTo("Number",email)
                                            .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if (task.isSuccessful()){
                                                                for (QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
                                                                    String pass = (String) queryDocumentSnapshot.get("Password");
                                                                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                                                            "+91" + email,
                                                                            60,
                                                                            TimeUnit.SECONDS,
                                                                            getActivity(),
                                                                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                                                @Override
                                                                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                                                                                }

                                                                                @Override
                                                                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                                                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                }

                                                                                @Override
                                                                                public void onCodeSent(@NonNull String Sendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                                                    Intent intent = new Intent(getActivity(), getOTPActivity.class);
                                                                                    intent.putExtra("phoneTxt",email);
                                                                                    intent.putExtra("backend",Sendotp);
                                                                                    startActivity(intent);
                                                                                }
                                                                            }
                                                                    );
                                                                }
                                                            }
                                                        }
                                                    });

                } else {

                    firebaseFirestore.collection("Users")
                            .whereEqualTo("Email",email)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                        String useremail = (String) documentSnapshot.get("Email");
                                        mAuth.signInWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            // Sign in success, update UI with the signed-in user's information
                                                            Toast.makeText(getActivity(), "Login Successful.",
                                                                    Toast.LENGTH_SHORT).show();
                                                            Intent intent= new Intent(getActivity(),MainActivity.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(intent);
                                                            getActivity().finish();

                                                        } else {
                                                            // If sign in fails, display a message to the user.
                                                            Toast.makeText(getActivity(), "Login failed.",Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                });
                                    }
                                }
                            }).addOnFailureListener(e -> {

                            });

                    counter--;
                }
                if(counter==0){
                    Toast.makeText(getActivity(),"failed to login attempts",Toast.LENGTH_SHORT).show();
                    login_btn.setEnabled(false);
                }

            }

        });

        signInButton = view.findViewById(R.id.googleLoginBtn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                googleSignInClient= GoogleSignIn.getClient(getActivity(),googleSignInOptions);
            }
        });



        return view;

    }

    private boolean validateEmail(){
        String email = Objects.requireNonNull(log_email.getText()).toString().trim();

        if (email.isEmpty()){
            log_email.setError("Field can't be Empty");
            return false;
        }
        else {
            log_email.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String password = Objects.requireNonNull(log_password.getText()).toString().trim();

        if (password.isEmpty()){
            log_password.setError("Field can't be Empty");
            return false;
        }
        else {
            log_password.setError(null);
            return true;
        }
    }

    public  void confirmInput(View v){
        if (!validateEmail() | !validatePassword()){
            return;
        }
        String input = "email: " + log_email.getText().toString();
        input +="\n";
        input = "Password: " + log_password.getText().toString();

        Toast.makeText(getActivity(), "Please Enter", Toast.LENGTH_SHORT).show();

    }
}