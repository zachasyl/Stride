package neu.edu.madcourse.strideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.FirebaseInstallations;

import java.util.ArrayList;


public class TrophyCase extends AppCompatActivity {
    int TotalCalories;
    boolean unlocked;
    boolean toasted;
    DatabaseReference database;
    ArrayList<Exercise> list;
    private ImageView mImageView;
    private String firebaseIdentifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String TotalCalories = "TotalCalories";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trophies);


        FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseIdentifier = task.getResult();
                DatabaseReference ref = database.getReference().child(firebaseIdentifier).child("Trophies");



        ref.addValueEventListener(new ValueEventListener(){
            private ImageView mImageView;
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() != null) {
                        try {

                            boolean bronze = (boolean) snapshot.child("Bronze").getValue();

                            if (bronze) {
                                mImageView = (ImageView) findViewById(R.id.bronze);
                                mImageView.setImageResource(R.drawable.bronze);
                            }

                            boolean silver = (boolean) snapshot.child("Silver").getValue();


                            if (silver) {
                                mImageView = (ImageView) findViewById(R.id.silver);
                                mImageView.setImageResource(R.drawable.silver);
                            }

                            boolean gold = (boolean) snapshot.child("Gold").getValue();

                            if (gold) {
                                mImageView = (ImageView) findViewById(R.id.gold);
                                mImageView.setImageResource(R.drawable.gold);


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

            }});
    }

    public void onClickManual(View v) {

        Intent Sprint = new Intent(TrophyCase.this, Sprint.class);
        startActivity(Sprint);

    }

    public void onClickMyStats(View v) {
        // go to the activity for displaying journeys
        Intent MyStats = new Intent(TrophyCase.this, Statistics.class);
        startActivity(MyStats);
    }

    public void onClickView(View v) {
        // go to the activity for displaying journeys
        Intent myView = new Intent(TrophyCase.this, activityList.class);
        startActivity(myView);
    }



    public void onClickMyTrophies(View v) {
        // go to the activity for displaying statistics
        Intent MyTrophies = new Intent(TrophyCase.this, neu.edu.madcourse.strideapp.TrophyCase.class);
        startActivity(MyTrophies);
    }

    public void onClickRun(View v) {
        Intent StartRun = new Intent(TrophyCase.this, neu.edu.madcourse.strideapp.MapsActivity.class);
        startActivity(StartRun);
    }

}



