package com.example.manpreetkaur.pet_treasure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pet_treasure_Model.Constants;
import pet_treasure_Model.DatabaseHelper;
import pet_treasure_Model.User;

public class Login_Activity extends AppCompatActivity {

    Button btnLogin;
    TextView newUser;
    EditText edUsername;     //1064
    EditText edPwd;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        newUser = (TextView)findViewById(R.id.txtNewMember);
        edUsername = (EditText)findViewById(R.id.edUsername);
        edPwd = (EditText)findViewById(R.id.edPwd);

        databaseHelper = new DatabaseHelper(this);



        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrationIntent = new Intent(Login_Activity.this, Registration_Activity.class);
                startActivity(registrationIntent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edUsername.getText().length() == 0){
                    edUsername.setError("FIELD CANNOT BE EMPTY");
                }
                else if (edPwd.getText().length() == 0){
                    edPwd.setError("FIELD CANNOT BE EMPTY");
                }
                else{
                    verifyFromSQLite();
                }


            }
        });

            }

    private void verifyFromSQLite() {




        Intent mainIntent2 = new Intent(Login_Activity.this, MainActivity.class);
        startActivity(mainIntent2);

        if (databaseHelper.checkUser(edUsername.getText().toString().trim()
                , edPwd.getText().toString().trim())) {


            Intent mainIntent = new Intent(Login_Activity.this, MainActivity.class);
            startActivity(mainIntent);

            Toast.makeText(this,"login Successfull", Toast.LENGTH_SHORT).show();

        } else {
            // Snack Bar to show success message that record is wrong
            //Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
            Toast.makeText(this,"invalid information", Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent(Login_Activity.this, Login_Activity.class);
            startActivity(mainIntent);

        }
    }


}
