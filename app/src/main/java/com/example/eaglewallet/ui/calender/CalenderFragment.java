package com.example.eaglewallet.ui.calender;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.eaglewallet.R;

public class CalenderFragment extends Fragment {

    private CalenderViewModel calenderViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calenderViewModel =
                new ViewModelProvider(this).get(CalenderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calender, container, false);

        return root;
    }
}