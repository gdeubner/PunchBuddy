package com.mean.punchbuddy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DrillListActivity extends AppCompatActivity {

    private ArrayList<String> listOfLists;
    private static final int new_list_request_code = 1;
    private static final int edit_list_request_code = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drill_list);
        getListsFromMem();
        setListView();
    }


    private void getListsFromMem(){
        try{
            File saveFile = new File(getFilesDir() + getResources()
                    .getString(R.string.save_file));
            if(saveFile.exists()){
                //load list from save file
                listOfLists = new ArrayList<String>();
                FileInputStream fis = new FileInputStream(saveFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                while(true) {
                    try {
                        Object obj = ois.readObject();
                        listOfLists.add((String)obj);
                    }catch(EOFException e){
                        ois.close();
                        Log.d("debug", "read from and closed save file");
                        return;
                    }catch(Exception e){
                        ois.close();
                        Log.d("debug", "failed to read object from " +
                                "saveFile. file closed");
                        return;
                    }
                }
            } else {
                //load default list from resources
                listOfLists = new ArrayList<String>
                        (Arrays.asList(getResources().getStringArray(R.array.list_of_lists)));
                Log.d("debug", "loaded list from resources");
                try {
                    FileOutputStream fos = new FileOutputStream(saveFile);
                    Log.d("debug", "Opened file output stream.");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    Log.d("debug", "created object output stream");
                    for(String s : listOfLists){
                        oos.writeObject(s);
                    }
                    oos.close();
                    Log.d("debug", "created save file");

                } catch(Exception e2){
                    Log.d("debug",  "Unable to print to file\n" + e2.getMessage());
                }
            }
        } catch (Exception e){
            Log.d("debug", "getListsFromMem: failed big time");
        }
    }

    private void SaveListsToMem(ArrayList<String> listOfLists){

    }


    private void setListView(){
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, listOfLists);
        ListView myView = (ListView) findViewById(R.id.list_view_layout);
        myView.setAdapter(myAdapter);
        myView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    public void createList(View view){
        Intent intent = new Intent(this, EditListActivity.class);
        intent.putExtra("mode", "create_mode");
        startActivityForResult(intent, new_list_request_code);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ResultReturn", "onActivityResult: Entered method");

        // Check that it is the SecondActivity with an OK result
        switch (requestCode) {
            case new_list_request_code :
                Log.d("ResultReturn", "onActivityResult: request code matched");
                if (resultCode == RESULT_OK) {
                    // Get String data from Intent

                    ArrayList<String> returnList = data.getStringArrayListExtra("new_list");
                    if (returnList.size() == 0) {
                        Log.d("ResultReturn", "onActivityResult: Returned empty array");
                    }
                }
            case edit_list_request_code:
                if (resultCode == RESULT_OK) {
                    // Get String data from Intent
                    ArrayList<String> returnList = data.getStringArrayListExtra("new_list");
                    if (returnList.size() == 0) {
                        Log.d("ResultReturn", "onActivityResult: Returned empty array");
                    }
                }

        }
    }

    public void editSelectedList(View view){
        ListView listView = (ListView) findViewById(R.id.list_view_layout);
        int len = listView.getCount();
        ArrayList<String> selectedItems = new ArrayList<String>(1);
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        for (int i = 0; i < len; i++) {
            if (checked.get(i)) {
                selectedItems.add(listOfLists.get(i));
            }
        }
        if(selectedItems.size() == 1){
            Intent intent = new Intent(DrillListActivity.this, EditListActivity.class);
            intent.putExtra("list_to_edit", selectedItems.get(0));
            intent.putExtra("mode", "edit_mode");
            startActivityForResult(intent, edit_list_request_code);
        } else if(selectedItems.size()>1){
            Toast.makeText(this, R.string.multiple_selection_toast_message,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.no_selection_toast_message,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void goToRoutine(View view){
        ListView myView = (ListView) findViewById(R.id.list_view_layout);

        Intent intent = new Intent(this, RoutineActivity.class);
        //intent.putExtra("selected_lists", );
        startActivity(intent);
    }

}