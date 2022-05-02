package neu.edu.madcourse.strideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.FirebaseInstallations;

import java.util.ArrayList;

public class activityList extends AppCompatActivity {
    RecyclerView recylcerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Exercise> list;
    String firebaseIdentifier;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        recylcerView = findViewById(R.id.userList);
        FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseIdentifier = task.getResult();
                // Do what you need with firebaseIdentifier


        database = FirebaseDatabase.getInstance().getReference(firebaseIdentifier).child("Activities");
        recylcerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recylcerView.setLayoutManager(linearLayoutManager);

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recylcerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener(){
            private ImageView mImageView;


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Exercise exercise = dataSnapshot.getValue(Exercise.class);
                    list.add(exercise);
                    myAdapter.notifyDataSetChanged();
//                    if (exercise.getDistance() != null) {
//                        int x = Integer.parseInt(exercise.getDistance());
//                        totalDistance += x;
//                        String TotalDistance = "TotalDistance";
//                        FirebaseDatabase.getInstance().getReference().child(TotalDistance).setValue(totalDistance);
//                        myAdapter.notifyDataSetChanged();
//
//
//                    }
                }
//                totalDistance = 0;

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            }
        });

    }

    public void onClickManual(View v) {

        Intent Sprint = new Intent(activityList.this, Sprint.class);
        startActivity(Sprint);

    }

    public void onClickMyStats(View v) {
        // go to the activity for displaying journeys
        Intent MyStats = new Intent(activityList.this, Statistics.class);
        startActivity(MyStats);
    }


    public void onClickView(View v) {
        Intent myView = new Intent(activityList.this, activityList.class);
        startActivity(myView);
    }



    public void onClickMyTrophies(View v) {
        // go to the activity for displaying statistics
        Intent MyTrophies = new Intent(activityList.this, neu.edu.madcourse.strideapp.TrophyCase.class);
        startActivity(MyTrophies);
    }

    public void onClickRun(View v) {
        Intent StartRun = new Intent(activityList.this, neu.edu.madcourse.strideapp.MapsActivity.class);
        startActivity(StartRun);
    }

}
