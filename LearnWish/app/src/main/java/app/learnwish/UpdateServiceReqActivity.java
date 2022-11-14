package app.learnwish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class UpdateServiceReqActivity extends AppCompatActivity {

    Button saveBtn;
    TextInputEditText service, position, location, method, description, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_service_req);

        email = findViewById(R.id.EmailAddress);
        service = findViewById(R.id.Service);
        position = findViewById(R.id.Position);
        location = findViewById(R.id.Location);
        method = findViewById(R.id.Method);
        description = findViewById(R.id.Description);
        saveBtn = findViewById(R.id.SaveBtn);

        ServiceRequest request = (ServiceRequest) getIntent().getSerializableExtra("Edits");

        email.setText(request.getEmail());
        service.setText(request.getService());
        position.setText(request.getPosition());
        location.setText(request.getLocation());
        method.setText(request.getMethod());
        description.setText(request.getDescription());

        saveBtn.setOnClickListener(view -> {

            HashMap<String, Object> hashMap = new HashMap<>();

            String mail = email.getText().toString();
            String serv = service.getText().toString();
            String pos = position.getText().toString();
            String loc = location.getText().toString();
            String meth = method.getText().toString();
            String des = description.getText().toString();

            if (!mail.equals("") && !serv.equals("") && !pos.equals("") && !loc.equals("") && !meth.equals("") && !des.equals("")) {

                hashMap.put("email", mail);
                hashMap.put("service", serv);
                hashMap.put("position", pos);
                hashMap.put("location", loc);
                hashMap.put("method", meth);
                hashMap.put("description", des);

                DAOLearnWish dao = new DAOLearnWish();

                dao.update(request.getKey(), hashMap).addOnSuccessListener(suc -> {

                    Toast.makeText(this, "Service Request is Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateServiceReqActivity.this, RequestsActivity.class).putExtra("key", request.getEmail()));

                }).addOnFailureListener(er -> {
                    Toast.makeText(this, "Service Request Update is Failed" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            } else {
                Toast.makeText(this, "All Details are Required", Toast.LENGTH_SHORT).show();
            }
        });
    }
}