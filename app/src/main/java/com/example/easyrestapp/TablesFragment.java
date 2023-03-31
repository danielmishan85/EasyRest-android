package com.example.easyrestapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.easyrestapp.databinding.FragmentTablesBinding;


public class TablesFragment extends Fragment {

    FragmentTablesBinding binding;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("All Reviews");
        // Inflates the layout for this fragment and sets the title of the ActionBar
        binding = FragmentTablesBinding.inflate(inflater, container, false);
        View v=binding.getRoot();
        binding.openTableBtn.setOnClickListener(V->{
            NavDirections action = TablesFragmentDirections.actionTablesFragmentToOpenTableFragment();
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



        return  v;
    }



    public void showOpenTablePopup() {
        // Create the popup dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        View popupView = getLayoutInflater().inflate(R.layout.new_table_popup, null);
        builder.setView(popupView);

        // Get references to the user input fields
        EditText editText1 = popupView.findViewById(R.id.editText1);
        EditText editText2 = popupView.findViewById(R.id.editText2);
        EditText editText3 = popupView.findViewById(R.id.editText3);
        EditText editText4 = popupView.findViewById(R.id.editText4);
        TextView tv1 = popupView.findViewById(R.id.textView);
        TextView tv2 = popupView.findViewById(R.id.textView7);
        TextView tv3 = popupView.findViewById(R.id.textView9);
        TextView tv4 = popupView.findViewById(R.id.textView10);
        tv1.setText("Amount: ");
        tv2.setText("Discount: ");
        tv3.setText("Service: ");
        tv4.setText("Total: ");


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
}