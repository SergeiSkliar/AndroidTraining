package com.hfad.starbuzz;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {

    public static final String EXTRA_DRINKID = "drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkId = (Integer)getIntent().getExtras().get(EXTRA_DRINKID);

        SQLiteOpenHelper starrbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);

        try {
            SQLiteDatabase db = starrbuzzDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("DRINK",
                            new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                            new String[] {Integer.toString(drinkId)},
                            null, null, null);

            if (cursor.moveToFirst()) {
                String nameD = cursor.getString(0);
                String descD = cursor.getString(1);
                int idD = cursor.getInt(2);

                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameD);
                TextView description = (TextView) findViewById(R.id.description);
                description.setText(descD);
                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(idD);
                photo.setContentDescription(nameD);
            }
            cursor.close();
            db.close();
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}