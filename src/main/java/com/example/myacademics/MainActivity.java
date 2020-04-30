package com.example.myacademics;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    com.example.myacademics.DataBase myDb;
    EditText editName, editMarks, editId, editSession, editRollNumber;
    Button buttonAdd;
    Button btnViewAll;
    Button btnUpdate;
    Button btnDelete;
    TextView textId, textName, textMarks, textSession, textRollNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DataBase(this);

        editId = (EditText)findViewById(R.id.editId);
        editName = (EditText)findViewById(R.id.editName);
        editMarks = (EditText)findViewById(R.id.editMarks);
        editSession = (EditText)findViewById(R.id.editSession);
        editRollNumber = (EditText)findViewById(R.id.editRollNumber);
        buttonAdd = (Button)findViewById(R.id.buttonAdd);
        btnViewAll = (Button)findViewById(R.id.btnViewAll);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        textId = (TextView)findViewById(R.id.textid);
        textName = (TextView)findViewById(R.id.textName);
        textMarks = (TextView)findViewById(R.id.textMarks);
        textSession = (TextView)findViewById(R.id.textSession);
        textRollNumber = (TextView)findViewById(R.id.textRollNumber);



        addData();
        viewAll();
        updateData();
        delete();

    }

    public void delete() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(editId.getText().toString());
                if (deletedRows > 0) {
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateData() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateData(editId.getText().toString(), editName.getText().toString(),
                        editMarks.getText().toString(), editRollNumber.getText().toString(),
                        editSession.getText().toString());

                if (isUpdated) {
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void addData() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted =
                        myDb.insertData(editName.getText().toString(), editMarks.getText().toString(),
                                editRollNumber.getText().toString(), editSession.getText().toString());

                if (isInserted)
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewAll() {
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                }
                else {
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append("Id : " + res.getString(0) + "\n");
                        buffer.append("Name : " + res.getString(1) + "\n");
                        buffer.append("Marks : " + res.getString(2) + "\n");
                        buffer.append("Roll Number : " + res.getString(3) + "\n");
                        buffer.append("Session : " + res.getString(4) + "\n\n");

                    }
                    showMessage("Data", buffer.toString());
                }
            }
        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}

