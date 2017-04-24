package pnzio.contactsassignment;

/**
 * Created by Alexander on 01/03/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ContactsDatabaseAdapter {
    SQLiteDatabase sqLiteDatabase;
    ContactsDatabaseHelper cdh;
    Context context;

    public ContactsDatabaseAdapter(Context c){
        context = c;
    }

    public ContactsDatabaseAdapter write() {
        cdh = new ContactsDatabaseHelper(context,
                cdh.DATABASE_NAME, null, cdh.VERSION);
        sqLiteDatabase = cdh.getWritableDatabase();
        return this;
    }
    public void close(){
        sqLiteDatabase.close();
    }

    public void insertDetails(String first_name, String last_name, String phone, String mobile, String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(cdh.FIRST_NAME, first_name);
        contentValues.put(cdh.LAST_NAME, last_name);
        contentValues.put(cdh.PHONE_NUMBER, phone);
        contentValues.put(cdh.MOBILE_NUMBER, mobile);
        contentValues.put(cdh.EMAIL, email);

        write();
        sqLiteDatabase.insert(cdh.TABLE_NAME, null, contentValues);
        close();
    }

    public Cursor checkContacts() {
        String[] cols = { cdh.KEY_ID, cdh.FIRST_NAME, cdh.LAST_NAME, cdh.PHONE_NUMBER,
                cdh.MOBILE_NUMBER, cdh.EMAIL};
        write();
        return sqLiteDatabase.query(cdh.TABLE_NAME, cols, null,
                null, null, null, null);
    }
    public Cursor checkAllContactsByID(int id) {
        String[] cols = { cdh.KEY_ID, cdh.FIRST_NAME, cdh.LAST_NAME,
                cdh.PHONE_NUMBER, cdh.MOBILE_NUMBER, cdh.EMAIL };
        write();
        return sqLiteDatabase.query(cdh.TABLE_NAME, cols,
                cdh.KEY_ID + "=" + id, null, null, null, null);
    }

    public boolean checkContactByName(String first_name, String last_name){
        Cursor cursor = null;
        write();
        String checkQuery = "SELECT " + cdh.FIRST_NAME + " FROM "
                + cdh.TABLE_NAME + " WHERE " + cdh.FIRST_NAME +
                "= '"+first_name + "'" + " AND " + cdh.LAST_NAME + "= '"+last_name + "'";
        cursor = sqLiteDatabase.rawQuery(checkQuery,null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public boolean checkContactByIDAndName(int id,String first_name, String last_name){
        Cursor cursor = null;
        write();
        String checkQuery = "SELECT " + cdh.KEY_ID +
                " FROM " + cdh.TABLE_NAME + " WHERE "
                + cdh.KEY_ID + "= '"+id + "' AND " + cdh.FIRST_NAME + "= '"+first_name + "'" +
                " AND " + cdh.LAST_NAME + "= '"+last_name + "'";
        cursor = sqLiteDatabase.rawQuery(checkQuery,null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public void deleteContact(int rowID) {
        write();
        sqLiteDatabase.delete(cdh.TABLE_NAME, cdh.KEY_ID + "=" + rowID, null);
        close();
    }

    public void updateContact(int rowId, String first_name, String last_name,
                              String phone, String mobile, String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(cdh.FIRST_NAME, first_name);
        contentValues.put(cdh.LAST_NAME, last_name);
        contentValues.put(cdh.PHONE_NUMBER, phone);
        contentValues.put(cdh.MOBILE_NUMBER, mobile);
        contentValues.put(cdh.EMAIL, email);
        write();
        sqLiteDatabase.update(cdh.TABLE_NAME, contentValues, cdh.KEY_ID + "=" + rowId, null);
        close();
    }

}
