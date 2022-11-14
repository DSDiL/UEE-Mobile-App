package app.learnwish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText emailAddress, password;
    TextView register, forgotPassword;
    Button loginBtn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailAddress = findViewById(R.id.EmailAddress);
        password = findViewById(R.id.Password);
        register = findViewById(R.id.Register);
        forgotPassword = findViewById(R.id.ForgotPassword);
        loginBtn = findViewById(R.id.LoginBtn);

        firebaseAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(view -> {
            userLogin();
        });

        register.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        forgotPassword.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        });
    }

    private void userLogin() {
        String e = emailAddress.getText().toString();
        String p = password.getText().toString();

        if (TextUtils.isEmpty(e)) {
            emailAddress.setError("Email Cannot be Empty");
            emailAddress.requestFocus();
        } else if (TextUtils.isEmpty(p)) {
            password.setError("Password Cannot be Empty");
            password.requestFocus();
        } else {
            firebaseAuth.signInWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("key", e));
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}