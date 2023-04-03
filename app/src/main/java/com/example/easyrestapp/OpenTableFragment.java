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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class OpenTableFragment extends Fragment {

    List<Dish> menu;
    List<Dish> filterMenu;
    List<TableDish> orderList;
    FragmentOpenTableBinding binding;
    MenuRecyclerAdapter menuAdapter;
    TableOrderRecyclerAdapter tableOrderAdapter;
    int currentTable;
    List<Table> tables;
    int totalAmount = 0;
    double numOfDiners = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        menu = new ArrayList<>();
        filterMenu = new ArrayList<>();
        orderList = new ArrayList<>();
        tables = new ArrayList<>();
        //menu
        for (int i=0;i<10;i++){
            menu.add(new Dish("Dish number " + Integer.toString(i), "Start", new ArrayList<>()));
        }
        for (int i=10;i<20;i++){
            menu.add(new Dish("Dish number " + Integer.toString(i), "Main", new ArrayList<>()));
        }
        for (int i=20;i<30;i++){
            menu.add(new Dish("Dish number " + Integer.toString(i), "Dessert", new ArrayList<>()));
        }
        //order list
        for (int i=0;i<2;i++){
            orderList.add(new TableDish(menu.get(i), "no comments yet"));
        }
        for (int i=10;i<12;i++){
            orderList.add(new TableDish(menu.get(i), "no comments yet"));
        }
        for (int i=20;i<22;i++){
            orderList.add(new TableDish(menu.get(i), "no comments yet"));
        }
       //tables
        for (int i=0;i<20;i++){
            tables.add(new Table(String.valueOf(i), "Note " + i, String.valueOf(i+1), i*10.0, (int)(i*10.0)/(i+1)));
        }

        binding = FragmentOpenTableBinding.inflate(inflater, container, false);
        View v=binding.getRoot();

        for (TableDish td: orderList){
            totalAmount += td.getPrice();
        }

        binding.menuList.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list
        setMenuAdapter(menuAdapter, menu);  //show all the dishes

        //filter dishes by type
        binding.openTableStartesBtn.setOnClickListener(V -> {
            filterMenu = menu.stream().filter(d -> d.type.equals("Start")).collect(Collectors.toList());
            setMenuAdapter(menuAdapter, filterMenu);
        });
        binding.openTableMainBtn.setOnClickListener(V -> {
            filterMenu = menu.stream().filter(d -> d.type.equals("Main")).collect(Collectors.toList());
            setMenuAdapter(menuAdapter, filterMenu);
        });
        binding.openTableDessertBtn.setOnClickListener(V -> {
            filterMenu = menu.stream().filter(d -> d.type.equals("Dessert")).collect(Collectors.toList());
            setMenuAdapter(menuAdapter, filterMenu);
        });

        binding.tableOrderList.setLayoutManager(new LinearLayoutManager(getContext()));  //define the recycler view to be a list
        tableOrderAdapter = new TableOrderRecyclerAdapter(getLayoutInflater(), orderList);
        binding.tableOrderList.setAdapter(tableOrderAdapter);

        binding.openTableTotalAmountTv.setText("Total amount: " + Double.toString(totalAmount) + " ₪");

        currentTable=OpenTableFragmentArgs.fromBundle(getArguments()).getChosenTable();
        numOfDiners = Double.valueOf(tables.get(currentTable).tDinners);
        binding.openTableAvgPerDinerTv.setText("Avg per diner: " + Double.toString(totalAmount/numOfDiners)  + " ₪");

        binding.openTableNumOfDinersTv.setText("Number of diners: " + tables.get(currentTable).tDinners);


        Log.d("tag"," "+currentTable);

        return v;
    }

    public void setMenuAdapter(MenuRecyclerAdapter adapter, List<Dish> l) {
        adapter = new MenuRecyclerAdapter(getLayoutInflater(),l);
        binding.menuList.setAdapter(adapter);
    }


    //--------------------- menu view holder ---------------------------
    class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView dishName;
        ImageView avatarImg;

        public MenuViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            dishName = itemView.findViewById(R.id.tableDishRow_dishName_tv);
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

    //--------------------- OnItemClickListener ---------------------------
    public interface OnItemClickListener{
        void onItemClick(int pos);
    }

    //--------------------- menu recycler adapter ---------------------------
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

    //--------------------- table order view holder ---------------------------
    class TableOrderViewHolder extends RecyclerView.ViewHolder {

        TextView dishType;
        TextView dishName;

        public TableOrderViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            dishName = itemView.findViewById(R.id.tableDishRow_dishName_tv);
            dishType = itemView.findViewById(R.id.tableDishRow_dishType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);
                }
            });
        }

        public void bind(TableDish td) {
            dishName.setText(td.getName());
            dishType.setText(td.getType());
        }
    }

    //--------------------- table order recycler adapter ---------------------------
    class TableOrderRecyclerAdapter extends RecyclerView.Adapter<TableOrderViewHolder>{
        OnItemClickListener listener;
        LayoutInflater inflater;
        List<TableDish> tableOrderList;

        public void setData(List<TableDish> data){
            this.tableOrderList = data;
            notifyDataSetChanged();
        }
        public TableOrderRecyclerAdapter(LayoutInflater inflater, List<TableDish> data){
            this.inflater = inflater;
            this.tableOrderList = data;
        }

        // Set the OnItemClickListener
        void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
        // Create a view holder
        @NonNull
        @Override
        public TableOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.table_dish_row,parent,false);
            return new TableOrderViewHolder(view,listener);
        }

        // Bind the data to the view holder
        @Override
        public void onBindViewHolder(@NonNull TableOrderViewHolder holder, int position) {
            TableDish dish = tableOrderList.get(position);
            holder.bind(dish);
        }

        // Return the number of items in the data
        @Override
        public int getItemCount() {
            if (tableOrderList == null) return 0;
            return tableOrderList.size();
        }
    }


}