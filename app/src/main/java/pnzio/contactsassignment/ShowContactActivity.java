package pnzio.contactsassignment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Alexander on 02/03/2017.
 */

public class ShowContactActivity extends Activity {
    ContactsDatabaseAdapter cda;
    Cursor cursor;
    TextView showFirstNameTextView, showLastNameTextView, showPhoneTextView, showMobileTextView, showEmailTextView;
    Button okBtn;
    int rowID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_contact);
        showFirstNameTextView = (TextView)findViewById(R.id.showFirstName);
        showLastNameTextView = (TextView)findViewById(R.id.showLastName);
        showPhoneTextView = (TextView)findViewById(R.id.showPhone);
        showMobileTextView = (TextView)findViewById(R.id.showMobile);
        showEmailTextView = (TextView)findViewById(R.id.showEmail);
        okBtn = (Button) findViewById(R.id.goBackFromShowContact);

        Bundle data = getIntent().getExtras();
        rowID = data.getInt("rowNumberID");
        cda = new ContactsDatabaseAdapter(this);
        cursor = cda.checkAllContactsByID(rowID);

        if (cursor.moveToFirst()) {
            do {
                showFirstNameTextView.setText(cursor.getString(1));
                showLastNameTextView.setText(cursor.getString(2));
                showPhoneTextView.setText(cursor.getString(3));
                showMobileTextView.setText(cursor.getString(4));
                showEmailTextView.setText(cursor.getString(5));
            } while (cursor.moveToNext());
        }

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                finish();
            }
        });

    }
}
