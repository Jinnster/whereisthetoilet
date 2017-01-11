package com.example.jinhua.whereisthetoilet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private HashMap <Marker, MarkerInfo> markerInfoHashMap;
    private ArrayList <MarkerInfo> markerInfoArrayList = new ArrayList<MarkerInfo>();
    public String thisUrl = "http://www.hint.hr.nl";
    public MediaPlayer soundFlush;
    public final static String EXTRA_MESSAGE = "toilet app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();


        //Initialiseer HashMap for objecten
        markerInfoHashMap = new HashMap<Marker, MarkerInfo>();

        // Maak toilleten aan
        markerInfoArrayList.add(new MarkerInfo("Bijenkorf toilet", "icon", Double.parseDouble("51.920456"), Double.parseDouble("4.479179"), "Toilet at the third floor at De Bijenkorf."));
        markerInfoArrayList.add(new MarkerInfo("V&D toilet", "icon", Double.parseDouble("51.092923"), Double.parseDouble("4.52483"), "Toilet on the third floor at the V&D"));
        markerInfoArrayList.add(new MarkerInfo("KFC toilet", "icon", Double.parseDouble("51.917834"), Double.parseDouble("4.477216"), "Toilet at the KFC: Binnenwegplein"));
        markerInfoArrayList.add(new MarkerInfo("KFC toilet", "icon", Double.parseDouble("51.920277"), Double.parseDouble("4.468909"), "Toilet at the KFC: West Kruiskade"));
        markerInfoArrayList.add(new MarkerInfo("Breakaway Cafe toilet", "icon", Double.parseDouble("50.936180"), Double.parseDouble("5.968605"), "Toilet at Breakaway Cafe"));
        markerInfoArrayList.add(new MarkerInfo("KFC toilet", "icon", Double.parseDouble("51.919273"), Double.parseDouble("4.471488"), "Toilet at Thurston"));
        markerInfoArrayList.add(new MarkerInfo("MacDonald toilet", "icon", Double.parseDouble("51.919818"), Double.parseDouble("4.480531"), "Toilet at Koopgoot: inside C&A @ MacDonalds"));
        markerInfoArrayList.add(new MarkerInfo("NS toilet", "icon", Double.parseDouble("51.924285"), Double.parseDouble("4.469892"), "Toilet at Rotterdam Central Station"));
        markerInfoArrayList.add(new MarkerInfo("Zuidplein toilet", "icon", Double.parseDouble("51.889074"), Double.parseDouble("4.489961"), "Toilet at Zuidplein"));
        markerInfoArrayList.add(new MarkerInfo("Alexandrium toilet", "icon", Double.parseDouble("51.947910"), Double.parseDouble("4.555559"), "Toilet at Alexandrium mall"));

        plotMarker(markerInfoArrayList);

        // Getting reference to the SupportMapFragment of activity_main.xml
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // Getting GoogleMap object from the fragment
        mMap = fm.getMap();

        // Enabling MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if(location != null){
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(provider, 20000, 0, this);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                MarkerInfo markerInfo = markerInfoHashMap.get(marker);

                Intent intent = new Intent (MapsActivity.this, ClickedInfoActivity.class);

                String toiletName = markerInfo.getmLabel();
                String toiletDescription = markerInfo.getmDescription();

                intent.putExtra("description", toiletDescription);
                intent.putExtra("name", toiletName);

                startActivity(intent);

                //Toast.makeText(getBaseContext(), "Naam"+ markerInfo.getmLabel(), Toast.LENGTH_LONG).show();
                soundFlush = MediaPlayer.create(MapsActivity.this, R.raw.flush_sound);
                soundFlush.start();
            }
        });
    }

    @Override
    protected void onResume() {


        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));


    }

    /**
     * METHOD PLT MARKERS ON MAP
     */
    private void plotMarker(ArrayList<MarkerInfo> markerInfos){
        if(markerInfos.size() > 0)
        {
            for (MarkerInfo markerInfo : markerInfos)
            {
                //Create marker
                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(markerInfo.getmLat(), markerInfo.getmLng()));

                // markerIcon hier
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon));

                Marker currentMarker = mMap.addMarker(markerOptions);
                markerInfoHashMap.put(currentMarker, markerInfo);

                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {


                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View v = getLayoutInflater().inflate(R.layout.markerwindow, null);

                        MarkerInfo markerInfo = markerInfoHashMap.get(marker);

                        ImageView markerIcon = (ImageView)v.findViewById(R.id.marker_icon);

                        TextView markerLabel = (TextView) v.findViewById(R.id.marker_label);

                        markerIcon.setImageResource(manageMarkerIcon(markerInfo.getmIcon()));
                        markerLabel.setText(markerInfo.getmLabel());
                        return v;
                    }
                });
            }
        }

    }

    /**
     * Set correct icon
     */
    private int manageMarkerIcon(String markerIcon){
        if (markerIcon.equals("icon"))
            return R.drawable.icondefault;
        else
            return R.drawable.icon;
    }

    @Override
    public void onLocationChanged(Location location) {


        Button btn1 =  (Button)findViewById(R.id.btn_rotterdam);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRotterdam();
            }
        });

        TextView tvLocation = (TextView) findViewById(R.id.tv_location);

        // Getting long and lat
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Object
        LatLng latLng = new LatLng(latitude, longitude);

        // Show current location
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


        try {
            Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty()) {
                tvLocation.setText("No Location");
            } else {

                if (addresses.size() > 0) {

                    String FeatureName = addresses.get(0).getFeatureName();
                    String Locality = addresses.get(0).getLocality();
                    String PlaceName =  addresses.get(0).getAdminArea();
                    String countryName =  addresses.get(0).getCountryName();

                    if (Locality.equals("Rotterdam")) {
                        //tvLocation.setText(FeatureName + Locality + PlaceName + countryName);
                        btn1.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        //tvLocation.setText("Your location:"+ Locality+","+countryName);
                        tvLocation.setText("This is not Rotterdam! The public toilets are in Rotterdam.");
                        btn1.setVisibility(View.VISIBLE);

                    }
                    //Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void goRotterdam()
    {
        Double latitude = 51.924420;
        Double longitude = 4.477733;

        // Object
        LatLng latLng = new LatLng(latitude, longitude);

        // Show current location
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}

