package com.noesdev.ade.numbers.fragment;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.noesdev.ade.numbers.R;
import com.noesdev.ade.numbers.adapter.FavAdapter;

import static com.noesdev.ade.numbers.db.DBContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rcView;
    private ProgressBar progressBar;
    private Cursor list;
    private FavAdapter favAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcView = view.findViewById(R.id.rv_search);
        progressBar = view.findViewById(R.id.progress);
        swipeRefreshLayout = view.findViewById(R.id.swiper);
        favAdapter = new FavAdapter(getContext());
        //favAdapter.setListNotes(list);
        rcView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcView.setAdapter(favAdapter);
        new LoadFavAsync().execute();
        swipeRefreshLayout.setColorScheme(R.color.me,
                R.color.ji, R.color.ku,
                R.color.hi, R.color.bi,
                R.color.bibi, R.color.ni,
                R.color.ung);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new LoadFavAsync().execute();
            }
        }, 2000);

    }

    private class LoadFavAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getActivity().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            progressBar.setVisibility(View.GONE);

            list = cursor;
            favAdapter.setListNotes(list);
            Log.e("cursorGet", String.valueOf(cursor.getCount()));
            favAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
