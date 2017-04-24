package pnzio.contactsassignment;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ContactsDatabaseAdapter cda;
    ContactsDatabaseHelper cdh;
    ListView contactListListView;
    Button addContactBtn;
    Cursor cursor;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactListListView = (ListView)findViewById(R.id.contactsList);
        addContactBtn = (Button)findViewById(R.id.btn_addContact);
        cda = new ContactsDatabaseAdapter(this);

        String[] from = { cdh.FIRST_NAME,cdh.LAST_NAME };
        int[] to = { R.id.contactListFirstName, R.id.contactListLastName};

        cursor = cda.checkContacts();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
                R.layout.contact_list, cursor, from, to);
        contactListListView.setAdapter(cursorAdapter);

        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addContactIntent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(addContactIntent);
            }
        });

        contactListListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor listCursor = (Cursor) adapterView.getItemAtPosition(i);
                int rowID = listCursor.getInt(listCursor.getColumnIndex(cdh.KEY_ID));
                simpleAlertDialog(rowID);
                return true;
            }
        });

        contactListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor listCursor = (Cursor) adapterView.getItemAtPosition(i);
                final int rowID = listCursor.getInt(listCursor.getColumnIndex(cdh.KEY_ID));
                Bundle data = new Bundle();
                data.putInt("rowNumberID",rowID);
                Intent showContactIntent = new Intent(MainActivity.this, ShowContactActivity.class);
                showContactIntent.putExtras(data);
                startActivity(showContactIntent);
            }
        });
    }

    public void onResume() {
        super.onResume();
        cursor.requery();
    }
    private void simpleAlertDialog(int id) {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialogView = layoutInflater.inflate(R.layout.dialog_view, null);
        Button deleteFromDialog = (Button)dialogView.findViewById(R.id.deleteFromDialog);
        Button editFromDialog = (Button)dialogView.findViewById(R.id.editFromDialog);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();
        final int numberOfRowID = id;
        alertD.setView(dialogView);
        alertD.setTitle(R.string.contact_settings);
        alertD.show();

        deleteFromDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cda.deleteContact(numberOfRowID);
                finish();
                startActivity(getIntent());
            }
        });

        editFromDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putInt("rowNumberID",numberOfRowID);
                Intent editContactIntent = new Intent(MainActivity.this, EditContactActivity.class);
                editContactIntent.putExtras(data);
                startActivity(editContactIntent);
            }
        });

    }

}
