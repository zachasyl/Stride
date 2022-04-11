package neu.edu.madcourse.strideapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class TrophyCase extends AppCompatActivity {
    int TotalCalories;
    boolean unlocked;
    boolean toasted;
    DatabaseReference database;
    ArrayList<Exercise> list;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String TotalCalories = "TotalCalories";
        DatabaseReference ref = database.getReference().child("TotalCalories");
        super.onCreate(savedInstanceState);


        setContentView(R.layout.trophies);

        ref.addValueEventListener(new ValueEventListener(){
            private ImageView mImageView;
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
//

                    if (snapshot.getValue() != null) {
                        String ma = snapshot.getValue().toString();
                        int k = Integer.parseInt(ma);


                        if (k >= 1500) {
                            mImageView = (ImageView) findViewById(R.id.imageView3);
                            mImageView.setImageResource(R.drawable.ic_launcher_foreground);
                            unlocked = true;

//                            add trophy to database

                        }


                    }




//                }




            }





            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}



