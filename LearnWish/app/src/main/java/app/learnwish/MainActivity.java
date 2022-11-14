package app.learnwish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button logoutBtn, addReqBtn, requestsBtn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        String value = null;
        if (extras != null) {
            value = extras.getString("key");
        }

        logoutBtn = findViewById(R.id.LogoutBtn);
        addReqBtn = findViewById(R.id.AddReqBtn);
        requestsBtn = findViewById(R.id.RequestsBtn);

        firebaseAuth = FirebaseAuth.getInstance();

        logoutBtn.setOnClickListener(view -> {
            firebaseAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

        String finalValue = value;
        addReqBtn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddServiceReqActivity.class).putExtra("key", finalValue));
        });

        requestsBtn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, RequestsActivity.class).putExtra("key", finalValue));
        });
    }

    protected void onStart() {
        super.onStart();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}