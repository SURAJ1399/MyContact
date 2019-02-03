package com.example.mycontacts;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static  final int VERSION=2;
    private static  final String DB_Name="ContactsDB";
    private static final String CONTACTS_TABLE="contacts";
    private static final String  Name="name";
    private static final String  Number="number";
    private static final String  Email="email";
    private static final String  Organisation="organisataion";
    private static final String Relationship="relationship";
    private static final String ID="id";

    public DBHandler( Context context) {
        super(context, Name, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CONTACTS_TABLE= "CREATE TABLE   "+CONTACTS_TABLE+"("+"ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                Name + " TEXT," +
                Number +" TEXT," +
                Email + " TEXT," +
                Organisation + " TEXT,"+
                Relationship + " TEXT"+
                " )";

        sqLiteDatabase.execSQL(SQL_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int il) {
        String sql= "DROP TABLE IF EXISTS"+ CONTACTS_TABLE;
        sqLiteDatabase.execSQL("DROP TABLE "+ CONTACTS_TABLE);
        onCreate(sqLiteDatabase);

    }
    public  void addContact(Contact contact)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Name,contact.getName());
        values.put(Number,contact.getNumber());
        values.put(Email,contact.getEmail());
        values.put(Organisation,contact.getOrganisation());
        values.put(Relationship,contact.getRelationship());
     db.insert(CONTACTS_TABLE,null,values);
     db.close();
    }
    public Contact getContact(int id){
        SQLiteDatabase db=getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor=db.query(CONTACTS_TABLE,new String[]{ ID,Name, Number, Email, Organisation, Relationship},ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        Contact contact;
if(cursor==null)
{
    cursor.moveToFirst();
    contact=new Contact(cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getString(5),
            Integer.parseInt(cursor.getString(0)));
    return  contact;
}else
{
    return null;
}
    }
    public List<Contact> getALLContact(){
        SQLiteDatabase db=getWritableDatabase();
        List<Contact> contacts=new ArrayList<>();
        String query= "SELECT * FROM "+CONTACTS_TABLE;
        @SuppressLint("Recycle") Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{ Contact contact=new Contact();
                      contact.setName(cursor.getString(1));
                      contact.setNumber(  cursor.getString(2));
                              contact.setEmail(cursor.getString(3));
                                contact.setOrganisation( cursor.getString(4));
                                        contact.setRelationship( cursor.getString(5));
                                                contact.setId(Integer.parseInt(cursor.getString(0)));
                                                contacts.add(contact);
            }while(cursor.moveToNext());      }
            return  contacts;

    }

    public int UpdateCOntacts(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Name, contact.getName());
        values.put(Number, contact.getNumber());
        values.put(Email, contact.getEmail());
        values.put(Organisation, contact.getOrganisation());
        values.put(Relationship, contact.getRelationship());
        return db.update(CONTACTS_TABLE, values, ID+"=?",new String[]{String.valueOf(contact.getId())});

    }
    public  void DeleteCOntacts(Contact contact){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(CONTACTS_TABLE,ID+"=?",new String[]{String.valueOf(contact.getId())});
        db.close();

    }
    public int getContactCount(){
        SQLiteDatabase db=getReadableDatabase();
        String query= String.format("SELECT * FROM%s", CONTACTS_TABLE);
        Cursor cursor=db.rawQuery(query,null);
        return  cursor.getCount();
    }
    public void delete(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(CONTACTS_TABLE,"ID=?",new String[]{id});


    }
}
