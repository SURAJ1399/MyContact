package com.example.mycontacts;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
ListView contactlist;
Button search;
Button add;
EditText searchText;
Context context;
DBHandler dbHandler;
List <Contact>contacts;
int REQUEST_CALL=1;
String callnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        contactlist = findViewById(R.id.contact_list);
        searchText = findViewById(R.id.search_text);

        contacts = new ArrayList<>();
        dbHandler = new DBHandler(context);
        contacts = dbHandler.getALLContact();

        //DISPLAYING ALL NAMES ON LISTVIEW

        String[] nameArray = new String[contacts.size()];
        for (int x = 0; x < contacts.size(); x++) {
            nameArray[x] = contacts.get(x).getName();

        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, nameArray);
        contactlist.setAdapter(adapter);


        //GETTING A CONTACT DATA

        contactlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                final Contact contact = contacts.get(position);
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(contact.getName()).
                        setMessage("Mobile No:- " + contact.getNumber() + "\n" + "Email:- " + contact.getEmail() + "\n" + "Organisation:- " + contact.getOrganisation() + "\n" + "Relation:- " + contact.getRelationship() + "\n" + "\n" + "PRESS ITEM LONG TO DELETE")
                        .setPositiveButton("Call Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                        {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
                            callnumber=contact.getNumber();
                        }
                        else
                            {
                            String call = "tel:"+contact.getNumber();
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(call)));
                             }

                        }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                    }
                }).show();

            }
        });

        ///SEARCH


        contactlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dbHandler.delete(contacts.get(position).getId() + "");
                Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show();
                String[] nameArray = new String[contacts.size()];
                for (int x = 0; x < contacts.size(); x++) {
                    nameArray[x] = contacts.get(x).getName();

                }
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, nameArray);
                contactlist.setAdapter(adapter);

                return false;
            }
        });

        //ADD CONTACT
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.additem:
              addx();
                return true;
            case R.id.searchitem:
               searchx();
                return true;
            default:
                return false;


        }

    }
    public void addx(){
        Intent intent=new Intent(MainActivity.this,AddContact.class);
        startActivity(intent);

    }
    public void searchx(){
        ArrayList<String> userlist = new ArrayList<>();
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getName().contains(searchText.getText().toString())) {
                userlist.add(contacts.get(i).getName());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, userlist);
        contactlist.setAdapter(adapter);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL)
        {
            if(grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                String call = "tel:"+callnumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(call)));
            }
        }
    }
}
