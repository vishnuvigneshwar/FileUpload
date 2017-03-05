package com.example.android.testfileupload;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.testfileupload.data.Justsend;
import com.example.android.testfileupload.data.MailAttachment;
import com.example.android.testfileupload.rest.ApiClient;
import com.example.android.testfileupload.rest.ApiInterface;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    /**
     * Context Variables
     */
    Context mContext;

    /**
     * Views
     */
    View parentView;
    ImageView imageView;
    TextView textView;

    /**
     * Image path to send
     */
    String imagePath;

    /**
     *
     */
    PermissionsChecker checker;

    /**
     *
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parentView = findViewById(R.id.parentView);
        mContext = getApplicationContext();
        checker = new PermissionsChecker(this);
        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);

        Button fab = (Button) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(imagePath)) {

                    /**
                     * Uploading AsyncTask
                     */

                        /******************Retrofit***************/
                        uploadImage();

                } else {
                    Toast.makeText(mContext, "Attach File to Upload", Toast.LENGTH_SHORT).show();
                }
            }
        });
        showImagePopup(parentView);
    }

    /**
     * Upload Image Client Code
     */
    private void uploadImage() {

        /**
         * Progressbar to Display if you need
         */
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Uploading");
        progressDialog.show();

        //Create Upload Server Client
        ApiInterface service = ApiClient.getApiService();

        //File creating from selected URL
        File file = new File(imagePath);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("attachment[]", file.getName(), requestFile);

        List<MultipartBody.Part> parts = new ArrayList<>();

        parts.add(body);
        parts.add(body);

        String descriptionString1 = "1";
        RequestBody description1 =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString1);
        String descriptionString2 = "2";
        RequestBody description2 =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString2);
        String descriptionString3 = "PA08HOT";
        RequestBody description3 =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString3);
        String descriptionString4 = "TU01HOT";
        RequestBody description4 =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString4);
        String descriptionString5 = "CH04HOT";
        RequestBody description5 =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString5);
        String descriptionString6 = "werong trun 123";
        RequestBody description6 =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString6);



        //parts.add(body);
        Call<MailAttachment> resultCall = service.uploadImage(parts,description1,description2,description3,description4,description5,description6,description6);

        resultCall.enqueue(new Callback<MailAttachment>() {
            @Override
            public void onResponse(Call<MailAttachment> call, Response<MailAttachment> response) {
                Log.v("fileresponce", ""+response.message());
                progressDialog.dismiss();

                // Response Success or Fail
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
                   /* if (response.body().getMessage().equals("success"))
                        Snackbar.make(parentView, R.string.string_upload_success, Snackbar.LENGTH_LONG).show();
                    else
                        Snackbar.make(parentView, R.string.string_upload_fail, Snackbar.LENGTH_LONG).show();*/

                } else {
                    Toast.makeText(mContext, "failed to upload", Toast.LENGTH_SHORT).show();
                }

                /**
                 * Update Views
                 */
                imagePath = "";
                textView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<MailAttachment> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    /**
     * Showing Image Picker
     */
    public void showImagePopup(View view) {
        if (checker.lacksPermissions(PERMISSIONS_READ_STORAGE)) {
            startPermissionsActivity(PERMISSIONS_READ_STORAGE);
        } else {
            // File System.
            final Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_PICK);

            // Chooser of file system options.
            final Intent chooserIntent = Intent.createChooser(galleryIntent, "Choose Image");
            startActivityForResult(chooserIntent, 1010);
        }
    }

    /***
     * OnResult of Image Picked
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1010) {
            if (data == null) {
                Toast.makeText(mContext, "Unable to pick image", Toast.LENGTH_SHORT).show();
                return;
            }
            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);

                Picasso.with(mContext).load(new File(imagePath))
                        .into(imageView);

                Toast.makeText(mContext, "Re select", Toast.LENGTH_SHORT).show();

                cursor.close();

                textView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                Toast.makeText(mContext, "unable to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }





    private void startPermissionsActivity(String[] permission) {
        PermissionsActivity.startActivityForResult(this, 0, permission);
    }
}
