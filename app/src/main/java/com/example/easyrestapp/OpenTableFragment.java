package com.example.easyrestapp;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyrestapp.databinding.FragmentOpenTableBinding;
import com.example.easyrestapp.model.Dish;
import com.example.easyrestapp.model.Model;
import com.example.easyrestapp.model.Table;
import com.example.easyrestapp.model.TableDish;

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
    int chosenTableDish = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentTable=OpenTableFragmentArgs.fromBundle(getArguments()).getChosenTable();
        menu = Model.instance().getMenu();
        filterMenu = new ArrayList<>(); //filter menu by type
        tables = Model.instance().getTables();
        orderList = tables.get(currentTable).getOrderList();

        binding = FragmentOpenTableBinding.inflate(inflater, container, false);
        View v=binding.getRoot();

        for (TableDish td: orderList){
            totalAmount += td.getPrice();
        }

        binding.menuList.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list
        //setMenuAdapter(menuAdapter, menu);  //show all the dishes
        menuAdapter = new MenuRecyclerAdapter(getLayoutInflater(), menu);
        binding.menuList.setAdapter(menuAdapter);

        menuAdapter.setOnItemClickListener((int pos) -> {
//            showOpenTableDishPopup(tables.get(currentTable), menu.get(pos));
            Log.d("chosenDish", Integer.toString(pos));
        });

        //filter dishes by type, by press button
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
        binding.openTableDrinksBtn.setOnClickListener(V -> {
            filterMenu = menu.stream().filter(d -> d.type.equals("Drink")).collect(Collectors.toList());
            setMenuAdapter(menuAdapter, filterMenu);
        });

        binding.tableOrderList.setLayoutManager(new LinearLayoutManager(getContext()));  //define the recycler view to be a list
        tableOrderAdapter = new TableOrderRecyclerAdapter(getLayoutInflater(), orderList);
        binding.tableOrderList.setAdapter(tableOrderAdapter);

        tableOrderAdapter.setOnItemClickListener((int pos) -> {
            chosenTableDish = pos;
            Log.d("chosenTableDish", Integer.toString(pos));
        });
        binding.openTableFirstBtn.setOnClickListener(V -> {
            orderList.get(chosenTableDish).type = "F";
            setTableOrderAdapter(tableOrderAdapter, orderList);
        });
        binding.openTableMainBtn2.setOnClickListener(V -> {
            orderList.get(chosenTableDish).type = "M";
            setTableOrderAdapter(tableOrderAdapter, orderList);
        });
        binding.openTableDeleteBtn.setOnClickListener(V -> {
            Model.instance().tables.get(currentTable).getOrderList().remove(chosenTableDish);
            orderList.remove(chosenTableDish);
            setTableOrderAdapter(tableOrderAdapter, orderList);
        });

        binding.openTableTotalAmountTv.setText("Total amount: " + Double.toString(totalAmount) + " ₪");

        numOfDiners = Double.valueOf(tables.get(currentTable).tDinners);
        binding.openTableAvgPerDinerTv.setText("Avg per diner: " + Double.toString(totalAmount/numOfDiners)  + " ₪");

        binding.openTableNumOfDinersTv.setText("Number of diners: " + tables.get(currentTable).tDinners);


        Log.d("tag"," "+currentTable);

        return v;
    }

    public void showOpenTableDishPopup(Table table, Dish dish) {
        // Create the popup dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.PinkAlertDialog);
        View popupView = getLayoutInflater().inflate(R.layout.table_dish_popup, null);
        builder.setView(popupView);

        // Get references to the dish fields
        ImageView dishImg = popupView.findViewById(R.id.tableDish_img);
        TextView dishName = popupView.findViewById(R.id.tableDish_name);
        TextView dishIngredients = popupView.findViewById(R.id.tableDish_ingredients);
        EditText tableComments = popupView.findViewById(R.id.tableDish_comments);
        dishImg.setImageResource(R.drawable.no_img);
        dishName.setText(dish.getName());
        dishIngredients.setText("will be written the dish ingredients...");
        tableComments.setText("will be written the allergic of table... and we can add more comments");


        // Set up the buttons
        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Save the waiter input
                String comments = tableComments.getText().toString();

                // Do something with the table dish input
                TableDish td = new TableDish(dish, comments);
                orderList.add(td);

            }
        });

        builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel the dialog
                dialog.dismiss();
            }
        });
    }

    public void setMenuAdapter(MenuRecyclerAdapter adapter, List<Dish> l) {
        adapter = new MenuRecyclerAdapter(getLayoutInflater(),l);
        binding.menuList.setAdapter(adapter);
    }

    public void setTableOrderAdapter(TableOrderRecyclerAdapter adapter, List<TableDish> l) {
        adapter = new TableOrderRecyclerAdapter(getLayoutInflater(),l);
        binding.tableOrderList.setAdapter(adapter);
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