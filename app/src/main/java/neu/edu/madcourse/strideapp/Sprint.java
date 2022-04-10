package neu.edu.madcourse.strideapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class Sprint extends AppCompatActivity {
    private EditText dateEntry;
    private EditText idEntry;
    private EditText distanceEntry;
    private EditText timeEntry;

    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint);
        dateEntry = (EditText)findViewById(R.id.dateEntry);
        idEntry = (EditText)findViewById(R.id.IDentry);
        distanceEntry = (EditText)findViewById(R.id.IDentry);
        timeEntry = (EditText)findViewById(R.id.timeEntry);

        addButton = findViewById(R.id.floatingActionButton2);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idEntry.getText().toString();
                String date = dateEntry.getText().toString();
                String distance = distanceEntry.getText().toString();
                String time = timeEntry.getText().toString();

                String speed =  distance + time;
                String caloriesBurned = "500";

                        FirebaseDatabase.getInstance().getReference().child(String.valueOf("Zach")).child(id).child("date").setValue(date);
                        FirebaseDatabase.getInstance().getReference().child(String.valueOf("Zach")).child(id).child("distance").setValue(distance);
                        FirebaseDatabase.getInstance().getReference().child(String.valueOf("Zach")).child(id).child("time").setValue(time);

                        FirebaseDatabase.getInstance().getReference().child(String.valueOf("Zach")).child(id).child("speed").setValue(speed);


                        FirebaseDatabase.getInstance().getReference().child(String.valueOf("Zach")).child(id).child("calories").setValue(caloriesBurned);


            }
        });

    }
}
