package com.example.easyrestapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyrestapp.databinding.FragmentOpenTableBinding;
import com.example.easyrestapp.databinding.FragmentTablesBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class OpenTableFragment extends Fragment {

    List<Dish> menu;
    List<Dish> filterMenu;
    FragmentOpenTableBinding binding;
    MenuRecyclerAdapter adapter;
    int currentTable;

//    List<Table> tables;
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        tables=new ArrayList<>();
//        for (int i=0;i<20;i++){
//            tables.add(new Table(String.valueOf(i), "Note " + i, String.valueOf(i+1), i*10.0, (int)(i*10.0)/(i+1)));
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        menu = new ArrayList<>();
        filterMenu = new ArrayList<>();
        for (int i=0;i<10;i++){
            menu.add(new Dish("Dish number " + Integer.toString(i), "Start", new ArrayList<>()));
        }
        for (int i=10;i<20;i++){
            menu.add(new Dish("Dish number " + Integer.toString(i), "Main", new ArrayList<>()));
        }
        for (int i=20;i<30;i++){
            menu.add(new Dish("Dish number " + Integer.toString(i), "Dessert", new ArrayList<>()));
        }

        binding = FragmentOpenTableBinding.inflate(inflater, container, false);
        View v=binding.getRoot();

        binding.menuList.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list
        adapter = new MenuRecyclerAdapter(getLayoutInflater(),menu); //show all the dishes
        binding.menuList.setAdapter(adapter);

        //filter dishes by type
        binding.openTableStartesBtn.setOnClickListener(V -> {
            filterMenu = menu.stream().filter(d -> d.type.equals("Start")).collect(Collectors.toList());
            adapter = new MenuRecyclerAdapter(getLayoutInflater(),filterMenu);
            binding.menuList.setAdapter(adapter);
        });
        binding.openTableMainBtn.setOnClickListener(V -> {
            filterMenu = menu.stream().filter(d -> d.type.equals("Main")).collect(Collectors.toList());
            adapter = new MenuRecyclerAdapter(getLayoutInflater(),filterMenu);
            binding.menuList.setAdapter(adapter);
        });
        binding.openTableDessertBtn.setOnClickListener(V -> {
            filterMenu = menu.stream().filter(d -> d.type.equals("Dessert")).collect(Collectors.toList());
            adapter = new MenuRecyclerAdapter(getLayoutInflater(),filterMenu);
            binding.menuList.setAdapter(adapter);
        });

        currentTable=OpenTableFragmentArgs.fromBundle(getArguments()).getChosenTable();
        Log.d("tag"," "+currentTable);

        return v;
    }


    //--------------------- view holder ---------------------------
    class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView dishName;
        ImageView avatarImg;

        public MenuViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            dishName = itemView.findViewById(R.id.dishRow_dishName_tv);
            avatarImg = itemView.findViewById(R.id.dishRow_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);
                }
            });
        }

        public void bind(Dish d) {
            dishName.setText(d.getName());
            avatarImg.setImageResource(R.drawable.no_img);
        }
    }

    //---------------------OnItemClickListener ---------------------------
    public interface OnItemClickListener{
        void onItemClick(int pos);
    }

    //---------------------Recycler adapter ---------------------------
    class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuViewHolder>{
        OnItemClickListener listener;
        LayoutInflater inflater;
        List<Dish> menu;

        public void setData(List<Dish> data){
            this.menu = data;
            notifyDataSetChanged();
        }
        public MenuRecyclerAdapter(LayoutInflater inflater, List<Dish> data){
            this.inflater = inflater;
            this.menu = data;
        }

        // Set the OnItemClickListener
        void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
        // Create a view holder
        @NonNull
        @Override
        public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.dish_row,parent,false);
            return new MenuViewHolder(view,listener);
        }

        // Bind the data to the view holder
        @Override
        public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
            Dish dish = menu.get(position);
            holder.bind(dish);
        }

        // Return the number of items in the data
        @Override
        public int getItemCount() {
            if (menu == null) return 0;
            return menu.size();
        }
    }


}