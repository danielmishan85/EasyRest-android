package com.example.easyrestapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
            showDialogAndAddTable();

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



    public void showDialogAndAddTable() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.PinkAlertDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.new_table_popup, null);
        builder.setView(dialogView);

        EditText tableNumberEditText = dialogView.findViewById(R.id.newTable_popup_numTableEditText);
        EditText numberOfDinnerEditText = dialogView.findViewById(R.id.newTable_popup_numberOfPeopleEditText);
        EditText notesEditText = dialogView.findViewById(R.id.newTable_popup_notesEditText);
        EditText othersEditText = dialogView.findViewById(R.id.newTable_popup_othersEditText);
        CheckBox glutenCheckBox = dialogView.findViewById(R.id.newTable_popup_glutenCheckBox);
        CheckBox lactoseCheckBox = dialogView.findViewById(R.id.newTable_popup_lactoseCheckBox);
        CheckBox veganCheckBox = dialogView.findViewById(R.id.newTable_popup_veganCheckBox);
        CheckBox vegetarianCheckBox = dialogView.findViewById(R.id.newTable_popup_vegetarianCheckBox);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tableNumber = tableNumberEditText.getText().toString();
                String numberOfDinner = numberOfDinnerEditText.getText().toString();
                String notes = notesEditText.getText().toString();
                String others = othersEditText.getText().toString();
                boolean glutenFree = glutenCheckBox.isChecked();
                boolean lactoseFree = lactoseCheckBox.isChecked();
                boolean isVegan = veganCheckBox.isChecked();
                boolean isVegetarian = vegetarianCheckBox.isChecked();
                String restaurantName="EasyRest";

                // Call the addTable function with the obtained details
                Table t= new Table(tableNumber, Integer.parseInt(numberOfDinner),restaurantName, glutenFree, lactoseFree, isVegan, isVegetarian,others,notes);
                Model.instance().addNewTable(t);

                // Refresh the table data
                List<Table> tables = Model.instance().getAllOpenTables();
                adapter.setData(tables);

            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
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


        Table t=Model.instance().getTableByNumber(String.valueOf(chosenTable));
        if (chosenTable!=0)
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