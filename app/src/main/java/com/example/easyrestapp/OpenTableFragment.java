package com.example.easyrestapp;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

        binding = FragmentOpenTableBinding.inflate(inflater, container, false);
        View v=binding.getRoot();
        currentTable=OpenTableFragmentArgs.fromBundle(getArguments()).getChosenTable();

        menu = Model.instance().getMenu();
        filterMenu = new ArrayList<>(); //filter menu by type
        tables = Model.instance().getTables();
        orderList = tables.get(currentTable).getOrderList();

        //calculate the amount of the specific table
        for (TableDish td: orderList){
            totalAmount += td.dish.getPrice();
        }


        //*********************************menu list *********************************************
        binding.menuRV.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list
        menuAdapter = new MenuRecyclerAdapter(getLayoutInflater(), menu);  //show all the dishes
        binding.menuRV.setAdapter(menuAdapter);

        menuAdapter.setOnItemClickListener((int pos) -> {
            showOpenTableDishPopup(tables.get(currentTable), menu.get(pos));
            Log.d("chosenDish", Integer.toString(pos));
        });

        //filter dishes by type, by press button
        binding.openTableStartesBtn.setOnClickListener(V -> {
            menuButtonsColorReset();
            binding.openTableStartesBtn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
            filterMenu = menu.stream().filter(d -> d.type.equals("Start")).collect(Collectors.toList());
            setMenuAdapter(menuAdapter, filterMenu);
        });
        binding.openTableMainBtn.setOnClickListener(V -> {
            menuButtonsColorReset();
            binding.openTableMainBtn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
            filterMenu = menu.stream().filter(d -> d.type.equals("Main")).collect(Collectors.toList());
            setMenuAdapter(menuAdapter, filterMenu);
        });
        binding.openTableDessertBtn.setOnClickListener(V -> {
            menuButtonsColorReset();
            binding.openTableDessertBtn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
            filterMenu = menu.stream().filter(d -> d.type.equals("Dessert")).collect(Collectors.toList());
            setMenuAdapter(menuAdapter, filterMenu);

        });
        binding.openTableDrinksBtn.setOnClickListener(V -> {
            menuButtonsColorReset();
            binding.openTableDrinksBtn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
            filterMenu = menu.stream().filter(d -> d.type.equals("Drink")).collect(Collectors.toList());
            setMenuAdapter(menuAdapter, filterMenu);
        });



        //************************************** table orders list***********************************
        binding.tableOrderRV.setLayoutManager(new LinearLayoutManager(getContext()));  //define the recycler view to be a list
        tableOrderAdapter = new TableOrderRecyclerAdapter(getLayoutInflater(), orderList);
        binding.tableOrderRV.setAdapter(tableOrderAdapter);

        tableOrderAdapter.setOnItemClickListener((int pos) -> {
            chosenTableDish = pos;
            Log.d("chosenTableDish", Integer.toString(pos));
        });

        binding.openTablePaymentBtn.setOnClickListener(V -> {
            showPaymentPopup();
        });



        binding.openTableTotalAmountTv.setText("Total amount: " + Double.toString(totalAmount) + " ₪");

        numOfDiners = Double.valueOf(tables.get(currentTable).tDinners);
        binding.openTableAvgPerDinerTv.setText("Avg per diner: " + Double.toString(totalAmount/numOfDiners)  + " ₪");

        binding.openTableNumOfDinersTv.setText("Number of diners: " + tables.get(currentTable).tDinners);


        Log.d("tag"," "+currentTable);

        return v;
    }

    public void menuButtonsColorReset(){
        binding.openTableDrinksBtn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        binding.openTableStartesBtn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        binding.openTableDessertBtn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        binding.openTableMainBtn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

    }

    public void showPaymentPopup() {
        // Create the popup dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder = new AlertDialog.Builder(getContext(), R.style.PinkAlertDialog);
        View popupView = getLayoutInflater().inflate(R.layout.new_table_popup, null);
        builder.setView(popupView);

        // Get references to the user input fields
        EditText editText1 = popupView.findViewById(R.id.editText1);
        EditText editText2 = popupView.findViewById(R.id.editText2);
        EditText editText3 = popupView.findViewById(R.id.editText3);
        EditText editText4 = popupView.findViewById(R.id.editText4);
        //change the text when we have a real DB
        editText1.setText(totalAmount);
        editText2.setText("0");
        editText3.setText("0");
        editText4.setText("0");

        TextView tv1 = popupView.findViewById(R.id.textView);
        TextView tv2 = popupView.findViewById(R.id.textView7);
        TextView tv3 = popupView.findViewById(R.id.textView9);
        TextView tv4 = popupView.findViewById(R.id.textView10);
        tv1.setText("Amount: ");
        tv2.setText("Discount: ");
        tv3.setText("Service: ");
        double discount = Double.parseDouble(editText2.getText().toString()) / 100;
        tv4.setText("Total: ");
        double amount = Double.parseDouble(editText1.getText().toString());
        double tax = Double.parseDouble(editText3.getText().toString()) / 100;

        double totalAmount = amount * (1 - discount) * (1 + tax);
        editText4.setText(Double.toString(totalAmount));


        // Set up the buttons
        builder.setPositiveButton("Cash", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Save the user input
                String field1 = editText1.getText().toString();
                String field2 = editText2.getText().toString();
                String field3 = editText3.getText().toString();
                String field4 = editText4.getText().toString();

                // Do something with the user input
                // ...


            }
        });

        builder.setNeutralButton("Credit Card", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel the dialog
                dialog.dismiss();
            }
        });



        // Show the popup dialog
        builder.show();
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
//        tableComments.setHint("will be written the allergic of table... and we can add more comments");


        // Set up the buttons
        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Save the waiter input
                String comments = tableComments.getText().toString();

                // Do something with the table dish input
                TableDish td = new TableDish(dish, comments);
                orderList.add(td);
                setTableOrderAdapter(tableOrderAdapter, orderList);

            }
        });

        builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel the dialog
                dialog.dismiss();
            }
        });
        // Show the popup dialog
        builder.show();
    }

    private void showDishCommentsPopup(int dishIndex) {
        // Get the dish object from the orderList using the dishIndex
        TableDish dish = orderList.get(dishIndex);

        // Create the popup dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.PinkAlertDialog);
        builder.setTitle("Comments for " + dish.dish.getName()); // Set the title of the popup dialog
        builder.setMessage(dish.getComments()); // Set the comments as the message of the popup dialog

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog when "OK" is clicked
                dialog.dismiss();
            }
        });

        // Show the popup dialog
        builder.show();
    }




    public void setMenuAdapter(MenuRecyclerAdapter adapter, List<Dish> l) {
        adapter = new MenuRecyclerAdapter(getLayoutInflater(),l);
        binding.menuRV.setAdapter(adapter);
    }

    public void setTableOrderAdapter(TableOrderRecyclerAdapter adapter, List<TableDish> l) {
        adapter = new TableOrderRecyclerAdapter(getLayoutInflater(),l);
        binding.tableOrderRV.setAdapter(adapter);
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
        TextView dishName;
        Button dishType;
        ImageButton dishDelete;
        ImageButton dishFire;
        ImageButton dishComment;


        public TableOrderViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            dishName = itemView.findViewById(R.id.tableDishRow_dishName_tv);
            dishType = itemView.findViewById(R.id.FirstOrMainBtb);
            dishDelete = itemView.findViewById(R.id.deleteDish_btn);
            dishFire = itemView.findViewById(R.id.fireDish_btn);
            dishComment = itemView.findViewById(R.id.commentsDish_btn);


            dishType.setOnClickListener(v->{
                if(dishType.getText().toString().equals("F")) {
                    dishType.setText("M");
                    orderList.get(getAdapterPosition()).dish.type = "M";
                }
                else{
                    dishType.setText("F");
                    orderList.get(getAdapterPosition()).dish.type = "F";
                }
            });
            dishDelete.setOnClickListener(v->{
                orderList.remove(getAdapterPosition());
                setTableOrderAdapter(tableOrderAdapter, orderList);
            });

            dishFire.setOnClickListener(v->{
                //continue from here
            });

            dishComment.setOnClickListener(v->{
                //continue from here
                showDishCommentsPopup(getAdapterPosition());
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);
                }
            });
        }

        public void bind(TableDish td) {
            dishName.setText(td.dish.getName());
            dishType.setText(td.dish.getType());


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