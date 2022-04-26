package neu.edu.madcourse.strideapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.installations.FirebaseInstallations;

public class Sprint extends AppCompatActivity {
    private EditText dateEntry;
    private EditText idEntry;
    private EditText distanceEntry;
    private EditText timeEntry;
    private String firebaseIdentifier;

    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint);
        dateEntry = (EditText)findViewById(R.id.dateEntry);
        distanceEntry = (EditText)findViewById(R.id.distanceEntry);
        timeEntry = (EditText)findViewById(R.id.timeEntry);

        addButton = findViewById(R.id.floatingActionButton2);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = dateEntry.getText().toString();
                String distance = distanceEntry.getText().toString();
                String time = timeEntry.getText().toString();

               FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        firebaseIdentifier = task.getResult();
                        // Do what you need with firebaseIdentifier

                String speed =  distance + time;
                String caloriesBurned = "500";
                DatabaseReference x = FirebaseDatabase.getInstance().getReference().child(String.valueOf(firebaseIdentifier)).push();
                x.child("time").setValue(time);
                x.child("distance").setValue(distance);
                x.child("speed").setValue(speed);
                x.child("date").setValue(date);
                x.child("calories").setValue(caloriesBurned);


                    }
               });
            }
        });

    }
}
