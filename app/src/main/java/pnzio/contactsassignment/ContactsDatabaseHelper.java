package pnzio.contactsassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alexander on 01/03/2017.
 */

public class ContactsDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CONTACTS_DB";
    public static final String TABLE_NAME = "CONTACTS_TABLE";
    public static final int VERSION = 1;
    public static final String KEY_ID = "_id";
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
    public static final String EMAIL = "EMAIL";
    public static final String DATABASE_SCRIPT = "create table " + TABLE_NAME + " ("
            + KEY_ID + " integer primary key autoincrement, " + FIRST_NAME
            + " text not null, " + LAST_NAME + " text not null, "
            + PHONE_NUMBER + " text not null, "
            + MOBILE_NUMBER + " text not null, " + EMAIL + " text not null );";

    public ContactsDatabaseHelper(Context context, String name,
                                  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
        onCreate(db);
    }
}