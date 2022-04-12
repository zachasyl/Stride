package neu.edu.madcourse.strideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean achievement = false;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickSprint(View v) {
        // go to the record journey activity

        Intent Sprint = new Intent(MainActivity.this, neu.edu.madcourse.strideapp.userlist.class);
        startActivity(Sprint);

    }



    public void onClickMySprints(View v) {
        // go to the activity for displaying journeys
        Intent MySprints = new Intent(MainActivity.this, Sprint.class);
        startActivity(MySprints);
    }

//    public void onClickMyStatistics(View v) {
//        // go to the activity for displaying statistics
//        Intent MyStatistics = new Intent(MainActivity.this, neu.edu.madcourse.strideapp.MyStatistics.class);
//        startActivity(MyStatistics);
//    }


    public void onClickMyTrophies(View v) {
        // go to the activity for displaying statistics
        Intent MyTrophies = new Intent(MainActivity.this, neu.edu.madcourse.strideapp.TrophyCase.class);
        startActivity(MyTrophies);
    }

    public void onClickRun(View v) {
        // go to the activity for displaying statistics
        Intent StartRun = new Intent(MainActivity.this, neu.edu.madcourse.strideapp.MapsActivity.class);
        startActivity(StartRun);
    }

}