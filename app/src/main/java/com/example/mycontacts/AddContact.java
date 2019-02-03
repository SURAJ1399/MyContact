package com.example.mycontacts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {
    Context context;
    EditText et_name,et_number_,et_email,et_organistion;
    Button  setrelationship;
    Button add;
    String relationshipString;
    DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        context=this;
        dbHandler=new DBHandler(context);
        et_name=findViewById(R.id.name);
     et_number_=   findViewById(R.id.number);
     et_email=   findViewById(R.id.email);
     et_organistion=   findViewById(R.id.organisation);
     setrelationship=findViewById(R.id.setrelationship);
     add=findViewById(R.id.addcontact);

        relationshipString="Unspecified";
        setrelationship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence options[]={"Business","Friend","Acquaintance","Others"};
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Choose Relationship Type").setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(i==0) {relationshipString="Business";}
                        else if(i==1){relationshipString="Friend";}
                        else if(i==2){relationshipString="Acquaintance";}
                        else if(i==3){relationshipString="Others";}

                    }
                }).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=et_name.getText().toString();
                String number=et_number_.getText().toString();
                String email=et_email.getText().toString();
                String organisataion=et_organistion.getText().toString();
                if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(number)&& !TextUtils.isEmpty(email)&& !TextUtils.isEmpty(organisataion))
                {
                    Contact contact=new Contact(name,number,email,organisataion,relationshipString);
                    dbHandler.addContact(contact);
                   Toast.makeText(context, "Contact Added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddContact.this,MainActivity.class));

                }
                else
                {
                    Toast.makeText(context, "Please! Fill All Details", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
