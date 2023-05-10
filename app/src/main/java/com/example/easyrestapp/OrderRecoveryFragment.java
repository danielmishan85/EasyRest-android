package com.example.easyrestapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.easyrestapp.databinding.FragmentOrderRecoveryBinding;
import com.example.easyrestapp.model.Table;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;


public class OrderRecoveryFragment extends Fragment {

    FragmentOrderRecoveryBinding binding;
    OrderRecoveryRecyclerAdapter adapter;

    List<Table> lastOrders;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFC0CB'>Order Recovery</font>"));
        lastOrders=new ArrayList<>();
        for (int i=0;i<20;i++){
            //lastOrders.add(new Table(String.valueOf(i), "Note " + i, "Time " + i,String.valueOf(i+1), i*10.0, (int)(i*10.0)/(i+1)));
        }
        binding = FragmentOrderRecoveryBinding.inflate(inflater, container, false);
        View view =binding.getRoot();
        binding.orderRecoveryBtn.setOnClickListener(V->{
//            NavDirections action = OrderRecoveryFragmentDirections.actionOrderRecoveryFragmentToOrderRecoveryFragment();
//            Navigation.findNavController(getActivity(), R.id.main_navhost).navigate(action);
            //create the order again from DB
        });

        binding.lastOrdersRv.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list
        adapter = new OrderRecoveryRecyclerAdapter(getLayoutInflater(),lastOrders);
        binding.lastOrdersRv.setAdapter(adapter);

        adapter.setOnItemClickListener((int pos)-> {

            //in order to find the rec position in all rec list so that i can use the same recInfo frag

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

        public void bind(Table table) {
//            tNum.setText(table.tNum);
//            tNote.setText(table.tNote);
//            tTime.setText(table.tTime);
//            tDinners.setText(table.tDinners);
//            tTotal.setText(String.valueOf(table.tTotal));
//            tAvg.setText(String.valueOf( table.tAvg));
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
        List<Table> data;

        public void setData(List<Table> data){
            this.data = data;
            notifyDataSetChanged();
        }
        public OrderRecoveryRecyclerAdapter(LayoutInflater inflater, List<Table> data){
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
            Table re = data.get(position);
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

