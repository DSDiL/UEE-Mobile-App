package app.learnwish;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<ServiceRequest> list = new ArrayList<>();

    public RecyclerViewAdapter(Context ctx) {
        this.context = ctx;
    }

    public void setItems(ArrayList<ServiceRequest> request) {
        list.addAll(request);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_items, parent, false);
        return new ServiceReqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ServiceReqViewHolder viewHolder = (ServiceReqViewHolder) holder;
        ServiceRequest request = list.get(position);
        viewHolder.description.setText(request.getDescription());
        viewHolder.service.setText(request.getService());

        viewHolder.moreBtn.setOnClickListener(view -> {
            Intent intent = new Intent(context, RequestDetailsActivity.class);
            intent.putExtra("Edits", request);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
