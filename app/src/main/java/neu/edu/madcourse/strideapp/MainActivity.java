package neu.edu.madcourse.strideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }





    public void onClickManual(View v) {

        Intent Sprint = new Intent(MainActivity.this, Sprint.class);
        startActivity(Sprint);

    }

    public void onClickMyStats(View v) {
        // go to the activity for displaying journeys
        Intent MyStats = new Intent(MainActivity.this, Statistics.class);
        startActivity(MyStats);
    }

    public void onClickView(View v) {
        // go to the activity for displaying journeys
        Intent myView = new Intent(MainActivity.this, activityList.class);
        startActivity(myView);
    }



    public void onClickMyTrophies(View v) {
        // go to the activity for displaying statistics
        Intent MyTrophies = new Intent(MainActivity.this, neu.edu.madcourse.strideapp.TrophyCase.class);
        startActivity(MyTrophies);
    }

    public void onClickRun(View v) {
        Intent StartRun = new Intent(MainActivity.this, neu.edu.madcourse.strideapp.MapsActivity.class);
        startActivity(StartRun);
    }

}