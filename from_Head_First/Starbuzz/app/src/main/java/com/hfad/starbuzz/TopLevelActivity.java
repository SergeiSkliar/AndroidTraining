package com.hfad.starbuzz;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.content.Intent;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.List;

public class TopLevelActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor favoritesCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        setupOprionsListView();
        setupFavoritesListView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favoritesCursor.close();
        db.close();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Cursor newCursor = db.query("DRINK",
                new String[]{"_id", "NAME"},
                "FAVORITE = 1",
                null, null, null, null);
        ListView listFavorites = (ListView) findViewById(R.id.list_favorites);
        CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();
        adapter.changeCursor(newCursor);
        favoritesCursor = newCursor;
    }

    private void setupOprionsListView() {
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View itemView, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);
                }
            }
        };
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }

    private void setupFavoritesListView() {
        ListView listFavorites = (ListView) findViewById(R.id.list_favorites);
        try {
            SQLiteOpenHelper starrbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starrbuzzDatabaseHelper.getReadableDatabase();
            favoritesCursor = db.query("DRINK",
                    new String[]{"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);

            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(TopLevelActivity.this,
                    android.R.layout.simple_list_item_1,
                    favoritesCursor,
                    new String[]{"NAME"},
                    new int[] {android.R.id.text1}, 0);
            listFavorites.setAdapter(favoriteAdapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "DB unavailable", Toast.LENGTH_LONG);
            toast.show();
        }

        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int) id);
                startActivity(intent);
            }
        });
    }
}