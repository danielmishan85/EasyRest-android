package com.example.easyrestapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyrestapp.databinding.FragmentOpenTableBinding;
import com.example.easyrestapp.model.Dish;
import com.example.easyrestapp.model.Model;
import com.example.easyrestapp.model.ServerConnection;
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
    double totalAmount = 0;
    int numOfDiners = 0;
    int chosenTableDish = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentOpenTableBinding.inflate(inflater, container, false);
        View v=binding.getRoot();
        currentTable=OpenTableFragmentArgs.fromBundle(getArguments()).getChosenTable();

        menu = Model.instance().getAllDishes();
        filterMenu = new ArrayList<>(); //filter menu by type
        tables = Model.instance().getAllOpenTables();
        orderList = Model.instance().getTableByNumber(String.valueOf(currentTable)).getOrderList();


        //calculate the amount of the specific table
        for (TableDish td: orderList){
            totalAmount += td.dish.getDishPrice();
        }


        //*********************************menu list *********************************************
        binding.menuRV.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list

        //the menu will open from starter
        binding.openTableBtn1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
        filterMenu = menu.stream().filter(d -> d.dishCategory.equals("starter")).collect(Collectors.toList());
        menuAdapter = new MenuRecyclerAdapter(getLayoutInflater(), filterMenu);  //show all the dishes
        binding.menuRV.setAdapter(menuAdapter);

        menuAdapter.setOnItemClickListener((int pos) -> {
            showOpenTableDishPopup(Model.instance().getTableByNumber(String.valueOf(currentTable)), menuAdapter.getData().get(pos));
            Log.d("chosenDish", Integer.toString(pos));
        });

        //filter dishes by type, by press button
        binding.openTableBtn4.setOnClickListener(V -> {
            menuButtonsColorReset();
            binding.openTableBtn4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
            filterMenu = menu.stream().filter(d -> d.dishCategory.equals("Fish and Meat")).collect(Collectors.toList());
            menuAdapter.setData(filterMenu);
        });
        binding.openTableBtn2.setOnClickListener(V -> {
            menuButtonsColorReset();
            binding.openTableBtn2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
            filterMenu = menu.stream().filter(d -> d.dishCategory.equals("Pasta")).collect(Collectors.toList());
            menuAdapter.setData(filterMenu);
        });
        binding.openTableBtn3.setOnClickListener(V -> {
            menuButtonsColorReset();
            binding.openTableBtn3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
            filterMenu = menu.stream().filter(d -> d.dishCategory.equals("Pizza")).collect(Collectors.toList());
            menuAdapter.setData(filterMenu);

        });
        binding.openTableBtn1.setOnClickListener(V -> {
            menuButtonsColorReset();
            binding.openTableBtn1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
            filterMenu = menu.stream().filter(d -> d.dishCategory.equals("starter")).collect(Collectors.toList());
            menuAdapter.setData(filterMenu);
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

        refreshAmountDetails();
        return v;
    }

    //**************************************total amount, avg per person, send reservation, fire, payment*******************
    public void refreshAmountDetails(){
        totalAmount=Model.instance().getTableByNumber(String.valueOf(currentTable)).getAvgPerPerson()*Model.instance().getTableByNumber(String.valueOf(currentTable)).getNumberOfPeople();
        numOfDiners = Model.instance().getTableByNumber(String.valueOf(currentTable)).getNumberOfPeople();

        binding.openTableTotalAmountTv.setText("Total amount: " + Double.toString(totalAmount)+ " ₪");
        double avgPerPerson = Model.instance().getTableByNumber(String.valueOf(currentTable)).getAvgPerPerson();
        String avgPerPersonStr = String.format("%.1f", avgPerPerson);
        binding.openTableAvgPerDinerTv.setText("Avg per diner: " + avgPerPersonStr + " ₪");

        binding.openTableNumOfDinersTv.setText("Number of diners: " + numOfDiners);

    }

    public void menuButtonsColorReset(){
        binding.openTableBtn1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        binding.openTableBtn2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        binding.openTableBtn3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        binding.openTableBtn4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

    }

    public void showPaymentPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.PinkAlertDialog);
        View paymentPopup = getLayoutInflater().inflate(R.layout.payment_popup, null);
        builder.setView(paymentPopup);


        // Initialize views
        EditText editTextPrice = paymentPopup.findViewById(R.id.payment_popup_editTextPrice);
        EditText editTextDiscount = paymentPopup.findViewById(R.id.payment_popup_editTextDiscount);
        EditText editTextService = paymentPopup.findViewById(R.id.payment_popup_editTextService);
        RadioGroup radioGroupDiscount = paymentPopup.findViewById(R.id.payment_popup_radioGroupDiscount);
        RadioGroup radioGroupService = paymentPopup.findViewById(R.id.payment_popup_radioGroupService);
        RadioButton radioButtonPercentage = paymentPopup.findViewById(R.id.payment_popup_radioButtonPercentage);
        RadioButton radioButtonAmount = paymentPopup.findViewById(R.id.payment_popup_radioButtonAmount);
        RadioButton radioButtonServicePercentage = paymentPopup.findViewById(R.id.payment_popup_radioButtonServicePercentage);
        RadioButton radioButtonServiceAmount = paymentPopup.findViewById(R.id.payment_popup_radioButtonServiceAmount);
        Button buttonCalculate = paymentPopup.findViewById(R.id.payment_popup_buttonCalculate);
        TextView textViewTotalPrice = paymentPopup.findViewById(R.id.payment_popup_textViewTotalPrice);


        Table t=Model.instance().getTableByNumber(String.valueOf(currentTable));
        if (currentTable!=0)
            editTextPrice.setText(t.getAvgPerPerson()*t.numberOfPeople+"");
        // Set click listener for the Calculate button
        buttonCalculate.setOnClickListener((v)-> {
            String priceStr = editTextPrice.getText().toString().trim();
            String discountStr = editTextDiscount.getText().toString().trim();
            String serviceStr = editTextService.getText().toString().trim();

            if (priceStr.isEmpty()) {
                Toast.makeText(MyApplication.getMyContext(), "Please enter the price.", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);
            double discount = discountStr.isEmpty() ? 0.0 : Double.parseDouble(discountStr);
            double service = serviceStr.isEmpty() ? 0.0 : Double.parseDouble(serviceStr);

            if (radioButtonPercentage.isChecked()) {
                discount = price * (discount / 100.0);
            }

            if (radioButtonServicePercentage.isChecked()) {
                service = price * (service / 100.0);
            }

            double totalPrice = price - discount + service;
            textViewTotalPrice.setText(String.format("Total Price: %.2f", totalPrice));

        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void showOpenTableDishPopup(Table table, Dish dish) {
        // Create the popup dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.PinkAlertDialog);
        View popupView = getLayoutInflater().inflate(R.layout.table_dish_popup, null);
        builder.setView(popupView);

        // Get references to the dish fields
        TextView dishName = popupView.findViewById(R.id.tableDish_name);
        TextView dishIngredients = popupView.findViewById(R.id.tableDish_ingredients);
        EditText tableComments = popupView.findViewById(R.id.tableDish_comments);
        EditText amountET=popupView.findViewById(R.id.table_dish_popup_amount);
        Button fOrM=popupView.findViewById(R.id.td_popup_ForM_btn);
        dishName.setText(dish.getDishName());
        StringBuilder sb=new StringBuilder("possible changes: \n" );
        for (String change: dish.possibleChanges)
            sb.append(change+ "\n");
        dishIngredients.setText(sb.toString());
//        tableComments.setHint("will be written the allergic of table... and we can add more comments");

        fOrM.setOnClickListener((v)->{
            if(fOrM.getText().toString().equals("F"))
                fOrM.setText("M");
            else
                fOrM.setText("F");
        });

        // Set up the buttons
        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Save the waiter input

                String comments="";
                comments=tableComments.getText().toString();
                int firstOrMain=0,amount=1;
                if(fOrM.getText().equals("M"))
                    firstOrMain=1;
                amount= Integer.parseInt(amountET.getText().toString());
                ArrayList<String> arrComments = new ArrayList<>();
                arrComments.add(comments);
                TableDish td = new TableDish(dish,amount,firstOrMain,true,arrComments);
                Model.instance().addDishToOrder(td,table.getId());

                tables = Model.instance().getAllOpenTables();
                orderList =Model.instance().getTableByNumber(String.valueOf(currentTable)).getOrderList();
                tableOrderAdapter.setData(orderList);
                binding.tableOrderRV.setAdapter(tableOrderAdapter);

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
        TableDish tableDish = orderList.get(dishIndex);

        // Create the popup dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.PinkAlertDialog);
        builder.setTitle("Comments for " + tableDish.dish.getDishName()); // Set the title of the popup dialog
        List<String> possibleChanges = tableDish.getComments();
        StringBuilder messageBuilder = new StringBuilder();
        for (String change : possibleChanges) {
            messageBuilder.append(change).append("\n");
        }
        builder.setMessage(messageBuilder.toString());

        //builder.setMessage(tableDish.dish.getPossibleChanges().get(0)); // Set the comments as the message of the popup dialog

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

        public MenuViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            dishName = itemView.findViewById(R.id.TableDishRow_dishName_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);
                }
            });
        }

        public void bind(Dish d) {
            dishName.setText(d.getDishName());
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

        public List<Dish> getData(){
            return this.menu;
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
            //Log.d("server", "dishName: " + dish.getDishName());
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
        Button orderFire;
        ImageButton dishComment;


        public TableOrderViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            dishName = itemView.findViewById(R.id.TableDishRow_dishName_tv);
            dishType = itemView.findViewById(R.id.tableDishRow_FirstOrMainBtb);
            dishDelete = itemView.findViewById(R.id.deleteDish_btn);
            dishComment = itemView.findViewById(R.id.commentsDish_btn);
            orderFire = itemView.findViewById(R.id.openTable_fire_btn);

            dishType.setOnClickListener(v->{
                if(dishType.getText().toString().equals("F")) {
                    dishType.setText("M");

                    //orderList.get(getAdapterPosition()).dish. = "M";
                }
                else{
                    dishType.setText("F");
                    //orderList.get(getAdapterPosition()).dish.type = "F";
                }
            });


            dishDelete.setOnClickListener(v->{
                orderList.remove(getAdapterPosition());
                setTableOrderAdapter(tableOrderAdapter, orderList);
            });

//            orderFire.setOnClickListener(v->{
//                //continue from here
//            });

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
            Log.d("server", "dishID: " + td.dish.getDishId());
            Dish d = Model.instance().getDishById(td.dish.getDishId());
            td.setDish(d);
            dishName.setText(td.dish.getDishName());
            //Log.d("server", "dishName: " + td.getFirstOrMain());

            if(td.getFirstOrMain() == 1)
                dishType.setText("F");
            else
                dishType.setText("M");


        }
    }

    //--------------------- table order recycler adapter ---------------------------
    class TableOrderRecyclerAdapter extends RecyclerView.Adapter<TableOrderViewHolder>{
        OnItemClickListener listener;
        LayoutInflater inflater;
        List<TableDish> tableOrderList;

        public void setData(List<TableDish> data){
            this.tableOrderList = data;
            refreshAmountDetails();
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