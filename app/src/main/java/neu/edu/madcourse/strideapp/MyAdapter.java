package neu.edu.madcourse.strideapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<Exercise> list;

    public MyAdapter(Context context, ArrayList<Exercise> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item, parent,false) ;
        return new MyViewHolder(v);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date, distance, time, calories, speed;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
/*
            id = itemView.findViewById(R.id.IDentry);
*/
            date = itemView.findViewById(R.id.date);
            distance = itemView.findViewById(R.id.distance);
            time = itemView.findViewById(R.id.time);
            calories = itemView.findViewById(R.id.calories);
            speed = itemView.findViewById(R.id.speed);

        }

    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Exercise exercise = list.get(position) ;

/*
        holder.id.setText(user.getId());
*/
        holder.calories.setText(exercise.getCalories());
        holder.date.setText(exercise.getDate());
        holder.distance.setText(exercise.getDistance());
        holder.speed.setText(exercise.getSpeed());
        holder.time.setText(exercise.getTime());

    }

}
