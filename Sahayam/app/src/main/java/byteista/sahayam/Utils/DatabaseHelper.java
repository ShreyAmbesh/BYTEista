package byteista.sahayam.Utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by Lykan on 9-02-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASENAME="sahayam";
    private static final int DATABASEVERSION=1;
    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String querry2="CREATE TABLE IF NOT EXISTS 'my_profile'" +
                " ('name' varchar(150) NOT NULL," +
                "'date_of_birth' date NOT NULL," +
                "'gender' char(1) NOT NULL," +
                "'usn' varchar(10) NOT NULL," +
                "'semester' char(1) NOT NULL," +
                "'branch' char(4) NOT NULL," +
                "'interests' varchar NOT NULL," +
                "'hostelite' char(1) NOT NULL," +
                "'phone_no' varchar(15) NOT NULL," +
                "'profile_pic' Varchar(10) DEFAULT NULL);";
        String querry3="CREATE TABLE IF NOT EXISTS 'library'" +
                " ('_id' int AUTO INCREMENT PRIMARY KEY," +
                "'book_barcode' varchar NOT NULL," +
                "'returndate' timestamp NOT NULL," +
                "'book_nqame' char(1) NOT NULL);";
        String querry4="CREATE TABLE IF NOT EXISTS 'mess'" +
                " ('_id' int AUTO INCREMENT PRIMARY KEY," +
                "'date' timestamp UNIQUE);";

        try {
            db.execSQL(querry2);
            db.execSQL(querry3);
            db.execSQL(querry4);

        }catch (SQLException e)
        {
            Log.d("sql error",e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {




    }
}
