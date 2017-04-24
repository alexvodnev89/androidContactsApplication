package pnzio.contactsassignment;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Alexander on 01/03/2017.
 */

public class AddContactActivity extends Activity {
    ContactsDatabaseAdapter cda;
    EditText addFirstNameEditText, addLastNameEditText, addPhoneEditText, addMobileEditText, addEmailEditText;
    Button addBtn, resetFormBtn, goBackBtn;
    Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);
        addFirstNameEditText = (EditText)findViewById(R.id.firstNameET);
        addLastNameEditText = (EditText)findViewById(R.id.lastNameET);
        addPhoneEditText = (EditText)findViewById(R.id.phoneNumberET);
        addMobileEditText = (EditText)findViewById(R.id.mobileNumberET);
        addEmailEditText = (EditText)findViewById(R.id.emailET);
        addBtn = (Button)findViewById(R.id.addContactSubmit);
        resetFormBtn = (Button)findViewById(R.id.addContactResetForm);
        goBackBtn = (Button) findViewById(R.id.goBackFromAddContact);

        cda = new ContactsDatabaseAdapter(this);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstNameValue = addFirstNameEditText.getText().toString();
                String lastNameValue = addLastNameEditText.getText().toString();
                String phoneValue = addPhoneEditText.getText().toString();
                String mobileValue = addMobileEditText.getText().toString();
                String emailValue = addEmailEditText.getText().toString();

                if(validateForNonEmpty(firstNameValue,lastNameValue,phoneValue,mobileValue,emailValue)) {
                    if(!phoneValue.matches("[0-9]+") || !mobileValue.matches("[0-9]+")){
                        showNotNumbersDialog();
                    }
                    else{
                        if(cda.checkContactByName(firstNameValue,lastNameValue)){
                            showContactExistsDialog();
                        }else {
                            cda.insertDetails(firstNameValue, lastNameValue, phoneValue, mobileValue, emailValue);
                            finish();
                        }
                    }
                }
                else{
                    showEmptyFieldsDialog();
                }
            }
        });
        resetFormBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFirstNameEditText.setText("");
                addLastNameEditText.setText("");
                addPhoneEditText.setText("");
                addEmailEditText.setText("");
                addMobileEditText.setText("");
            }
        });
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public boolean validateForNonEmpty(String s1,String s2,String s3,String s4,String s5){
        return !s1.equals("") && !s2.equals("") && !s3.equals("") && !s4.equals("") && !s5.equals("");
    }
    public void showContactExistsDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddContactActivity.this);
        builder.setTitle(R.string.error);
        builder.setMessage(R.string.contact_exists);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showEmptyFieldsDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddContactActivity.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(AddContactActivity.this);
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
