package app.learnwish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

public class AddServiceReqActivity extends AppCompatActivity {

    Button addReqBtn;
    TextInputEditText service, position, location, method, description, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_req);

        Bundle extras = getIntent().getExtras();
        String value = null;
        if (extras != null) {
            value = extras.getString("key");
        }

        email = findViewById(R.id.EmailAddress);
        addReqBtn = findViewById(R.id.AddReqBtn);
        service = findViewById(R.id.Service);
        position = findViewById(R.id.Position);
        location = findViewById(R.id.Location);
        method = findViewById(R.id.Method);
        description = findViewById(R.id.Description);

        email.setText(value);

        addReqBtn.setOnClickListener(view -> {
            addRequest();
        });
    }

    private void addRequest() {

        String mail = email.getText().toString();
        String serv = service.getText().toString();
        String pos = position.getText().toString();
        String loc = location.getText().toString();
        String meth = method.getText().toString();
        String des = description.getText().toString();

        DAOLearnWish dao = new DAOLearnWish();

        if (!mail.equals("") && !serv.equals("") && !pos.equals("") && !loc.equals("") && !meth.equals("") && !des.equals("")) {

            ServiceRequest request = new ServiceRequest(mail, serv, pos, loc, meth, des);
            dao.add(request).addOnSuccessListener(suc -> {
                Toast.makeText(this, "Request is Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddServiceReqActivity.this, MainActivity.class).putExtra("key", mail));
            }).addOnFailureListener(er -> {
                Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "All Details are Required", Toast.LENGTH_SHORT).show();
        }


    }
}