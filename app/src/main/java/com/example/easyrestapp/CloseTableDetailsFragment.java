package com.example.easyrestapp;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.easyrestapp.databinding.FragmentCloseTableDetailsBinding;
import com.example.easyrestapp.model.ClosedTable;
import com.example.easyrestapp.model.Dish;
import com.example.easyrestapp.model.Model;
import com.example.easyrestapp.model.TableDish;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CloseTableDetailsFragment extends Fragment {
    ArrayList<ClosedTable> lastOrders;
    List<TableDish> orderList;
    FragmentCloseTableDetailsBinding binding;

    TableOrderRecyclerAdapter adapter;
    int pos;
    ClosedTable ct;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        lastOrders= Model.instance().getAllClosedTables();
        binding = FragmentCloseTableDetailsBinding.inflate(inflater, container, false);
        View v=binding.getRoot();
        pos=CloseTableDetailsFragmentArgs.fromBundle(getArguments()).getPos();
        ct=lastOrders.get(pos);
        orderList=ct.getT().getOrderList();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list
        adapter=new TableOrderRecyclerAdapter((getLayoutInflater()), orderList);
        binding.recyclerView.setAdapter(adapter);

        String outputDateFormat = "yyyy-MM-dd,HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputDateFormat, Locale.getDefault());

        try {
            Date date = inputFormat.parse(ct.getT().getOpenTime());
            String outputDateString = outputFormat.format(date);
            binding.tableNumberTv.setText(ct.getT().getTableNumber()+ "");
            binding.tableOpenTimeTv.setText(outputDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }




        return  v;
    }



    //--------------------- tableDish order view holder ---------------------------
    class TableOrderViewHolder extends RecyclerView.ViewHolder {
        TextView dishName;
        Button dishType;
        ImageButton dishDelete;
        ImageButton dishComment;
        TextView amount;
        CheckBox cb;


        public TableOrderViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            dishName = itemView.findViewById(R.id.TableDishRow_dishName_tv);
            dishType = itemView.findViewById(R.id.tableDishRow_FirstOrMainBtb);
            dishDelete = itemView.findViewById(R.id.deleteDish_btn);
            dishComment = itemView.findViewById(R.id.commentsDish_btn);
            amount = itemView.findViewById(R.id.tableDishRow_amount_tv);
            cb=itemView.findViewById(R.id.table_dish_row_checkBox);



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
            td.setDish(d);
            dishName.setText(td.dish.getDishName());
            if(td.getFirstOrMain().equals("M"))
                dishType.setText("M");
            else
                dishType.setText("F");
            amount.setText("Amount: " + td.getAmount());
            dishDelete.setVisibility(View.GONE);
            cb.setVisibility(View.GONE);
            dishComment.setVisibility(View.GONE);
        }
    }


    public interface OnItemClickListener{
        void onItemClick(int pos);
    }
    //--------------------- tableDish order recycler adapter ---------------------------
    class TableOrderRecyclerAdapter extends RecyclerView.Adapter<TableOrderViewHolder> {
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
            return new TableOrderViewHolder(view, (OnItemClickListener) listener);
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

