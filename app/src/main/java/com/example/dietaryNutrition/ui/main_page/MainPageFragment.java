package com.example.dietaryNutrition.ui.main_page;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dietaryNutrition.Adapter.MyAdapter;
import com.example.dietaryNutrition.Interface.ILoadMore;
import com.example.dietaryNutrition.Model.Item;
import com.example.dietaryNutrition.R;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainPageFragment extends Fragment {

    private MainPageViewModel mainPageViewModel;

    List<Item> items = new ArrayList<>();
    MyAdapter adapter;

    private byte[] imageBytes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainPageViewModel =
                ViewModelProviders.of(this).get(MainPageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_main_page, container, false);

        for(int i = 0; i < 10; i++)
        {
            String name = UUID.randomUUID().toString();
            Item item = new Item(name,"asdasd", "Recipes/1.png");
            items.add(item);
        }

        RecyclerView recycler = root.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyAdapter(recycler, getActivity(),items);
        recycler.setAdapter(adapter);

        adapter.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                if(items.size()<=20){
                    items.add(null);
                    adapter.notifyItemInserted(items.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            items.remove(items.size()-1);
                            adapter.notifyItemRemoved(items.size());
                            int index = items.size();
                            int end = index+10;
                            for(int i = index; i < end; i++)
                            {
                                String name = UUID.randomUUID().toString();
                                Item item = new Item(name,"asdasd", "Recipes/1.png");
                                items.add(item);
                            }
                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();
                        }
                    },2000);
                }
                else
                {
                    Toast.makeText(getActivity(),"Load data completed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }
}