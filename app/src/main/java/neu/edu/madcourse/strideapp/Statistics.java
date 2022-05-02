package neu.edu.madcourse.strideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.FirebaseInstallations;

public class Statistics extends AppCompatActivity {
    private String firebaseIdentifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        TextView distanceText = (TextView) findViewById(R.id.totalDistance);
        TextView activityText = (TextView) findViewById(R.id.totalActivities);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseIdentifier = task.getResult();
                DatabaseReference ref = database.getReference().child(firebaseIdentifier).child("TotalDistance");
                DatabaseReference exerciseCount = database.getReference().child(firebaseIdentifier).child("Activities");

                ref.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            String ma = snapshot.getValue().toString();
                            double totalStat = Double.parseDouble(ma);

                            distanceText.setText("Total Miles:  " + totalStat);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                exerciseCount.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int counter = 0;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            counter += 1;
                        }
                        activityText.setText("Total Activities: " + counter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
            }
        });
    }

    public void onClickManual(View v) {

        Intent Sprint = new Intent(Statistics.this, Sprint.class);
        startActivity(Sprint);

    }

    public void onClickMyStats(View v) {
        // go to the activity for displaying journeys
        Intent MyStats = new Intent(Statistics.this, Statistics.class);
        startActivity(MyStats);
    }

    public void onClickView(View v) {
        // go to the activity for displaying journeys
        Intent myView = new Intent(Statistics.this, activityList.class);
        startActivity(myView);
    }



    public void onClickMyTrophies(View v) {
        // go to the activity for displaying statistics
        Intent MyTrophies = new Intent(Statistics.this, neu.edu.madcourse.strideapp.TrophyCase.class);
        startActivity(MyTrophies);
    }

    public void onClickRun(View v) {
        Intent StartRun = new Intent(Statistics.this, neu.edu.madcourse.strideapp.MapsActivity.class);
        startActivity(StartRun);
    }
}
