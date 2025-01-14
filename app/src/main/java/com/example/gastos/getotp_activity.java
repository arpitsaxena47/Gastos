package com.example.gastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class getotp_activity extends AppCompatActivity {

   TextView phone_num;
   EditText inputotp1, inputotp2, inputotp3, inputotp4, inputotp5,inputotp6;
   public String phonenumber_value;
   private FirebaseAuth mAuth;
   ImageView btnVerify;
    private TextView resendOTPTV;
    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken resendOTPtoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getotp_activity);

        //getting phone number
        phone_num=findViewById(R.id.pho_number);
         phonenumber_value = getIntent().getStringExtra("phone_number");
        phone_num.setText(phonenumber_value);
        mAuth = FirebaseAuth.getInstance();
         phonenumber_value="+91"+phonenumber_value;

         btnVerify = findViewById(R.id.go);
        //getting OTP
        inputotp1=findViewById(R.id.inputcode1);
        inputotp2=findViewById(R.id.inputcode2);
        inputotp3=findViewById(R.id.inputcode3);
        inputotp4=findViewById(R.id.inputcode4);
        inputotp5=findViewById(R.id.inputpin5);
        inputotp6=findViewById(R.id.inputcode6);
        resendOTPTV=findViewById(R.id.idTVResendOTP);


         setupOTPINPUTS();
        sendVerificationCode(phonenumber_value);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = inputotp1.getText().toString() + inputotp2.getText().toString() + inputotp3.getText().toString() + inputotp4.getText().toString() + inputotp5.getText().toString() + inputotp6.getText().toString();

                if(otp .length() == 6)
                verifyCode(otp);
                else
                    Toast.makeText(getotp_activity.this, "Enter Correct OTP", Toast.LENGTH_SHORT).show();
            }
        });
        resendOTPTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationCode(phonenumber_value, resendOTPtoken);
            }
        });
    }


    private void setupOTPINPUTS()
    {
        inputotp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty())
                {
                    inputotp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputotp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty())
                {
                    inputotp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputotp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty())
                {
                    inputotp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputotp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty())
                {
                    inputotp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputotp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty())
                {
                    inputotp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallBack,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(getotp_activity.this, step_pin_activity.class);
                            startActivity(i);
                            //Toast.makeText(VerifyOTPActivity.this, "User verified..", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getotp_activity.this, "Fail to verify the user..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                getotp_activity.this,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            resendOTPtoken = forceResendingToken;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                //otpEdt.setVisibility(View.INVISIBLE);
                inputotp1.setText(code.substring(0, 1));
                inputotp2.setText(code.substring(1, 2));
                inputotp3.setText(code.substring(2, 3));
                inputotp4.setText(code.substring(3, 4));
                inputotp5.setText(code.substring(4, 5));
                inputotp6.setText(code.substring(5, 6));
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.e("TAG", "ERROR IS " + e.getMessage());
            Toast.makeText(getotp_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

}


