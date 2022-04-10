package neu.edu.madcourse.strideapp.RecyclerAssignment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import neu.edu.madcourse.strideapp.R;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private final ArrayList<ItemCard> itemList;
    private ItemClickListener listener;

    //Constructor
    public Adapter(ArrayList<ItemCard> itemList) {
        this.itemList = itemList;
    }



    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemCard currentItem = itemList.get(position);

/*
        holder.itemIcon.setImageResource(currentItem.getImage());
*/
        holder.itemName.setText(currentItem.getItemName());
        holder.itemDesc.setText(currentItem.getURL());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
