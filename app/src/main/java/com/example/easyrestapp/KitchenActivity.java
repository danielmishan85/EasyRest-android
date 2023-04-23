package com.example.easyrestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.easyrestapp.model.Model;
import com.example.easyrestapp.model.Table;
import com.example.easyrestapp.model.TableDish;

import java.util.List;

public class KitchenActivity extends AppCompatActivity {

    List<Table> tables;
    RecyclerView openTablesList;
    kitchenRecyclerAdapter kitchenAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        tables = Model.instance().getTables();
        openTablesList = findViewById(R.id.kitchen_tablesList);
        openTablesList.setLayoutManager(new GridLayoutManager(MyApplication.getMyContext(),3,GridLayoutManager.HORIZONTAL,false));  //define the recycler view to be a list
        kitchenAdapter = new kitchenRecyclerAdapter(getLayoutInflater(), tables);
        openTablesList.setAdapter(kitchenAdapter);
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
            tableNumber.setText("Table Number " + t.gettNum());
            orderList.setLayoutManager(new LinearLayoutManager(MyApplication.getMyContext()));  //define the recycler view to be a list
            tableOrderAdapter = new OrderRecyclerAdapter(getLayoutInflater(), t.orderList);
            orderList.setAdapter(tableOrderAdapter);
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

        public OrderViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            dishType = itemView.findViewById(R.id.kitchenGridRow_FirstOrMainBtb);
            dishName = itemView.findViewById(R.id.kitchenGridRow_dishName_tv);
            time = itemView.findViewById(R.id.kitchenGridRow_timeTV);
            checkBox = itemView.findViewById(R.id.kitchenGridRow_checkBox);


        }

        public void bind(TableDish td) {
            dishType.setText(td.dish.getType());
            dishName.setText(td.dish.getName());
            time.setText("no time yet");
        }
    }

    //--------------------- table's order recycler adapter ---------------------------
    class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderViewHolder>{
        OnItemClickListener listener;
        LayoutInflater inflater;
        List<TableDish> orderList;

        public void setData(List<TableDish> data){
            this.orderList = data;
            notifyDataSetChanged();
        }
        public OrderRecyclerAdapter(LayoutInflater inflater, List<TableDish> data){
            this.inflater = inflater;
            this.orderList = data;
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
            return new OrderViewHolder(view,listener);
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