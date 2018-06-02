package com.noesdev.ade.numbers.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.noesdev.ade.numbers.MainActivity;
import com.noesdev.ade.numbers.R;
import com.noesdev.ade.numbers.AsyncTask.CallBackResult;
import com.noesdev.ade.numbers.AsyncTask.GetRes;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView tvHomeNumber, tvResHome, tvKeterangan;
    private Button btnToday;
    private int dateNumber, monthNumber;
    private ProgressBar progressBar;
    public static final String KEY_TODAY = "TODAY";


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        tvHomeNumber = view.findViewById(R.id.tv_homeNumber);
        tvResHome = view.findViewById(R.id.resHome);
        tvKeterangan = view.findViewById(R.id.tv_keterangan);


        btnToday = view.findViewById(R.id.btn_homeDate);

        progressBar = view.findViewById(R.id.p_bar);


        btnToday.setOnClickListener(this);
        getDate();

    }

    private void getDate() {
        progressBar.setVisibility(View.VISIBLE);
        dateNumber = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        monthNumber = Calendar.getInstance().get(Calendar.MONTH) + 1;

        tvKeterangan.setText("Now is ");
        tvHomeNumber.setText(toOrdinal());
        Log.e("tanggal", dateNumber + " " + monthNumber);
        new GetRes(dateNumber, monthNumber, KEY_TODAY, new CallBackResult() {
            @Override
            public void hasil(String text) {
                progressBar.setVisibility(View.GONE);
                tvResHome.setText(text);
                MainActivity.inHome=text;
                MainActivity.inHomeNo=dateNumber+"";

            }

            @Override
            public void nomor(String number) {

            }


        });

    }

    private String toOrdinal() {

        String[] ord = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (dateNumber % 100) {
            case 11:
            case 12:
            case 13:
                return dateNumber + " th";
            default: return dateNumber + ord[dateNumber%10];
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_homeDate: {
                Log.e("home", "date");
                getDate();
            }
            break;

        }

    }

}
