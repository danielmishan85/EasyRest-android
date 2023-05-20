package com.example.easyrestapp;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.easyrestapp.databinding.FragmentOrderRecoveryBinding;
import com.example.easyrestapp.model.ClosedTable;
import com.example.easyrestapp.model.Model;
import com.example.easyrestapp.model.Table;
import com.example.easyrestapp.model.TableDish;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;


public class OrderRecoveryFragment extends Fragment {

    FragmentOrderRecoveryBinding binding;
    OrderRecoveryRecyclerAdapter adapter;

    List<ClosedTable> lastOrders;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFC0CB'>Order Recovery</font>"));
        lastOrders= Model.instance().getAllClosedTables();

        binding = FragmentOrderRecoveryBinding.inflate(inflater, container, false);
        View view =binding.getRoot();


        binding.lastOrdersRv.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list
        adapter = new OrderRecoveryRecyclerAdapter(getLayoutInflater(),lastOrders);
        binding.lastOrdersRv.setAdapter(adapter);

        adapter.setOnItemClickListener((int pos)-> {
            NavDirections action = OrderRecoveryFragmentDirections.actionOrderRecoveryFragmentToCloseTableDetailsFragment(pos);
            Navigation.findNavController(getActivity(), R.id.main_navhost).navigate(action);
            });



        return view;
    }





    //--------------------- view holder ---------------------------
    class OrderRecoveryViewHolder extends RecyclerView.ViewHolder {
        TextView tNum,tNote,tDinners, tAvg,tTotal,tTime;

        public OrderRecoveryViewHolder(@NonNull View itemView, OrderRecoveryFragment.OnItemClickListener listener) {
            super(itemView);
            tNum=itemView.findViewById(R.id.table_num);
            tNote=itemView.findViewById(R.id.notes_tv);
            tTime=itemView.findViewById(R.id.opentime_tv);
            tDinners=itemView.findViewById(R.id.dinners_num);
            tTotal=itemView.findViewById(R.id.amount_tv);
            tAvg=itemView.findViewById(R.id.avg_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);
                }
            });
        }

        public void bind(ClosedTable table) {
            tNum.setText(getAdapterPosition()+ "");
            tNote.setText(table.getT().getNotes());
            tDinners.setText(String.valueOf(table.getT().getNumberOfPeople()));
            tTotal.setText(String.format("%.1f",table.getT().getAvgPerPerson()*table.getT().getNumberOfPeople()));
            tAvg.setText(String.format("%.1f",table.getT().getAvgPerPerson()));
            String outputDateFormat = "yyyy-MM-dd,HH:mm";
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputDateFormat, Locale.getDefault());

            try {
                Date date = inputFormat.parse(table.getT().getOpenTime());
                String outputDateString = outputFormat.format(date);
                tTime.setText(outputDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    //---------------------OnItemClickListener ---------------------------
    public interface OnItemClickListener{
        void onItemClick(int pos);
    }



    //---------------------Recycler adapter ---------------------------
    class OrderRecoveryRecyclerAdapter extends RecyclerView.Adapter<OrderRecoveryFragment.OrderRecoveryViewHolder>{
        OrderRecoveryFragment.OnItemClickListener listener;
        LayoutInflater inflater;
        List<ClosedTable> data;

        public void setData(List<ClosedTable> data){
            this.data = data;
            notifyDataSetChanged();
        }
        public OrderRecoveryRecyclerAdapter(LayoutInflater inflater, List<ClosedTable> data){
            this.inflater = inflater;
            this.data = data;
        }

        // Set the OnItemClickListener
        void setOnItemClickListener(OrderRecoveryFragment.OnItemClickListener listener){
            this.listener = listener;
        }
        // Create a view holder
        @NonNull
        @Override
        public OrderRecoveryFragment.OrderRecoveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflate the row layout
            View view = getLayoutInflater().inflate(R.layout.table_with_opentime_row,parent,false);
            // Create and return a new AllRecommendationsViewHolder
            return new OrderRecoveryFragment.OrderRecoveryViewHolder(view,listener);
        }

        // Bind the data to the view holder
        @Override
        public void onBindViewHolder(@NonNull OrderRecoveryViewHolder holder, int position) {
            ClosedTable re = data.get(position);
            holder.bind(re);
        }

        // Return the number of items in the data
        @Override
        public int getItemCount() {
            if (data == null) return 0;
            return data.size();
        }
    }



}

