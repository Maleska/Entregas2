package com.consulsoftware.entregas;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputLayout etUser,etPass;
    private Button btnlogin;
    private ImageButton config;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        etUser =  (TextInputLayout) findViewById(R.id.textInputLayout);
        etPass = (TextInputLayout) findViewById(R.id.textInputLayout2);
        config = (ImageButton) findViewById(R.id.ibConfig) ;

        btnlogin = (Button) findViewById(R.id.btnEntrar);

        // Initialize Firebase Auth
        //mAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(etUser.getEditText().getText().toString(),etPass.getEditText().getText().toString())
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent activityInicio = new Intent(MainActivity.this, InicioActivity.class);
                                    //activityInicio.putExtra("usuario", user.getEmail().toString());
                                    startActivity(activityInicio);

                                }else {

                                }
                            }
                        });
                    }
                });
        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityConfig = new Intent(MainActivity.this, ConfigActivity.class);
                startActivity(activityConfig);
            }
        });
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            Intent activityInicio = new Intent(MainActivity.this, InicioActivity.class);
            //activityInicio.putExtra("usuario", user.getEmail().toString());
            startActivity(activityInicio);
        }
    }

}