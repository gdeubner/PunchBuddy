package com.mean.punchbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class EditListActivity extends AppCompatActivity {

    ArrayList<String> newList;

    EditText titleText;
    RecyclerView strikeList;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);
        titleText =  (EditText)findViewById(R.id.list_name);

        newList = new ArrayList<String>();
        newList.add("Punch");
        strikeList = findViewById(R.id.recycler_view_strike_list);
        adapter = new MyAdapter(this, newList);
        //adapter = new StrikeListAdapter(this, newList);
        strikeList.setAdapter(adapter);

        Intent recieved_intent = getIntent();
        String originalName = recieved_intent.getStringExtra("list_to_edit");
        if(originalName != null){
            titleText.setText(originalName);
        } else{
            newList.add("Punch");
        }

        adapter.notifyDataSetChanged();
    }



    public void closeCreate(View view){
        Intent returnIntent = new Intent();
        Intent initial_intent = getIntent();
        if(titleText.getText().toString().equals("")){ //check that the list has a name
            Toast.makeText(this, "You must give your list a title.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if(newList.size() < 1){ //check that the list isn't empty
            Toast.makeText(this, "You must make at least 1 entry.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(initial_intent.getStringExtra("mode").toString().equals("edit_mode")){
            returnIntent.putExtra("old_list_name", initial_intent.
                    getStringExtra("list_to_edit").toString());
        }
        returnIntent.putExtra("new_list", newList);
        returnIntent.putExtra("new_list_name", titleText.getText());
        setResult(RESULT_OK, returnIntent);
        Log.d("debug", "closeCreate: Closing edit activity");
        finish();
    }

    public void deleteList(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(getResources().getString(R.string.warning));
        builder.setMessage(getResources().getString(R.string.delete_warning));
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent returnIntent = new Intent();
                        Intent initialIntent = getIntent();
                        String list_to_edit = initialIntent.getStringExtra("list_to_edit");
                        if(list_to_edit != null) {
                            returnIntent.putExtra("delete_request", list_to_edit);
                            setResult(RESULT_OK, returnIntent);
                        }
                        finish();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing, just closes the dialogue
                    }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        newList.remove(position);
        adapter.notifyDataSetChanged();
    }


    public void addItem(View view){
        newList.add("");
        adapter.notifyDataSetChanged();
    }

    public void cancelEdit(View view){
        finish();
    }

}