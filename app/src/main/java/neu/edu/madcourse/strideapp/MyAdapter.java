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
    ArrayList<User> list;

    public MyAdapter(Context context, ArrayList<User> list) {
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
        User user = list.get(position) ;

/*
        holder.id.setText(user.getId());
*/
        holder.calories.setText(user.getCalories());
        holder.date.setText(user.getDate());
        holder.distance.setText(user.getDistance());
        holder.speed.setText(user.getSpeed());
        holder.time.setText(user.getTime());

    }

}
