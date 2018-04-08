package com.cheshmak.tazhan;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.AutoCompleteTextView;

import com.cheshmak.tazhan.Adapter.BillboardsAdapter;
import com.cheshmak.tazhan.Adapter.PlaceAutocompleteAdapter;
import com.cheshmak.tazhan.Adapter.ViewPagerAdapter;
import com.cheshmak.tazhan.Model.Billboard;
import com.cheshmak.tazhan.Utils.NetUtils;
import com.cheshmak.tazhan.Utils.Utils;
import com.cheshmak.tazhan.Utils.VolleySingleton;
import com.cheshmak.tazhan.cheshmak.R;

import java.util.ArrayList;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements NetUtils.Communicator {
    public static final String TAG = "tagg";
    private Toolbar toolbar;
    RecyclerView rvBillboards;

    BillboardsAdapter billboardsAdapter;
    SwipeRefreshLayout refreshBillboards;


    public static ArrayList billboards;


    private AppBarLayout appBar;
    private TabLayout tabLayout;
    private ViewPager viewPagerBillboards;
    private ViewPagerAdapter viewPagerAdapter;
    private MyNestedScrollView nestedscrollView;
    private AutoCompleteTextView etSearch;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    public static int currentPage;
    private FragmentBillboardsList fList;
    private FragmentBillboardsMap fMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.getCounties();


        viewPagerBillboards = findViewById(R.id.viewPagerBillboards);
        toolbar = findViewById(R.id.toolbarBillboards);
        appBar = findViewById(R.id.appBarBillboards);
        tabLayout = findViewById(R.id.tabLayout);
        etSearch = findViewById(R.id.etSearch);


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);


        viewPagerBillboards.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPagerBillboards);

        viewPagerBillboards.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //0 list
                //1 map
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        fList = (FragmentBillboardsList) viewPagerAdapter.getItem(0);
        fMap = (FragmentBillboardsMap) viewPagerAdapter.getItem(1);

//        toolbar.setLogo(R.drawable.ic_launcher_foreground);
        toolbar.inflateMenu(R.menu.menu_billboards);
        //        toolbar.setNavigationIcon(R.drawable.ic_launcher_foreground);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(null);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Utils.changeAppbarFont(toolbar, tabLayout, this);
        Utils.hideSoftKeyboard(this);


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_billboards, menu);
//        return true;
//    }

    @Override
    protected void onStop() {
        super.onStop();
        VolleySingleton.getInstance(this).cancelPendingRequests("billboard");
        VolleySingleton.getInstance(this).cancelPendingRequests("image");
    }


    @Override
    public void refreshMapFragment(ArrayList<Billboard> billboards) {
        if (null != fMap) {
            fMap.viewSerachedMarkers(billboards);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Realm.getDefaultInstance().close();

    }
}
