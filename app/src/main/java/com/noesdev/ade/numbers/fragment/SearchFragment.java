package com.noesdev.ade.numbers.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.noesdev.ade.numbers.AsyncTask.CallBackResult;
import com.noesdev.ade.numbers.AsyncTask.GetRes;
import com.noesdev.ade.numbers.MainActivity;
import com.noesdev.ade.numbers.R;

import java.util.Random;

import static com.noesdev.ade.numbers.MainActivity.NETWORK_ERROR;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private Spinner searchType, sPmonth;
    private EditText edtSearch;
    private Button btnSearch, btnRandom;
    private ProgressBar pBar;
    private TextView tvSearch;
    private String access;
    public static final String KEY_MATH = "MATH";
    public static final String KEY_TRIVIA = "TRIVIA";
    public static final String KEY_YEAR = "YEAR";
    public static final String KEY_DATE = "DATE";
    int nomborSearch, monthSearch;
    public static final String KEY_RANDOM = "RANDOM";

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchType = view.findViewById(R.id.sp_type);
        edtSearch = view.findViewById(R.id.edt_search);
        sPmonth = view.findViewById(R.id.sp_month);
        sPmonth.setVisibility(View.INVISIBLE);
        btnSearch = view.findViewById(R.id.btn_search);
        btnRandom = view.findViewById(R.id.btn_searchRandom);
        tvSearch = view.findViewById(R.id.search_res);
        pBar = view.findViewById(R.id.pg_bar);
        access = " ";
        pBar.setVisibility(View.GONE);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingSpinner();
                if (edtSearch.getText().toString().isEmpty() && searchType.getSelectedItemPosition() != 0) {
                    edtSearch.setError("This field can not be blank");
                    Log.e("itemPos", searchType.getSelectedItemPosition() + "");
                } else if (searchType.getSelectedItemPosition() == 0) {
                    Toast.makeText(getContext(), R.string.select_type, Toast.LENGTH_SHORT).show();
                } else if (searchType.getSelectedItemPosition()==4 && sPmonth.getSelectedItemPosition() == 0) {
                    Toast.makeText(getContext(), R.string.select_month, Toast.LENGTH_SHORT).show();

                } else {
                    nomborSearch = Integer.parseInt(edtSearch.getText().toString());
                    monthSearch = sPmonth.getSelectedItemPosition();
                    getresult();
                }

            }
        });
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingSpinner();
                if (searchType.getSelectedItemPosition() == 0) {
                    int coin = new Random().nextInt(3);
                    Log.e("coin", coin + "");
                    switch (coin) {
                        case 0: {
                            cekType(false);
                            randomNomor(10000);
                            access = KEY_RANDOM;
                        }
                        break;
                        case 1: {
                            cekType(false);
                            randomNomor(1000);
                            access = KEY_RANDOM;
                        }
                        break;
                        case 2: {
                            cekType(true);
                            randomDate();
                            access = KEY_DATE;
                        }
                        break;

                    }

                } else if (searchType.getSelectedItemPosition() == 4) {

                    randomDate();
                } else {
                    randomNomor(3000);
                }

                edtSearch.setText(String.valueOf(nomborSearch));
//                edtMonth.setText(String.valueOf(monthSearch));
                sPmonth.setSelection(monthSearch);
                Log.e("random", nomborSearch + " " + monthSearch);
                getresult();
            }
        });


        searchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 4) sPmonth.setVisibility(View.VISIBLE);
                else sPmonth.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void cekType(boolean isDate) {
        if (isDate) {
            sPmonth.setVisibility(View.VISIBLE);
        } else sPmonth.setVisibility(View.INVISIBLE);
    }

    private void randomNomor(int max) {
        int n = new Random().nextInt(max);
        nomborSearch = n;
    }

    private void randomDate() {
        int n = new Random().nextInt(31) + 1;
        int m = new Random().nextInt(12) + 1;

        nomborSearch = n;
        monthSearch = m;
    }

    private void getresult() {
        pBar.setVisibility(View.VISIBLE);
        btnRandom.setEnabled(false);
        btnSearch.setEnabled(false);
        Log.e("send", nomborSearch + " " + monthSearch);
        new GetRes(nomborSearch, monthSearch, access, new CallBackResult() {
            @Override
            public void hasil(String text) {
                tvSearch.setText(text);
                MainActivity.inSearch=text;
                MainActivity.inSearchNo=nomborSearch+"";
            }

            @Override
            public void nomor(String number) {
                //    edtSearch.setText(number);
                btnRandom.setEnabled(true);
                btnSearch.setEnabled(true);
                pBar.setVisibility(View.GONE);
            }


        });
       if (!tvSearch.getText().equals(NETWORK_ERROR)) {
           btnRandom.setEnabled(true);
           btnSearch.setEnabled(true);
           pBar.setVisibility(View.GONE);
       }
    }

    private void settingSpinner() {
        switch (searchType.getSelectedItemPosition()) {
            case 0: {

            }
            break;
            case 1: {
                access = KEY_MATH;
            }
            break;
            case 2: {
                access = KEY_TRIVIA;
            }
            break;
            case 3: {
                access = KEY_YEAR;
            }
            break;
            case 4: {
                access = KEY_DATE;
            }
            break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(getContext());
    }
}
