package com.example.eaglewallet.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.eaglewallet.R;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ProgressBar mealBar;
    TextView mealProgressText;
    TextView mealTotalText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //This is where backend will go for seeing how many meal plans are left
        mealBar = (ProgressBar)root.findViewById(R.id.progress_mealPlan);
        mealProgressText = (TextView)root.findViewById(R.id.text_progressMeal);
        mealTotalText = (TextView)root.findViewById(R.id.text_totalMeal);


        //Instead of setting text to progress of bar it will pull from API unless we pull from API for bar progress
        mealProgressText.setText(String.valueOf(mealBar.getProgress()));
        mealTotalText.setText((String.valueOf(mealBar.getMax())));



        return root;
    }
}