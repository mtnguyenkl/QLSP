package com.example.ass_ad2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {
    private Button btnLogin;
    private TextInputLayout loName, loPass;
    private TextInputEditText ipName, ipPass;
    private TextView txtSignUp, txtForgotPass;
    DataBaseUser db;
    private CheckBox chkRemember;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "PrefsLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AnhXa();
        getSharedData();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginSQL();
            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Resgister.class));
            }
        });
    }

    private void LoginSQL() {
        String name = ipName.getText().toString();
        String pass = ipPass.getText().toString();

        boolean check = CheckEmptyLogin(name, pass);
        if (check) {
            boolean checkLogin = db.checkUserPass(name, pass);
            if (checkLogin) {
                if (chkRemember.isChecked()) {
                    sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", name);
                    editor.putString("pass", pass);
                    editor.putBoolean("check", chkRemember.isChecked());

                    editor.apply();
                    Toast.makeText(this, "You was remember your account!1!1!", Toast.LENGTH_SHORT).show();
                } else {
                    sharedPreferences.edit().clear().apply();
                }
                Toast.makeText(Login.this, "Sign in Sucsessfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, MainActivity.class));
            } else {
                Toast.makeText(Login.this, "Sign in failed", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean CheckEmptyLogin(String name, String pass) {
        if (name.isEmpty()) {
            loName.setError("Please enter field name");
            return false;
        } else {
            loName.setError(null);
        }
        if (pass.isEmpty()) {
            loPass.setError("Please enter field pass");
            return false;
        } else {
            loPass.setError(null);
        }
        return true;
    }


    public void getSharedData() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        if (sp.contains("username")) {
            String nameR = sp.getString("username", "Not found");
            ipName.setText(nameR);
        }
        if (sp.contains("pass")) {
            String passR = sp.getString("pass", "Not Founnd");
            ipPass.setText(passR);
        }
        if (sp.contains("check")) {
            Boolean checkR = sp.getBoolean("check", false);
            chkRemember.setChecked(checkR);
        }

    }

    public void AnhXa() {
        chkRemember = findViewById(R.id.chkRemember);
        txtForgotPass = findViewById(R.id.txtForgot);
        txtSignUp = findViewById(R.id.txtSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        ipName = findViewById(R.id.ipName);
        ipPass = findViewById(R.id.ipPass);
        loName = findViewById(R.id.loName);
        loPass = findViewById(R.id.loPass);

        db = new DataBaseUser(this);

    }

    public void ResetForm() {
        ipName.setText("");
        ipPass.setText("");
    }
}