package neu.edu.madcourse.strideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.FirebaseInstallations;

public class Sprint extends AppCompatActivity {
    private EditText dateEntry;
    private EditText idEntry;
    private EditText distanceEntry;
    private EditText timeEntry;
    private EditText activityEntry;
    private String firebaseIdentifier;
    private double totalDistance;



    final FirebaseDatabase database = FirebaseDatabase.getInstance();


    private FloatingActionButton addButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint);

        activityEntry = (EditText) findViewById(R.id.activityEntry);
        dateEntry = (EditText) findViewById(R.id.dateEntry);
        distanceEntry = (EditText) findViewById(R.id.distanceEntry);
        timeEntry = (EditText) findViewById(R.id.timeEntry);

        addButton = findViewById(R.id.floatingActionButton2);



        Thread thread = new Thread() {
            @Override
            public void run() {


                addButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        String activity = activityEntry.getText().toString();
                        String date = dateEntry.getText().toString();
                        String distance = distanceEntry.getText().toString();
                        String time = timeEntry.getText().toString();

                        FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                firebaseIdentifier = task.getResult();
                                // Do what you need with firebaseIdentifier

                                String activities = "activities";
                                DatabaseReference x = FirebaseDatabase.getInstance().getReference().child(String.valueOf(firebaseIdentifier)).child("Activities").push();
                                DatabaseReference totalDist = FirebaseDatabase.getInstance().getReference().child(String.valueOf(firebaseIdentifier)).child("Activities");

                                x.child("activity").setValue(activity);
                                x.child("time").setValue(time);
                                x.child("distance").setValue(distance);
                                x.child("speed").setValue("");
                                x.child("date").setValue(date);



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

                                DatabaseReference trop = database.getReference().child(firebaseIdentifier).child("Trophies");

                                trop.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.getValue() != null) {
                                            try {
                                                if (snapshot.child("Bronze").getValue().toString() == "true" & snapshot.child("BronzeNotified").exists() == false) {

                                                    Toast.makeText(Sprint.this, "You've earned a Bronze Trophy!", Toast.LENGTH_SHORT).show();
                                                    FirebaseDatabase.getInstance().getReference().child(firebaseIdentifier).child("Trophies").child("BronzeNotified").setValue(true);

                                                }


                                                if (snapshot.child("Silver").getValue().toString() == "true" & snapshot.child("SilverNotified").exists() == false) {

                                                    Toast.makeText(Sprint.this, "You've earned a Silver Trophy!", Toast.LENGTH_SHORT).show();
                                                    FirebaseDatabase.getInstance().getReference().child(firebaseIdentifier).child("Trophies").child("SilverNotified").setValue(true);

                                                }

                                                if (snapshot.child("Gold").getValue().toString() == "true" & snapshot.child("GoldNotified").exists() == false) {

                                                    Toast.makeText(Sprint.this, "You've earned a Gold Trophy!", Toast.LENGTH_SHORT).show();
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

                            }

                        });
                    }

                });
            }
        };

        thread.start();
    }

    public void onClickManual(View v) {

        Intent Sprint = new Intent(Sprint.this, Sprint.class);
        startActivity(Sprint);

    }

    public void onClickMyStats(View v) {
        // go to the activity for displaying journeys
        Intent MyStats = new Intent(Sprint.this, Statistics.class);
        startActivity(MyStats);
    }

    public void onClickView(View v) {
        // go to the activity for displaying journeys
        Intent myView = new Intent(Sprint.this, activityList.class);
        startActivity(myView);
    }



    public void onClickMyTrophies(View v) {
        // go to the activity for displaying statistics
        Intent MyTrophies = new Intent(Sprint.this, neu.edu.madcourse.strideapp.TrophyCase.class);
        startActivity(MyTrophies);
    }

    public void onClickRun(View v) {
        Intent StartRun = new Intent(Sprint.this, neu.edu.madcourse.strideapp.MapsActivity.class);
        startActivity(StartRun);
    }
}
