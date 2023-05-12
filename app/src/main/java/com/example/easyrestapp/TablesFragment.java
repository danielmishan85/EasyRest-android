package com.example.easyrestapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyrestapp.databinding.FragmentTablesBinding;
import com.example.easyrestapp.model.Model;
import com.example.easyrestapp.model.Table;

import java.util.List;


public class TablesFragment extends Fragment {

    FragmentTablesBinding binding;
    TablesRecyclerAdapter adapter;
    List<Table> tables;
    int chosenTable;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFC0CB'>Tables</font>"));


        tables= Model.instance().getAllOpenTables();


        binding = FragmentTablesBinding.inflate(inflater, container, false);
        View v=binding.getRoot();

        binding.openTableBtn.setOnClickListener(V->{
            NavDirections action = TablesFragmentDirections.actionTablesFragmentToOpenTableFragment(chosenTable);
            Navigation.findNavController(getActivity(), R.id.main_navhost).navigate(action);
        });

        binding.newTableBtn.setOnClickListener(V->{
            showOpenTablePopup();
        });

        binding.orderRecoveryBtn.setOnClickListener(V->{
            NavDirections action = TablesFragmentDirections.actionTablesFragmentToOrderRecoveryFragment();
            Navigation.findNavController(getActivity(), R.id.main_navhost).navigate(action);
        });

        binding.paymentBtn.setOnClickListener(V->{
            showPaymentPopup();
        });


        binding.tablesRv.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list
        adapter = new TablesRecyclerAdapter(getLayoutInflater(),tables);
        binding.tablesRv.setAdapter(adapter);

        adapter.setOnItemClickListener((int pos)-> {
            List<Table> tables=Model.instance().getAllOpenTables();
            chosenTable=Integer.parseInt(tables.get(pos).getTableNumber());
            binding.chosenTableTv.setText("Chosen table: "+chosenTable);
        });


        return  v;
    }



    public void showOpenTablePopup() {
        // Create the popup dialog
        AlertDialog.Builder  builder = new AlertDialog.Builder(getContext(), R.style.PinkAlertDialog);
        View popupView = getLayoutInflater().inflate(R.layout.new_table_popup, null);
        builder.setView(popupView);

        // Get references to the user input fields
        EditText editText1 = popupView.findViewById(R.id.editText1);
        EditText editText2 = popupView.findViewById(R.id.editText2);
        EditText editText3 = popupView.findViewById(R.id.editText3);
        EditText editText4 = popupView.findViewById(R.id.editText4);

        // Set up the buttons
        builder.setPositiveButton("Open", new DialogInterface.OnClickListener() {
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

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel the dialog
                dialog.dismiss();
            }
        });



        // Show the popup dialog
        builder.show();
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
        editText1.setText("0");
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


    //--------------------- view holder ---------------------------
    class TablesViewHolder extends RecyclerView.ViewHolder {
        TextView   tNum,tNote,tDinners, tAvg,tTotal;

        public TablesViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tNum=itemView.findViewById(R.id.table_num);
            tNote=itemView.findViewById(R.id.notes_tv);
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
            tNum.setText(table.tableNumber);
            tNote.setText(table.getOthers());
            tDinners.setText(Integer.toString(table.numberOfPeople));
            tTotal.setText(String.valueOf(table.avgPerPerson*table.numberOfPeople));
            tAvg.setText(String.valueOf( table.getAvgPerPerson()));
        }
    }
    //---------------------OnItemClickListener ---------------------------
    public interface OnItemClickListener{
        void onItemClick(int pos);
    }



    //---------------------Recycler adapter ---------------------------
    class TablesRecyclerAdapter extends RecyclerView.Adapter<TablesViewHolder>{
        OnItemClickListener listener;
        LayoutInflater inflater;
        List<Table> data;

        public void setData(List<Table> data){
            this.data = data;
            notifyDataSetChanged();
        }
        public TablesRecyclerAdapter(LayoutInflater inflater, List<Table> data){
            this.inflater = inflater;
            this.data = data;
        }

        // Set the OnItemClickListener
        void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
        // Create a view holder
        @NonNull
        @Override
        public TablesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflate the row layout
            View view = getLayoutInflater().inflate(R.layout.table_row,parent,false);
            // Create and return a new AllRecommendationsViewHolder
            return new TablesViewHolder(view,listener);
        }

        // Bind the data to the view holder
        @Override
        public void onBindViewHolder(@NonNull TablesViewHolder holder, int position) {
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