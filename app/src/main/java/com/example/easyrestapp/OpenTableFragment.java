package com.example.easyrestapp;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyrestapp.databinding.FragmentOpenTableBinding;
import com.example.easyrestapp.model.Dish;
import com.example.easyrestapp.model.Drink;
import com.example.easyrestapp.model.Model;
import com.example.easyrestapp.model.Payment;
import com.example.easyrestapp.model.Table;
import com.example.easyrestapp.model.TableDish;
import com.example.easyrestapp.model.TableDrink;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


public class OpenTableFragment extends Fragment {

    List<Dish> menu;
    List<Drink> drinksMenu;
    List<Dish> filterMenu;
    List<TableDish> orderList;
    List<TableDrink> orderDrinkList;

    FragmentOpenTableBinding binding;
    MenuRecyclerAdapter menuAdapter;
    DrinkMenuRecyclerAdapter drinkMenuAdapter;
    TableOrderRecyclerAdapter tableOrderAdapter;
    TableDrinkRecyclerAdapter tableDrinkRecyclerAdapter;
    ExecutorService es;
    Table currentT;

    int currentTable;
    List<Table> tables;
    double totalAmount = 0;
    int numOfDiners = 0;
    int chosenTableDish = 0;
    double totalPrice,discount;
    TextView tableNum;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentOpenTableBinding.inflate(inflater, container, false);
        View v=binding.getRoot();
        currentTable=OpenTableFragmentArgs.fromBundle(getArguments()).getChosenTable();
        drinksMenu=Model.instance().getAllDrinks();
        menu = Model.instance().getAllDishes();
        filterMenu = new ArrayList<>(); //filter menu by type
        tables = Model.instance().getAllOpenTables();
        currentT = Model.instance().getTableByNumber(String.valueOf(currentTable));
        orderList = currentT.getOrderList();
        orderDrinkList=currentT.getDrinkArray();
        es= Executors.newSingleThreadExecutor();
        binding.refreshTv2.setText("Last refresh: " + getCurrentDateTime());
        binding.openTableTableNumTv.setText("Table number: "+currentTable);



        //*********************************menu list *********************************************
        binding.menuRV.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list

        //the menu will open from starter
        binding.openTableBtn1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
        filterMenu = menu.stream().filter(d -> d.dishCategory.equals("Starters")).collect(Collectors.toList());
        menuAdapter = new MenuRecyclerAdapter(getLayoutInflater(), filterMenu);  //show all the dishes
        drinkMenuAdapter = new DrinkMenuRecyclerAdapter(getLayoutInflater(), drinksMenu);  //show all the dishes
        tableDrinkRecyclerAdapter=new TableDrinkRecyclerAdapter((getLayoutInflater()), orderDrinkList);
        binding.menuRV.setAdapter(menuAdapter);

        menuAdapter.setOnItemClickListener((int pos) -> {
            showOpenTableDishPopup(currentT, menuAdapter.getData().get(pos));
        });

        drinkMenuAdapter.setOnItemClickListener((int pos)->{
            String comments="";
            ArrayList<String> arrComments = new ArrayList<>();
            arrComments.add(comments);
            Drink d= drinksMenu.get(pos);
            TableDrink td= new TableDrink( d,1, arrComments);
            Model.instance().addDrinkToOrder(td,currentT.getId());

            es.execute(()->{
                currentT = Model.instance().getTableByNumber(String.valueOf(currentTable));
                orderList = currentT.getOrderList();
                orderDrinkList =currentT.getDrinkArray();
                getActivity().runOnUiThread(() -> {
                    orderButtonsColorReset();
                    binding.openTableOrderBtnDrinks.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
                    tableDrinkRecyclerAdapter.setData(orderDrinkList);
                    binding.tableOrderRV.setAdapter(tableDrinkRecyclerAdapter);
                });
            });


        });

        binding.openTableOrderBtnFood.setOnClickListener((V)->{
            getActivity().runOnUiThread(() -> {
                orderButtonsColorReset();
                binding.openTableOrderBtnFood.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
                tableOrderAdapter.setData(orderList);
                binding.tableOrderRV.setAdapter(tableOrderAdapter);
            });

        });

        binding.refreshTableDishesIb.setOnClickListener(V -> {
            currentT = Model.instance().getTableByNumber(String.valueOf(currentTable));
            orderList = currentT.getOrderList();
            orderDrinkList=currentT.getDrinkArray();
            tableOrderAdapter.setData(orderList);
            tableDrinkRecyclerAdapter.setData(orderDrinkList);
            refreshAmountDetails();
            getSensitivityAndFire();
            binding.refreshTv2.setText("Last refresh: " + getCurrentDateTime());
        });

