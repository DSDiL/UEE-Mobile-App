package app.learnwish;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RequestDetailsActivity extends AppCompatActivity {

    Button providersBtn, updateBtn, deleteBtn;
    TextInputEditText service, position, location, method, description, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);

        email = findViewById(R.id.EmailAddress);
        service = findViewById(R.id.Service);
        position = findViewById(R.id.Position);
        location = findViewById(R.id.Location);
        method = findViewById(R.id.Method);
        description = findViewById(R.id.Description);
        providersBtn = findViewById(R.id.ProvidersBtn);
        updateBtn = findViewById(R.id.UpdateBtn);
        deleteBtn = findViewById(R.id.DeleteBtn);

        ServiceRequest request = (ServiceRequest) getIntent().getSerializableExtra("Edits");

        email.setText(request.getEmail());
        service.setText(request.getService());
        position.setText(request.getPosition());
        location.setText(request.getLocation());
        method.setText(request.getMethod());
        description.setText(request.getDescription());

        updateBtn.setOnClickListener(view -> {
            startActivity(new Intent(RequestDetailsActivity.this, UpdateServiceReqActivity.class).putExtra("Edits", request));
        });

        deleteBtn.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(RequestDetailsActivity.this);
            builder.setMessage("Do You Really Want to Delete This Service Request?");
            builder.setTitle("Alert!");
            builder.setCancelable(true);

            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {

                DAOLearnWish dao = new DAOLearnWish();

                dao.remove(request.getKey()).addOnSuccessListener(suc -> {

                    Toast.makeText(this, "Service Request is Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RequestDetailsActivity.this, RequestsActivity.class).putExtra("key", request.getEmail()));

                }).addOnFailureListener(er -> {
                    Toast.makeText(this, "Service Request Delete Failed" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            });

            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();;
            });

            AlertDialog alertDialog = builder.create();

            alertDialog.show();
        });
    }
}