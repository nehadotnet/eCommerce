package com.example.ecommerce.view.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerce.R;
import com.example.ecommerce.utils.Utils;


public class AuthFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        AppCompatButton btnLogin = view.findViewById(R.id.btn_login);
        AppCompatButton btnSignUp = view.findViewById(R.id.btn_signup);

        // button listeners
        btnLogin.setOnClickListener(v -> Utils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fragment_container, new LoginFragment()));
        btnSignUp.setOnClickListener(v -> Utils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fragment_container, new SignUpFragment()));
    }
}