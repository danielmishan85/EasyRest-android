package com.example.easyrestapp;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyrestapp.model.Dish;
import com.example.easyrestapp.model.Model;
import com.example.easyrestapp.model.Table;
import com.example.easyrestapp.model.TableDish;

import java.util.List;

public class KitchenActivity extends AppCompatActivity {

    RecyclerView recyclerViewOutside;
    KitchenRecyclerAdapter kra;
    List<Table> tables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        tables=Model.instance().getTables();
        recyclerViewOutside = this.findViewById(R.id.kitchen_rv);
        recyclerViewOutside.setLayoutManager(new GridLayoutManager(MyApplication.getMyContext(),2));  //define the recycler view to be a list
        kra = new KitchenRecyclerAdapter(getLayoutInflater(), tables);
        recyclerViewOutside.setAdapter(kra);





    }

    //--------------------- menu view holder ---------------------------
    class KitchenViewHolder extends RecyclerView.ViewHolder {

        TextView tableNumber;
        RecyclerView rv;
        List<Table> tables;
        List<TableDish> orderList;
        KitchenTableDishRecyclerAdapter insideTableOrderAdapter;


        public KitchenViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tableNumber = itemView.findViewById(R.id.Kitchen_table_num_tv);
            rv = itemView.findViewById(R.id.rv_grid);

        }

        public void bind(Table t) {
            tableNumber.setText(t.gettNum());
            tables = Model.instance().getTables();
            orderList = tables.get(Integer.getInteger(tableNumber.toString())).getOrderList();
            rv.setLayoutManager(new LinearLayoutManager(MyApplication.getMyContext()));  //define the recycler view to be a list
            insideTableOrderAdapter = new KitchenTableDishRecyclerAdapter(getLayoutInflater(),  tables.get(Integer.getInteger(tableNumber.toString())).getOrderList());
            rv.setAdapter(insideTableOrderAdapter);
        }
    }

    //--------------------- OnItemClickListener ---------------------------
    public interface OnItemClickListener{
        void onItemClick(int pos);
    }

    //--------------------- menu recycler adapter ---------------------------
    class KitchenRecyclerAdapter extends RecyclerView.Adapter<KitchenViewHolder>{
        OnItemClickListener listener;
        LayoutInflater inflater;
        List<Table> tables;

        public void setData(List<Table> data){
            this.tables = data;
            notifyDataSetChanged();
        }
        public KitchenRecyclerAdapter(LayoutInflater inflater, List<Table> data){
            this.inflater = inflater;
            this.tables = data;
        }

        // Set the OnItemClickListener
        void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
        // Create a view holder
        @NonNull
        @Override
        public KitchenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.kitchen_grid,parent,false);
            return new KitchenViewHolder(view,listener);
        }

        // Bind the data to the view holder
        @Override
        public void onBindViewHolder(@NonNull KitchenViewHolder holder, int position) {
            Table t = tables.get(position);
            holder.bind(t);
        }

        // Return the number of items in the data
        @Override
        public int getItemCount() {
            if (tables == null) return 0;
            return tables.size();
        }
    }



//***************************************************************************INSIDE LIST*****************************************
//tableDish
    //--------------------- menu view holder ---------------------------
    class KitchenTableDishViewHolder extends RecyclerView.ViewHolder {

    TextView dishName;
    Button dishType;
    CheckBox cb;
    TextView time;


    public KitchenTableDishViewHolder(@NonNull View itemView, OnItemClickListener2 listener) {
        super(itemView);
        dishName = itemView.findViewById(R.id.kitchenGridRow_dishName_tv);
        dishType = itemView.findViewById(R.id.kitchenGridRow_FirstOrMainBtb);
        cb= itemView.findViewById(R.id.kitchenGridRow_checkBox);
        time= itemView.findViewById(R.id.kitchenGridRow_timeTV);

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

    //--------------------- OnItemClickListener ---------------------------
    public interface OnItemClickListener2{
        void onItemClick(int pos);
    }

    //--------------------- menu recycler adapter ---------------------------
    class KitchenTableDishRecyclerAdapter extends RecyclerView.Adapter<KitchenTableDishViewHolder>{
        OnItemClickListener2 listener;
        LayoutInflater inflater;
        List<TableDish> tableDishes;

        public void setData(List<TableDish> data){
            this.tableDishes = data;
            notifyDataSetChanged();
        }
        public KitchenTableDishRecyclerAdapter(LayoutInflater inflater, List<TableDish> data){
            this.inflater = inflater;
            this.tableDishes = data;
        }

        // Set the OnItemClickListener
        void setOnItemClickListener(OnItemClickListener2 listener){
            this.listener = listener;
        }
        // Create a view holder
        @NonNull
        @Override
        public KitchenTableDishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.kitchen_grid_row,parent,false);
            return new KitchenTableDishViewHolder(view,listener);
        }

        // Bind the data to the view holder
        @Override
        public void onBindViewHolder(@NonNull KitchenTableDishViewHolder holder, int position) {
            TableDish t = tableDishes.get(position);
            holder.bind(t);
        }

        // Return the number of items in the data
        @Override
        public int getItemCount() {
            if (tableDishes == null) return 0;
            return tableDishes.size();
        }
    }

}