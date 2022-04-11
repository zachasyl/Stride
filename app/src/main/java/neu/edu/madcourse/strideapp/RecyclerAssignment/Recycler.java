package neu.edu.madcourse.strideapp.RecyclerAssignment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import neu.edu.madcourse.strideapp.R;

public class Recycler extends AppCompatActivity implements LinkDialogListener {
    private static final java.util.UUID UUID = null;
    private ArrayList<ItemCard> itemList = new ArrayList<>();

    private RecyclerView recyclerView;
    private Adapter rviewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;
    private FloatingActionButton addButton;
    private int userId = 1;
    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        init(savedInstanceState);
        final EditText input = new EditText(this);
        addButton = findViewById(R.id.floatingActionButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialog();
            }
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(Recycler.this, "Delete an item", Toast.LENGTH_SHORT).show();
                int position = viewHolder.getLayoutPosition();

                itemList.remove(position);

                rviewAdapter.notifyItemRemoved(position);

            }


        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    // android orientation. instead of randomly generating unique ids
    // this code gives image, item name, and url a unique identifies added to the iterating i
    //then the recover the initialitemdata we know wha tthe unique id is without storing all the
    // unique ids


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        int size = itemList == null ? 0 : itemList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);


        for (int i = 0; i < size; i++) {
            outState.putInt( i + "0" , itemList.get(i).getImage());
            outState.putString( i + "1" , itemList.get(i).getItemName());
            outState.putString( i + "2" , itemList.get(i).getURL());
        }
        super.onSaveInstanceState(outState);

    }

    private void init(Bundle savedInstanceState) {

        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private void initialItemData(Bundle savedInstanceState) {


        // Not first time to open this Activity
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (itemList == null || itemList.size() == 0) {

                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                for (int i = 0; i < size; i++) {
                    Integer imgId = savedInstanceState.getInt( + i + "0");
                    String itemName = savedInstanceState.getString( + i + "1");
                    String itemDesc = savedInstanceState.getString( + i + "2");


                    ItemCard itemCard = new ItemCard(itemName, itemDesc);

                    itemList.add(itemCard);







                }
            }
        }
    }

    private void createRecyclerView() {


        rLayoutManger = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);

        rviewAdapter = new Adapter(itemList);
        ItemClickListener itemClickListener = new ItemClickListener() {

            @Override
            public void onClick(int position) {
                itemList.get(position).onClick(position);
                ItemCard x = itemList.get(position);
                String url = x.getURL();

                if (!url.startsWith("http://" ) && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                try {
                    startActivity(i);
                }catch (Exception e) {
                }

                rviewAdapter.notifyItemChanged(position);
            }
        };
        rviewAdapter.setOnItemClickListener(itemClickListener);

        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);


    }


    public void openDialog(){
        LinkDialog dialog = new LinkDialog();
        dialog.show(getSupportFragmentManager(),"dialog" );

    }


    @Override
    public void applyTexts(String url, String name) {
        int position = 0;
        itemList.add(position, new ItemCard(url, name));

        Snackbar snackbar = Snackbar
                .make(recyclerView, "Added a link", Snackbar.LENGTH_LONG);
        snackbar.show();

//        Toast.makeText(good.this, "Add an item", Toast.LENGTH_SHORT).show();

        rviewAdapter.notifyItemInserted(position);


    }
}