        binding.openTableOrderBtnDrinks.setOnClickListener((V)->{
            orderButtonsColorReset();
            binding.openTableOrderBtnDrinks.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
            tableDrinkRecyclerAdapter.setData(orderDrinkList);
            binding.tableOrderRV.setAdapter(tableDrinkRecyclerAdapter);
        });

        //filter dishes by type, by press button
        binding.openTableBtn4.setOnClickListener(V -> {
            menuButtonsColorReset();
            binding.openTableBtn4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
            filterMenu = menu.stream().filter(d -> d.dishCategory.equals("Fish-and-Meat")).collect(Collectors.toList());
            binding.menuRV.setAdapter(menuAdapter);
            menuAdapter.setData(filterMenu);
        });
        binding.openTableBtn2.setOnClickListener(V -> {
            menuButtonsColorReset();
            binding.openTableBtn2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
            filterMenu = menu.stream().filter(d -> d.dishCategory.equals("Pasta")).collect(Collectors.toList());
            binding.menuRV.setAdapter(menuAdapter);
            menuAdapter.setData(filterMenu);
        });
        binding.openTableBtn3.setOnClickListener(V -> {
            menuButtonsColorReset();
            binding.openTableBtn3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
            filterMenu = menu.stream().filter(d -> d.dishCategory.equals("Pizza")).collect(Collectors.toList());
            binding.menuRV.setAdapter(menuAdapter);
            menuAdapter.setData(filterMenu);

        });
        binding.openTableBtn1.setOnClickListener(V -> {
            menuButtonsColorReset();
            binding.openTableBtn1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
            filterMenu = menu.stream().filter(d -> d.dishCategory.equals("Starters")).collect(Collectors.toList());
            binding.menuRV.setAdapter(menuAdapter);
            menuAdapter.setData(filterMenu);
        });

        binding.openTableBtn.setOnClickListener(V -> {
            menuButtonsColorReset();
            binding.openTableBtn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
            binding.menuRV.setAdapter(drinkMenuAdapter);
            drinkMenuAdapter.setData(drinksMenu);
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
        getSensitivityAndFire();


        binding.sendResBtn.setOnClickListener((V)->{


            currentT.setOrderList(orderList);
            currentT.setDrinkArray(orderDrinkList);
            if(currentT.isFire()!=binding.fireCb.isChecked()) {
                currentT.setFire(binding.fireCb.isChecked());
                Model.instance().fire(currentT.getId());
            }
            currentT.setGluten(binding.glutenCb.isChecked());
            currentT.setLactose(binding.LactoseCb.isChecked());
            currentT.setVeggie(binding.isVegetarianCb.isChecked());
            currentT.setFire(binding.fireCb.isChecked());
            Model.instance().updateTable(currentT);
            //Table updatedTable = new Table();


        });


        return v;
    }
    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    public void getSensitivityAndFire(){
        Table t=Model.instance().getTableByNumber(String.valueOf(currentTable));
        binding.fireCb.setChecked(t.isFire());
        binding.glutenCb.setChecked(t.isGluten());
        binding.isVegetarianCb.setChecked(t.isVeggie());
        binding.LactoseCb.setChecked(t.isLactose());
    }

    //**************************************total amount, avg per person, send reservation, fire, payment*******************
    public void refreshAmountDetails(){

        currentT = Model.instance().getTableByNumber(String.valueOf(currentTable));
        binding.openTableTotalAmountTv.setText("Total amount: " + Double.toString(currentT.getTotalPrice())+ " ₪");
        double avgPerPerson = currentT.getAvgPerPerson();
        String avgPerPersonStr = String.format("%.1f", avgPerPerson);
        binding.openTableAvgPerDinerTv.setText("Avg per diner: " + avgPerPersonStr + " ₪");

        binding.openTableNumOfDinersTv.setText("Number of diners: " + currentT.getNumberOfPeople());

    }

    public void menuButtonsColorReset(){
        binding.openTableBtn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        binding.openTableBtn1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        binding.openTableBtn2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        binding.openTableBtn3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        binding.openTableBtn4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

    }

    public void orderButtonsColorReset(){
        binding.openTableOrderBtnFood.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        binding.openTableOrderBtnDrinks.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
    }

    public void showPaymentPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.PinkAlertDialog);
        View paymentPopup = getLayoutInflater().inflate(R.layout.payment_popup, null);
        builder.setView(paymentPopup);
        AtomicReference<Boolean> isCalc= new AtomicReference<>(false);
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

