package com.example.easyrestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.easyrestapp.model.Dish;
import com.example.easyrestapp.model.Model;
import com.example.easyrestapp.model.Table;
import com.example.easyrestapp.model.TableDish;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KitchenActivity extends AppCompatActivity {

    List<Table> tables;
    RecyclerView openTablesList;
    kitchenRecyclerAdapter kitchenAdapter;
    ExecutorService es,es2;
    ImageButton refreshBtn;
    TextView refreshTv;
    ProgressBar pb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        openTablesList = findViewById(R.id.kitchen_tablesList);
        refreshBtn = findViewById(R.id.kitchen_refresh_btn);
        refreshTv = findViewById(R.id.kitchen_refresh_tv);
        pb = findViewById(R.id.pbKitchen);
        pb.setVisibility(View.GONE);


        es= Executors.newSingleThreadExecutor();
        es2= Executors.newSingleThreadExecutor();

        openTablesList.setLayoutManager(new GridLayoutManager(MyApplication.getMyContext(),2,GridLayoutManager.HORIZONTAL,false));  //define the recycler view to be a list
        es.execute(() -> {
            pb.setVisibility(View.VISIBLE);
            tables = Model.instance().getAllOpenTables();
            kitchenAdapter = new kitchenRecyclerAdapter(getLayoutInflater(), tables);
            runOnUiThread(() -> {
                openTablesList.setAdapter(kitchenAdapter);
            });
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tables = Model.instance().getAllOpenTables();

                kitchenAdapter.setData(tables);
                refreshTv.setText("Last refresh: " + getCurrentDateTime());
            }
        });

        refreshTv.setText("Last refresh: " + getCurrentDateTime());
    }

    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    //--------------------- kitchen view holder ---------------------------
    class kitchenViewHolder extends RecyclerView.ViewHolder {

        TextView tableNumber;
        RecyclerView orderList;
        OrderRecyclerAdapter tableOrderAdapter;

        public kitchenViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tableNumber = itemView.findViewById(R.id.kitchen_grid_tableNum);
            orderList = itemView.findViewById(R.id.kitchen_grid_orderList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);
                }
            });
        }

        public void bind(Table t) {
            tableNumber.setText("Table Number " + t.getTableNumber());
            es2.execute(()->{
                orderList.setLayoutManager(new LinearLayoutManager(MyApplication.getMyContext()));  //define the recycler view to be a list
                runOnUiThread(() -> {
                    tableOrderAdapter = new OrderRecyclerAdapter(getLayoutInflater(), t);
                    orderList.setAdapter(tableOrderAdapter);
                    pb.setVisibility(View.GONE);

                });
            });

        }
    }

    //--------------------- OnItemClickListener ---------------------------
    public interface OnItemClickListener{
        void onItemClick(int pos);
    }

    //--------------------- kitchen recycler adapter ---------------------------
    class kitchenRecyclerAdapter extends RecyclerView.Adapter<kitchenViewHolder>{
        OnItemClickListener listener;
        LayoutInflater inflater;
        List<Table> openTables;

        public void setData(List<Table> data){
            this.openTables = data;
            notifyDataSetChanged();
        }
        public kitchenRecyclerAdapter(LayoutInflater inflater, List<Table> data){
            this.inflater = inflater;
            this.openTables = data;
        }

        // Set the OnItemClickListener
        void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
        // Create a view holder
        @NonNull
        @Override
        public kitchenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.kitchen_grid,parent,false);
            return new kitchenViewHolder(view,listener);
        }

        // Bind the data to the view holder
        @Override
        public void onBindViewHolder(@NonNull kitchenViewHolder holder, int position) {
            Table table = openTables.get(position);
            holder.bind(table);
        }

        // Return the number of items in the data
        @Override
        public int getItemCount() {
            if (openTables == null) return 0;
            return openTables.size();
        }
    }

    //--------------------- table's order view holder ---------------------------
    class OrderViewHolder extends RecyclerView.ViewHolder {

        Button dishType;
        TextView dishName;
        TextView time;
        CheckBox checkBox;
        List<TableDish> orderList;


        public OrderViewHolder(@NonNull View itemView, OnItemClickListener listener,Table t) {
            super(itemView);
            dishType = itemView.findViewById(R.id.kitchenGridRow_FirstOrMainBtb);
            dishName = itemView.findViewById(R.id.kitchenGridRow_dishName_tv);
            time = itemView.findViewById(R.id.kitchenGridRow_timeTV);
            checkBox = itemView.findViewById(R.id.kitchenGridRow_checkBox);
            orderList = t.getOrderList();

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBox.isChecked())
                        orderList.get(getAdapterPosition()).setReady(true);
                    else
                        orderList.get(getAdapterPosition()).setReady(false);
                    t.orderList = orderList;

                    es.execute(() -> {
                        Model.instance().updateDishOrDrinkTable(t);
                        Model.instance().dishIsReady(t.orderList.get(getAdapterPosition()).getId());
                        Log.d("updated","updateDishOrDrinkTable succeed");
                        Log.d("updated","dishIsReady succeed");
                    });
                }
            });
        }



        public void bind(TableDish td) {
            Dish d = Model.instance().getDishById(td.dish.getDishId());
            td.setDish(d);
            dishType.setText(td.getFirstOrMain());
            dishName.setText(td.dish.getDishName());
            if(td.ready){
                checkBox.setChecked(true);
            }
            time.setText("no time yet");
        }
    }

    //--------------------- table's order recycler adapter ---------------------------
    class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderViewHolder>{
        OnItemClickListener listener;
        LayoutInflater inflater;
        Table currentTable;
        List<TableDish> orderList;

        public void setData(List<TableDish> data){
            this.orderList = data;
            notifyDataSetChanged();
        }
        public OrderRecyclerAdapter(LayoutInflater inflater, Table t){
            this.inflater = inflater;
            currentTable = t;
            this.orderList = t.getOrderList();
        }

        // Set the OnItemClickListener
        void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
        // Create a view holder
        @NonNull
        @Override
        public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.kitchen_grid_row,parent,false);
            return new OrderViewHolder(view,listener, currentTable);
        }

        // Bind the data to the view holder
        @Override
        public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
            TableDish td = orderList.get(position);
            holder.bind(td);
        }

        // Return the number of items in the data
        @Override
        public int getItemCount() {
            if (orderList == null) return 0;
            return orderList.size();
        }
    }
}