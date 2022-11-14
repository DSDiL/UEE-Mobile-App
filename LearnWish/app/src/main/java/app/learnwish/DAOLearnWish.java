package app.learnwish;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DAOLearnWish {

    private DatabaseReference databaseReference;

    public DAOLearnWish() {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(ServiceRequest.class.getSimpleName());
    }

    public Task<Void> add(ServiceRequest request) {
        return databaseReference.push().setValue(request);
    }

    public Query get(String email) {
        return databaseReference.orderByChild("email").equalTo(email);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashmap) {
        return databaseReference.child(key).updateChildren(hashmap);
    }

    public Task<Void> remove(String key) {
        return databaseReference.child(key).removeValue();
    }
}
