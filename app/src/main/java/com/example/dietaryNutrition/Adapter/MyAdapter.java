package com.example.dietaryNutrition.Adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dietaryNutrition.Interface.ILoadMore;
import com.example.dietaryNutrition.Model.Item;
import com.example.dietaryNutrition.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.List;

class LoadingViewHolder extends RecyclerView.ViewHolder
{
    public ProgressBar progressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressBar);
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView name, description;
    public ImageView image;

    public ItemViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.txtName);
        description = itemView.findViewById(R.id.txtDescription);
        image = itemView.findViewById(R.id.txtImage);
    }
}

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM=0,VIEW_TYPE_LOADING=1;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<Item> items;
    int visibleThreshold=5;
    int lastVisibleItem, totalItemCount;

    StorageReference storageReference;

    public MyAdapter(RecyclerView recyclerView, Activity activity, List<Item> items) {
        this.activity = activity;
        this.items = items;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                assert linearLayoutManager != null;
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= (lastVisibleItem+visibleThreshold)){
                    if(loadMore != null)
                        loadMore.onLoadMore();
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM)
        {
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_layout,parent,false);
            return new ItemViewHolder(view);
        }
        else if(viewType == VIEW_TYPE_LOADING)
        {
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_loading,parent,false);
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof  ItemViewHolder)
        {
            Item item = items.get(position);
            final ItemViewHolder viewHolder = (ItemViewHolder) holder;
            if(item != null) {

                viewHolder.name.setText(items.get(position).getName());
                viewHolder.description.setText(items.get(position).getDescription());
                storageReference = FirebaseStorage.getInstance().getReference("Images").child(items.get(position).getImageUri());
                final long ONE_MEGABYTE = 2048 * 2048;
                storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Data for "images/island.jpg" is returns, use this as needed
                        viewHolder.image.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }
        }
        else if(holder instanceof  LoadingViewHolder)
        {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setLoaded() {
        isLoading = false;
    }
}