        if (currentTable!=0)
            editTextPrice.setText(currentT.getAvgPerPerson()*currentT.numberOfPeople+"");
        // Set click listener for the Calculate button
        buttonCalculate.setOnClickListener((v)-> {
            isCalc.set(true);
            String priceStr = editTextPrice.getText().toString().trim();
            String discountStr = editTextDiscount.getText().toString().trim();
            String serviceStr = editTextService.getText().toString().trim();

            if (priceStr.isEmpty()) {
                Toast.makeText(MyApplication.getMyContext(), "Please enter the price.", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);
            discount = discountStr.isEmpty() ? 0.0 : Double.parseDouble(discountStr);
            double service = serviceStr.isEmpty() ? 0.0 : Double.parseDouble(serviceStr);

            if (radioButtonPercentage.isChecked()) {
                discount = price * (discount / 100.0);
            }

            if (radioButtonServicePercentage.isChecked()) {
                service = price * (service / 100.0);
            }

             totalPrice = price - discount + service;
            textViewTotalPrice.setText(String.format("Total Price: %.2f", totalPrice));

        });

        builder.setPositiveButton("PAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(isCalc.get()){
                    Payment payment=new Payment("Card",totalPrice);
                    Model.instance().payment(currentT.getId(),payment,discount);
                    refreshAmountDetails();
                    Navigation.findNavController(getView()).popBackStack();

                }
                else{
                    Toast.makeText(MyApplication.getMyContext(), "Please click on calc first", Toast.LENGTH_LONG).show();

                }
            }
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
                int amount=1;
                amount= Integer.parseInt(amountET.getText().toString());
                ArrayList<String> arrComments = new ArrayList<>();
                arrComments.add(comments);
                TableDish td = new TableDish(dish,amount,fOrM.getText().toString(),true,arrComments);

                Model.instance().addDishToOrder(td,table.getId());


                es.execute(()->{
                    tables = Model.instance().getAllOpenTables();
                    currentT = Model.instance().getTableByNumber(String.valueOf(currentTable));
                    orderList =currentT.getOrderList();
                    getActivity().runOnUiThread(() -> {
                        tableOrderAdapter.setData(orderList);
                        binding.tableOrderRV.setAdapter(tableOrderAdapter);
                    });
                });


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

