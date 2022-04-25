package neu.edu.madcourse.strideapp;

import android.os.Bundle;
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

import java.util.ArrayList;

public class userlist extends AppCompatActivity {
    RecyclerView recylcerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Exercise> list;
    int totalCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        recylcerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("Zach");
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

                    if (exercise.getCalories() != null) {
                        int x = Integer.parseInt(exercise.getCalories());
                        totalCalories += x;
                        String TotalCalories = "TotalCalories";
                        FirebaseDatabase.getInstance().getReference().child(TotalCalories).setValue(totalCalories);
                        myAdapter.notifyDataSetChanged();


                    }
                }
                totalCalories = 0;

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}
