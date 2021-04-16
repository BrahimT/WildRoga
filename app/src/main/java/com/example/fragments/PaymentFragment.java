package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tools.CheckoutActivityJava;
import com.example.myapplication.R;


public class PaymentFragment extends Fragment {
 Button payButton;


    public PaymentFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_payment, container, false);

        payButton=view.findViewById(R.id.payButton);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity().getApplicationContext(), CheckoutActivityJava.class);
                startActivity(intent);
            }
        });


        return view;
    }









}