     public void showDishCommentsPopup(int dishIndex) {
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



    //--------------------- DrinkMenu view holder ---------------------------
    class DrinkMenuViewHolder extends RecyclerView.ViewHolder {

        TextView drinkName;

        public DrinkMenuViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            drinkName = itemView.findViewById(R.id.TableDishRow_dishName_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);
                }
            });
        }

        public void bind(Drink d) {
            drinkName.setText(d.getDrinkName());
        }
    }


    //--------------------- DrinkMenu recycler adapter ---------------------------
    class DrinkMenuRecyclerAdapter extends RecyclerView.Adapter<DrinkMenuViewHolder>{
        OnItemClickListener listener;
        LayoutInflater inflater;
        List<Drink> menu;

        public void setData(List<Drink> data){
            this.menu = data;
            notifyDataSetChanged();
        }

        public List<Drink> getData(){
            return this.menu;
        }
        public DrinkMenuRecyclerAdapter(LayoutInflater inflater, List<Drink> data){
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
        public DrinkMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.dish_row,parent,false);
            return new DrinkMenuViewHolder(view,listener);
        }

        // Bind the data to the view holder
        @Override
        public void onBindViewHolder(@NonNull DrinkMenuViewHolder holder, int position) {
            Drink drink = menu.get(position);
            //Log.d("server", "dishName: " + dish.getDishName());
            holder.bind(drink);
        }

        // Return the number of items in the data
        @Override
        public int getItemCount() {
            if (menu == null) return 0;
            return menu.size();
        }
    }




    //--------------------- TableDrink view holder ---------------------------
    class TableDrinkViewHolder extends RecyclerView.ViewHolder {

        TextView drinkName;
        Button drinkType;
        ImageButton drinkDelete;
        ImageButton drinkComment;
        TextView amount;

        public TableDrinkViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            drinkName = itemView.findViewById(R.id.TableDishRow_dishName_tv);
            drinkType = itemView.findViewById(R.id.tableDishRow_FirstOrMainBtb);
            drinkDelete = itemView.findViewById(R.id.deleteDish_btn);
            drinkComment = itemView.findViewById(R.id.commentsDish_btn);
            amount = itemView.findViewById(R.id.tableDishRow_amount_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);
                }
            });

            drinkDelete.setOnClickListener(v -> {
                double newTotal = currentT.totalPrice-orderDrinkList.get(getAdapterPosition()).getPrice();
                currentT.setLeftToPay(currentT.leftToPay-orderDrinkList.get(getAdapterPosition()).getPrice());
                currentT.setTotalPrice(newTotal);
                currentT.setAvgPerPerson(newTotal/currentT.getNumberOfPeople());
                orderDrinkList.remove(getAdapterPosition());
                tableDrinkRecyclerAdapter.setData(orderDrinkList);
                currentT.drinkArray=orderDrinkList;
                Model.instance().updateDishOrDrinkTable(currentT);
                refreshAmountDetails();
            });
        }




        public void bind(TableDrink d) {
            Drink drink = Model.instance().getDrinkById(d.getDrink().getId());
            drinkName.setText(drink.getDrinkName());
            drinkType.setVisibility(View.GONE);
            drinkComment.setVisibility(View.GONE);
            amount.setText("Amount: " + d.getAmount());

        }
    }


    //--------------------- TableDrink recycler adapter ---------------------------
    class TableDrinkRecyclerAdapter extends RecyclerView.Adapter<TableDrinkViewHolder>{
        OnItemClickListener listener;
        LayoutInflater inflater;
        List<TableDrink> menu;

        public void setData(List<TableDrink> data){
            this.menu = data;
            notifyDataSetChanged();
        }

        public List<TableDrink> getData(){
            return this.menu;
        }
        public TableDrinkRecyclerAdapter(LayoutInflater inflater, List<TableDrink> data){
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
        public TableDrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.table_dish_row,parent,false);
            return new TableDrinkViewHolder(view,listener);
        }

        // Bind the data to the view holder
        @Override
        public void onBindViewHolder(@NonNull TableDrinkViewHolder holder, int position) {
            TableDrink drink = menu.get(position);
            //Log.d("server", "dishName: " + dish.getDishName());
            holder.bind(drink);
        }

        // Return the number of items in the data
        @Override
        public int getItemCount() {
            if (menu == null) return 0;
            return menu.size();
        }
    }




    //--------------------- tableDish order view holder ---------------------------
    class TableOrderViewHolder extends RecyclerView.ViewHolder {
        TextView dishName;
        Button dishType;
        ImageButton dishDelete;
        CheckBox checkBox;
        ImageButton dishComment;
        TextView amount;


        public TableOrderViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            dishName = itemView.findViewById(R.id.TableDishRow_dishName_tv);
            dishType = itemView.findViewById(R.id.tableDishRow_FirstOrMainBtb);
            dishDelete = itemView.findViewById(R.id.deleteDish_btn);
            dishComment = itemView.findViewById(R.id.commentsDish_btn);
            amount = itemView.findViewById(R.id.tableDishRow_amount_tv);
            checkBox=itemView.findViewById(R.id.table_dish_row_checkBox);



            dishDelete.setOnClickListener(v -> {
                double newTotal = currentT.totalPrice-orderList.get(getAdapterPosition()).getPrice();
                currentT.setLeftToPay(currentT.leftToPay-orderList.get(getAdapterPosition()).getPrice());
                currentT.setTotalPrice(newTotal);
                currentT.setAvgPerPerson(newTotal/currentT.getNumberOfPeople());
                orderList.remove(getAdapterPosition());
                currentT.orderList=orderList;
                Model.instance().updateDishOrDrinkTable(currentT);
                tableOrderAdapter.setData(orderList);

            });


            dishComment.setOnClickListener(v -> {
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
            Dish d = Model.instance().getDishById(td.dish.getDishId());
            checkBox.setClickable(false);
            checkBox.setChecked(td.isReady());
            td.setDish(d);
            dishName.setText(td.dish.getDishName());
            if(td.getFirstOrMain().equals("M"))
                dishType.setText("M");
            else
                dishType.setText("F");
            amount.setText("Amount: " + td.getAmount());

        }
    }

    //--------------------- tableDish order recycler adapter ---------------------------
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