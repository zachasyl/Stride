package neu.edu.madcourse.strideapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.maps.android.SphericalUtil;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.Locale;


import neu.edu.madcourse.strideapp.databinding.ActivityMapsBinding;



// note that I use android's API instead of google's fusedlocation which we used in class
// simply because there was more information and I could not get fused to work.

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ArrayList<LatLng> routePoints;
    // this line added
    LocationManager locationManager;
    private Polyline line;
    private String firebaseIdentifier;
    private double totalDistance;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    double mileDistance;



    public void onClickSprint(View v) {
        // go to the record journey activity

        Intent Sprint = new Intent(MapsActivity.this, activityList.class);
        startActivity(Sprint);

    }

    public void onClickMyStats(View v) {
        // go to the activity for displaying journeys
        Intent MyStats = new Intent(MapsActivity.this, Statistics.class);
        startActivity(MyStats);
    }


    public void OnClickView(View v) {
        Intent MySprints = new Intent(MapsActivity.this, activityList.class);
        startActivity(MySprints);
    }



    public void onClickMyTrophies(View v) {
        Intent MyTrophies = new Intent(MapsActivity.this, neu.edu.madcourse.strideapp.TrophyCase.class);
        startActivity(MyTrophies);
    }

    public void onClickManual(View v) {
        Intent StartRun = new Intent(MapsActivity.this, neu.edu.madcourse.strideapp.Sprint.class);
        startActivity(StartRun);
    }


    public void onClickView(View v) {
        // go to the activity for displaying journeys
        Intent myView = new Intent(MapsActivity.this, activityList.class);
        startActivity(myView);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        routePoints = new ArrayList<LatLng>();

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // if permissions are not on, request permissions. In class
        // we did == packagemanager, main code, and then put the permisions in an else at the end.


//        fetchLocation();
    }



        private void fetchLocation() {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                         ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                return; //onRequestPermissionResult would be called and then it wil cal fetchlocaiton agan.
            } else { // we got the permissions
                //if the chronomoter is in the onclick function, it starts while app permissions is up.
                Chronometer simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer); // initiate a chronometer
                simpleChronometer.start(); // start a chronometer
                simpleChronometer.setBase(SystemClock.elapsedRealtime());


                // using GPS is the only viable option since we are constantly drawing a line.
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5,
                            5, new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            LatLng latLng = new LatLng(latitude, longitude);

                            PolylineOptions polylineOptions = new PolylineOptions()
                                    .width(4)
                                    .color(Color.GREEN)
                                    .geodesic(true);
                            double distanceMeters = 0;
                            for (int x = 0; x < routePoints.size(); x++) {
                                LatLng point = routePoints.get(x);
                                polylineOptions.add(point);
                                // x must be > 0 so we start at distance between first 2 points
                                if (x > 0) {
                                    LatLng startLatLng = routePoints.get(x - 1);
                                    LatLng endLatLng = routePoints.get(x);

//
                                    TextView textView = (TextView) findViewById(R.id.distanceText1);
                                    distanceMeters =
                                    distanceMeters +
                                            SphericalUtil.computeDistanceBetween(startLatLng, endLatLng);

                                    double distanceDouble =  Double.valueOf(distanceMeters);
                                    mileDistance = distanceDouble / 1609.344;
                                    mileDistance = Math.round(mileDistance*100.0)/100.0;
                                    textView.setText(String.valueOf(mileDistance));
                                }

                            }
                            line = mMap.addPolyline(polylineOptions);
                            routePoints.add(latLng);

                            // Geocoder class
                            Geocoder geocoder = new Geocoder(getApplicationContext());
                            try {

                                // only add marker and zoom in and label on first point; otherwise it will keep making points while you run.
                                if (routePoints.size() == 1) {
                                    List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                                    String str = addressList.get(0).getLocality() + " ,";
                                    str += addressList.get(0).getCountryName();
                                    mMap.addMarker(new MarkerOptions().position(latLng).title("str"));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f));

                        }

                    });
                }
            }

        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }


    public void onClickStartTimer(View v) {
        fetchLocation();
    }

    public void onStop(View v) {


        TextView distance = (TextView) findViewById(R.id.distanceText1);
        Chronometer simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer);

        // convert miliseconds to hours for miles per hour not the best...
        double timeDeltaFromStartInMs = (double) ((SystemClock.elapsedRealtime() - simpleChronometer.getBase())/ (3.6* Math.pow(10,6)));
        String time =  simpleChronometer.getText().toString();
        String distanceText = "10000";
                //distance.getText().toString();




        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("M/dd/yyyy", Locale.getDefault());
        String formattedDate = df.format(currentDate );

        FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                 firebaseIdentifier = task.getResult();
                DatabaseReference x =
                        FirebaseDatabase.getInstance().getReference().child(
                                String.valueOf(firebaseIdentifier)).child("Activities").push();




                // convert meters to miles

        //distance



       // min distance to prevent crash
