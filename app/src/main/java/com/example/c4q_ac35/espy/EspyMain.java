package com.example.c4q_ac35.espy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;


public class EspyMain extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String CLIENT_ID ="GHO15NRJ1DFJECCEPOPOC555Y1MKI23LPQQZHG04F2AG3FPJ";
    private static String client_Secret = "4CV4XEO03BPPLXSMOFVOB4KG14SSKQYGH20X3VN1RM5RLBRY";
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 900;
    FragmentPagerAdapter adapterViewPager;

    //Todo: merge Elvis code
    private static final String LOG_TAG = "MainActivity";
    private AutoCompleteTextView mAutocompleteTextView;
    private PlacesAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(40.498425, -74.250219), new LatLng(40.792266, -73.776434));
    private GoogleApiClient mGoogleApiClient;
    private static final int GOOGLE_API_CLIENT_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_back);

        mGoogleApiClient = new GoogleApiClient.Builder(EspyMain.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        ViewPager viewPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        viewPager.getCurrentItem();
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getApplicationContext(), "Selected page position: " + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //todo: Elvis Code
        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id.et_autocomplete_places);
        mAutocompleteTextView.setThreshold(2);

        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlacesAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);
        
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//         findPlaceById(mAutocompleteTextView.getText().toString());
//            Toast.makeText(getApplicationContext(), mAutocompleteTextView.getText(),Toast.LENGTH_LONG).show();
            //displayPlace();
//            LatLng latLng = new LatLng(latitude,longitude);
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//            googleMap.animateCamera(CameraUpdateFactory.zoomTo(11)); // choose default zoom of map

        }

    };

    class MyPagerAdapter extends FragmentPagerAdapter{
        private int NUM_ITEMS = 3;

        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
           switch(position){
               case 0:
                   return MyList.newInstance(0, "Page # 1");
               case 1:
                   return SearchResultsActivity.newInstance(1,"Page # 2");
               case 2:
                   return MapActivity.newInstance(2, "Page # 3");
               default:
                   return null;
           }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_espy_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent( EspyMain.this, SettingActivity.class);
            EspyMain.this.startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
