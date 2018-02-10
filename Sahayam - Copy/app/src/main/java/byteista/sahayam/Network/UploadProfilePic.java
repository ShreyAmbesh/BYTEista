package byteista.sahayam.Network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;

import byteista.sahayam.MainActivity.MainActivity;
import byteista.sahayam.NavigationDrawer.NavigationDrawerFragment;
import byteista.sahayam.Utils.DatabaseHelper;
import id.zelory.compressor.Compressor;

public class UploadProfilePic extends AsyncTask<String, String, String> {

    private final String jabberId;
    private final Context context;
    File sourceFile;
    int totalSize = 0;
    String FILE_UPLOAD_URL = "http://35.187.242.68/byteista/UploadProfilePic.php";
    String imagepath;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    ProgressDialog dialog;
    //Bitmap sourceBitmap;
    private static final String MY_PREFERENCES = "my_preferences";
    SharedPreferences reader;

    public UploadProfilePic(String path, String jabberId, Context context) {
        imagepath=path;
        this.jabberId=jabberId;
        this.context=context;
        dialog=new ProgressDialog(context);
        reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        // setting progress bar to zero
        //donut_progress.setProgress(0);
        //uploader_area.setVisibility(View.GONE); // Making the uploader area screen invisible
        //progress_area.setVisibility(View.VISIBLE); // Showing the stylish material progressbar

        try {
            sourceFile = new Compressor(context).compressToFile(new File(imagepath));
            //sourceBitmap = new Compressor(context).compressToBitmap(new File(imagepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        totalSize = (int)sourceFile.length();
        dialog.show();
        DatabaseHelper add=new DatabaseHelper(context);
        SQLiteDatabase database = add.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put("profile_pic","profilePic");
        try {
            File file = new File("profilePic");
            if(file.exists()){
                context.deleteFile("profilePic");}
            final SharedPreferences.Editor editor = reader.edit();
            editor.putString("profile_pic", "profile_pic");
            editor.commit();
            FileOutputStream fileOutputStream=new FileOutputStream(Environment.getExternalStorageDirectory()+"/profile_pic");
            copyFile(sourceFile,fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            database.update("my_profile", cv,"phone_no=?",new String[]{jabberId});
            if (reader.getBoolean("is_First",true)) {

                if (reader.getBoolean("MainActivity", false)) {
                    NavigationDrawerFragment.adapter.getImagePath();
                    NavigationDrawerFragment.adapter.notifyDataSetChanged();
                    ((Activity) context).finish();
                } else {
                    final SharedPreferences.Editor editor = reader.edit();
                    editor.putBoolean("is_first", false);
                    editor.commit();

                }
                database.close();
            }
        }catch (SQLException e)
        {
            Log.d("sql error",e.getMessage());
        }
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        Log.d("PROG", progress[0]);

        dialog.setMessage("Uploading.....progress"+progress[0]+"%");

    }

    @Override
    protected String doInBackground(String... args) {
        HttpURLConnection.setFollowRedirects(false);
        HttpURLConnection connection = null;
        String fileName = sourceFile.getName();

        try {
            connection = (HttpURLConnection) new URL(FILE_UPLOAD_URL).openConnection();
            connection.setRequestMethod("POST");
            String tail = "\r\n--" + boundary + "--\r\n";
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            connection.setDoInput(true); // Allow Inputs
            connection.setDoOutput(true); // Allow Outputs
            connection.setUseCaches(false); // Don't use a Cached Copy
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");









            String jab=twoHyphens + boundary + lineEnd+"Content-Disposition: form-data; name=\"jabberId\"" + lineEnd
                    +"Content-Type: text/plain; charset=UTF-8" + lineEnd+
                    "Content-Length: " + jabberId.length() + lineEnd+
                    lineEnd+
                    jabberId+
                    lineEnd;
            String metadataPart = "--" + boundary + "\r\n"
                    + "Content-Disposition: form-data; name=\"metadata\"\r\n\r\n"
                    + "" + "\r\n";

            String fileHeader1 = "--" + boundary + "\r\n"
                    + "Content-Disposition: form-data; name=\"fileToUpload\"; filename=\""
                    + fileName + "\"\r\n"
                    + "Content-Type: application/octet-stream\r\n"
                    + "Content-Transfer-Encoding: binary\r\n";

            long fileLength = sourceFile.length() + tail.length();
            String fileHeader2 = "Content-length: " + fileLength + "\r\n";
            String fileHeader = fileHeader1 + fileHeader2 + "\r\n";
            String stringData = metadataPart+jab+ fileHeader;

            long requestLength = stringData.length() + fileLength;
            connection.setRequestProperty("Content-length", "" + requestLength);
            connection.setFixedLengthStreamingMode((int) requestLength);
            connection.connect();

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(stringData);
            out.flush();


            int progress = 0;
            int bytesRead = 0;
            byte buf[] = new byte[1024];

            BufferedInputStream bufInput = new BufferedInputStream(new FileInputStream(sourceFile));
            while ((bytesRead = bufInput.read(buf)) != -1) {
                // write output
                out.write(buf, 0, bytesRead);
                out.flush();
                progress += bytesRead; // Here progress is total uploaded bytes

                publishProgress(""+(int)((progress*100)/totalSize)); // sending progress percent to publishProgress
            }

            // Write closing boundary and close stream
            out.writeBytes(tail);
            out.flush();
            out.close();

            // Get server response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
            Log.e("Response", "Response from server: " + builder);
        } catch (Exception e) {
            // Exception
        } finally {
            if (connection != null) connection.disconnect();
        }
        return null;
    }



    @Override
    protected void onPostExecute(String result) {
        Log.e("Response", "Response from server: " + result);

        context.startActivity(new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        ((Activity) context).finish();



        dialog.dismiss();
        super.onPostExecute(result);
    }

    private void copyFile(File sourceF, FileOutputStream destFile) throws IOException {
        if (!sourceF.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceF).getChannel();
        destination = destFile.getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }


    }

}