//       if (mileDistance < 1){
//           mileDistance = 0.01;
//       }

                // miles per hour
        double speed = mileDistance / timeDeltaFromStartInMs;
        speed = Math.round(speed*100.0)/100.0;

        String mileDistanceString = Double.toString(mileDistance);



        x.child("activity").setValue("Walk/run");
        x.child("time").setValue(" " + time);
        x.child("distance").setValue(" " + mileDistanceString + " miles");
        x.child("speed").setValue(" " + Double.toString(speed) + " mph");
        x.child("date").setValue(formattedDate);
            }




        simpleChronometer.setBase(SystemClock.elapsedRealtime());
        simpleChronometer.stop();



        DatabaseReference totalDist = FirebaseDatabase.getInstance().getReference().child(String.valueOf(firebaseIdentifier)).child("Activities");
        totalDist.addValueEventListener(new ValueEventListener() {
            private ImageView mImageView;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Exercise exercise = dataSnapshot.getValue(Exercise.class);


                    if (exercise.getDistance() != null ) {

                        String x =exercise.getDistance();
                        // remove " miles"
                        try {
                            x = x.split(" ")[0];
                            Double doubleResult = Double.valueOf(x);
                            totalDistance += doubleResult;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String TotalDistance = "TotalDistance";
                        FirebaseDatabase.getInstance().getReference().child(firebaseIdentifier).child(TotalDistance).setValue(totalDistance);

                    }
                }



                if (totalDistance >= 250  ) {

                    FirebaseDatabase.getInstance().getReference().child(firebaseIdentifier).child("Trophies").child("Bronze").setValue(true);

                }
                if (totalDistance >= 500 ) {
                    FirebaseDatabase.getInstance().getReference().child(firebaseIdentifier).child("Trophies").child("Silver").setValue(true);

                }
                if (totalDistance >= 3000 ) {
                    FirebaseDatabase.getInstance().getReference().child(firebaseIdentifier).child("Trophies").child("Gold").setValue(true);


                }
                totalDistance = 0;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        try {
            DatabaseReference trop = database.getReference().child(firebaseIdentifier).child("Trophies");

            trop.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() != null) {
                        try {
                            if (snapshot.child("Bronze").getValue().toString() == "true" & snapshot.child("BronzeNotified").exists() == false) {

                                Toast.makeText(MapsActivity.this, "You've earned a Bronze Trophy!", Toast.LENGTH_SHORT).show();
                                FirebaseDatabase.getInstance().getReference().child(firebaseIdentifier).child("Trophies").child("BronzeNotified").setValue(true);

                            }


                            if (snapshot.child("Silver").getValue().toString() == "true" & snapshot.child("SilverNotified").exists() == false) {

                                Toast.makeText(MapsActivity.this, "You've earned a Silver Trophy!", Toast.LENGTH_SHORT).show();
                                FirebaseDatabase.getInstance().getReference().child(firebaseIdentifier).child("Trophies").child("SilverNotified").setValue(true);

                            }

                            if (snapshot.child("Gold").getValue().toString() == "true" & snapshot.child("GoldNotified").exists() == false) {

                                Toast.makeText(MapsActivity.this, "You've earned a Gold Trophy!", Toast.LENGTH_SHORT).show();
                                FirebaseDatabase.getInstance().getReference().child(firebaseIdentifier).child("Trophies").child("GoldNotified").setValue(true);

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        });
        Intent Return = new Intent(MapsActivity.this, activityList.class);
        startActivity(Return);



    }
    //    the only purpose of this right now is that  immediately when access is granted
//    for the first time, the user will not need to click the button again
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation();
            }
            else{

            }
        }

    }

}