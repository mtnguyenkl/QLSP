package com.example.ass_ad2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Resgister extends AppCompatActivity {
    private Button btnRegister1;
    private ImageButton btnBack;
    private TextInputLayout loName1, loPass1, loPass2;
    private TextInputEditText ipName1, ipPass1, ipPass2;
    private DataBaseUser dataBaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgister);

        AnhXa();
        ResetForm();
        SignUpSQL();
        OnBackPress();
    }

    private void OnBackPress() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void SignUpSQL() {

        btnRegister1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ipName1.getText().toString();
                String pass1 = ipPass1.getText().toString();
                String pass2 = ipPass2.getText().toString();
                boolean check = CheckEmptySignUp(name, pass1, pass2);
                if (check) {
                    if (pass1.equals(pass2)) {
                        boolean checkUser = dataBaseUser.checkUser(name);
                        if (!checkUser) {
                            boolean insert = dataBaseUser.InsertData(name, pass1);
                            if (insert) {
                                ResetForm();
                                Toast.makeText(Resgister.this, "Registered Succesfully<3", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Resgister.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            ResetForm();
                            Toast.makeText(Resgister.this, "User already exist! please sign in<3", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ipPass2.setText("");
                        Toast.makeText(Resgister.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
    }

    private boolean CheckEmptySignUp(String name, String pass1, String pass2) {
        if (name.isEmpty()) {
            loName1.setError("Please enter field name");
            return false;
        } else {
            loName1.setError(null);
        }
        if (pass1.isEmpty()) {
            loPass1.setError("Please enter field pass");
            return false;
        } else {
            loPass1.setError(null);
        }
        if (pass2.isEmpty()) {
            loPass2.setError("Please enter field pass");
            return false;
        } else {
            loPass2.setError(null);
        }
        return true;
    }

    private void AnhXa() {
        btnRegister1 = findViewById(R.id.btnRegister1);
        btnBack = findViewById(R.id.btnBack);
        ipName1 = findViewById(R.id.ipName1);
        ipPass1 = findViewById(R.id.ipPass1);
        ipPass2 = findViewById(R.id.ipPass2);

        loName1 = findViewById(R.id.loName1);
        loPass1 = findViewById(R.id.loPass1);
        loPass2 = findViewById(R.id.loPass2);
        dataBaseUser = new DataBaseUser(this);
    }

    public void ResetForm() {
        ipName1.setText("");
        ipPass1.setText("");
        ipPass2.setText("");
    }
}