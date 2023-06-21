package com.vn.tcshop.foodapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vn.tcshop.foodapp.R;

public class SpecificationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_specification, container, false);

        // Kiểm tra xem arguments có null hay không
        Bundle arguments = getArguments();
        if (arguments != null) {
            int productId = arguments.getInt("productId");
        }

        return rootView;
    }
}