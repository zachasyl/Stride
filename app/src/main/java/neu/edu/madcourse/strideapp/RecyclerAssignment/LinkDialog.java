package neu.edu.madcourse.strideapp.RecyclerAssignment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.FirebaseDatabase;

import neu.edu.madcourse.strideapp.R;

public class LinkDialog extends AppCompatDialogFragment {
    private EditText editTextURL;
    private EditText uName;

    private LinkDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view);
        builder.setTitle("Enter new bookmark")      ;
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }

        });
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editTextURL = view.findViewById(R.id.url);
                uName = view.findViewById((R.id.url2));
                String url = editTextURL.getText().toString();
                String urlName = uName.getText().toString();

                listener.applyTexts(url, urlName);
                FirebaseDatabase.getInstance().getReference().child(String.valueOf(5)).setValue(url);


            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (LinkDialogListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + "m");
        }
    }



}
