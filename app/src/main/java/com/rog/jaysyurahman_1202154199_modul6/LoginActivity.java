package com.rog.jaysyurahman_1202154199_modul6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //Deklarasi View
    @BindView(R.id.email) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
    TextInputEditText tvEmail;
    @BindView(R.id.password) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
    TextInputEditText tvPassword;
    @BindView(R.id.email_sign_in_button) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
    Button btnLogin;
    @BindView(R.id.tvSignUp) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
    TextView tvDaftar;
    private ProgressDialog dialogPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this); //Binding ButterKnife pada activity
        dialogPB = new ProgressDialog(this);
        tvDaftar.setOnClickListener(this);
    }

    @OnClick(R.id.email_sign_in_button)
    public void login() {
        //validasi kosong
        if(tvEmail.getText().toString().isEmpty()) {
            tvEmail.setError("Required");
            return;
        }

        //validasi kosong
        if(tvPassword.getText().toString().isEmpty()) {
            tvPassword.setError("Required");
            return;
        }

        dialogPB.setMessage("Mohon Tunggu Sebentar");
        dialogPB.setIndeterminate(true);
        dialogPB.show();
        loginProcess();
    }

    private void loginProcess() {
        final String str_email = tvEmail.getText().toString();
        final String str_password = tvPassword.getText().toString();

        //melakukan proses login
        Constant.mAuth.signInWithEmailAndPassword(str_email, str_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login berhasil
                            dialogPB.dismiss();
                            Log.d("", "signInWithEmail:success");
                            FirebaseUser curUser = Constant.mAuth.getCurrentUser(); //Mengambil informasi user yang login
                            Constant.currentUser = curUser;
                            startActivity(new Intent(LoginActivity.this, MainActivity.class)); //memanggil activity main
                            finish();
                        } else {
                            // Jika login gagal
                            Log.w("", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Akun Belum Terdaftar",
                                    Toast.LENGTH_SHORT).show();
                            dialogPB.dismiss();

                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSignUp:
                startActivity(new Intent(LoginActivity.this, DaftarActivity.class)); //memanggil activity register
                break;
        }
    }
}
