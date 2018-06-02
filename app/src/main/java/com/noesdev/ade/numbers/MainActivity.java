package com.noesdev.ade.numbers;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.noesdev.ade.numbers.db.FavHelper;
import com.noesdev.ade.numbers.fragment.HomeFragment;
import com.noesdev.ade.numbers.fragment.InfoFragment;
import com.noesdev.ade.numbers.fragment.SearchFragment;

import java.util.ArrayList;
import java.util.List;

import static com.noesdev.ade.numbers.db.DBContract.CONTENT_URI;
import static com.noesdev.ade.numbers.db.DBContract.FavKolom.DESC_KOLOM;
import static com.noesdev.ade.numbers.db.DBContract.FavKolom.NOMOR_KOLOM;

public class MainActivity extends AppCompatActivity {

    public static String inSearch = "null";
    public static String inSearchNo = "null";
    public static String inHome;
    public static String inHomeNo;
    private ViewPager mPager;
    Toolbar toolbar;

    private FavHelper favHelper;
    public static final String NETWORK_ERROR = "NETWORK ERROR";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_info:
                    mPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_search:
                    mPager.setCurrentItem(0);
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String text = inHome;
        String nomor = inHomeNo;
        if (mPager.getCurrentItem() == 0) {
            if (inSearch.equals("null")) {
                Toast.makeText(this, "Select what you want to search !", Toast.LENGTH_SHORT).show();
            } else {
                text = inSearch;
                nomor = inSearchNo;

            }
        } else if (mPager.getCurrentItem() == 1) {
            text = inHome;
            nomor = inHomeNo;
        } else if (mPager.getCurrentItem()==2){
            https://drive.google.com/drive/folders/19DQW9cPwj2b-H68sUnXgirUjv2UZ7LpB?usp=sharing
            text = "Lets try this app for our knowledge about numbers !";
        }
            if (mPager.getCurrentItem() != 2) {
                if (id == R.id.action_save) {
//save
                    saveText(text, nomor);
                    Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show();
                }
            }
        if(id== R.id.action_share){
            if (mPager.getCurrentItem()==0 && inSearch.equals("null")){}else {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text +
                        "\n https://drive.google.com/drive/folders/19DQW9cPwj2b-H68sUnXgirUjv2UZ7LpB?usp=sharing");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Do you know ?");
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        }


        return super.onOptionsItemSelected(item);
    }

    private void saveText(String text, String nomor) {
        ContentValues values = new ContentValues();
        values.put(DESC_KOLOM, text);
        values.put(NOMOR_KOLOM, nomor);
        getContentResolver().insert(CONTENT_URI, values);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPager = findViewById(R.id.vp_main);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new SearchFragment(), "Search");
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new InfoFragment(), "Info");

        mPager.setAdapter(adapter);
        mPager.setCurrentItem(1);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);

        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
