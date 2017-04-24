package pnzio.contactsassignment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Alexander on 02/03/2017.
 */

public class EditContactActivity extends Activity{
    ContactsDatabaseAdapter cda;
    Cursor cursor;
    EditText editFirstNameEditText, editLastNameEditText, editPhoneEditText, editMobileEditText, editEmailEditText;
    Button saveDataBtn, goBackBtn;
    int rowID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);
        editFirstNameEditText = (EditText) findViewById(R.id.editFirstName);
        editLastNameEditText = (EditText) findViewById(R.id.editLastName);
        editPhoneEditText = (EditText) findViewById(R.id.editPhoneNumber);
        editMobileEditText = (EditText) findViewById(R.id.editMobileNumber);
        editEmailEditText = (EditText) findViewById(R.id.editEmail);
        saveDataBtn = (Button) findViewById(R.id.updateContactInfo);
        goBackBtn = (Button) findViewById(R.id.goBackFromEditContact);

        Bundle data = getIntent().getExtras();
        rowID = data.getInt("rowNumberID");
        cda = new ContactsDatabaseAdapter(this);
        cursor = cda.checkAllContactsByID(rowID);

        if (cursor.moveToFirst()) {
            do {
                editFirstNameEditText.setText(cursor.getString(1));
                editLastNameEditText.setText(cursor.getString(2));
                editPhoneEditText.setText(cursor.getString(3));
                editMobileEditText.setText(cursor.getString(4));
                editEmailEditText.setText(cursor.getString(5));
            } while (cursor.moveToNext());
        }

        saveDataBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String first_name = editFirstNameEditText.getText().toString();
                String last_name = editLastNameEditText.getText().toString();
                String phone = editPhoneEditText.getText().toString();
                String mobile = editMobileEditText.getText().toString();
                String email = editEmailEditText.getText().toString();

                if(validateForNonEmpty(first_name,last_name,phone,mobile,email)){
                    if(!phone.matches("[0-9]+") || !mobile.matches("[0-9]+")){
                        showNotNumbersDialog();
                    }
                    else{
                        if(cda.checkContactByIDAndName(rowID,first_name,last_name)) {
                            cda.updateContact(rowID, first_name, last_name, phone, mobile, email);
                            finish();
                            Intent mainIntent = new Intent(EditContactActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                        }
                        else{
                            if(cda.checkContactByName(first_name,last_name)) {
                                showContactExistsDialog();
                            }
                            else {
                                cda.updateContact(rowID, first_name, last_name, phone, mobile, email);
                                finish();
                                Intent mainIntent = new Intent(EditContactActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                            }
                        }
                    }

                }
                else{
                    showEmptyFieldsDialog();
                }
            }
        });

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent mainIntent = new Intent(EditContactActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });


    }
    public boolean validateForNonEmpty(String s1,String s2,String s3,String s4,String s5){
        return !s1.equals("") && !s2.equals("") && !s3.equals("") && !s4.equals("") && !s5.equals("");
    }
    public void showContactExistsDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditContactActivity.this);
        builder.setTitle(R.string.error);
        builder.setMessage(R.string.contact_exists);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showEmptyFieldsDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditContactActivity.this);
        builder.setTitle(R.string.error);
        builder.setMessage(R.string.empty_fields_error);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showNotNumbersDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditContactActivity.this);
        builder.setTitle(R.string.error);
        builder.setMessage(R.string.not_numbers_fields_error);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
