package com.gts.coordinator.Activity.UploadDoc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.gts.coordinator.Activity.ActivityDashboard;
import com.gts.coordinator.Model.ContractorData.UploadImage.UplodImageMdel;
import com.gts.coordinator.R;
import com.gts.coordinator.utils.Utils;
import com.sdsmdg.tastytoast.TastyToast;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadVendorDoc extends AppCompatActivity {
    private ImageView select_image_rc, send_image_rc, result_image_rc, select_image_id_proof, send_image_id_proof, result_image_id_proof;
    private String imageName, currentDateTimeString;
    private Uri mCropImageUri;
    private String doc_type, role;
    private String imageFilePath_rc, imageFilePath_id_proof;
    private ProgressBar progressBar_rc, progressBar_id_proof;
    private RelativeLayout image_send_lay_rc, image_send_lay_id_proof;
    long vcabid;
    int index = 0;
    MaterialButton cabCancelBtn;
    private boolean isMandatory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_vendor_doc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ud);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Upload Vendor Document");
        //getActionBar().setTitle();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            role = bundle.getString("role");
            vcabid = bundle.getLong("id");
            isMandatory = bundle.getBoolean("is_mandatory", true);
        }
        bindLayout();
        eventHandaler();
    }

    private void bindLayout() {
        select_image_rc = findViewById(R.id.selected_img_rc);
        send_image_rc = findViewById(R.id.upload_image_rc);
        result_image_rc = findViewById(R.id.checkImage_rc);
        select_image_id_proof = findViewById(R.id.selected_img_id_proof);
        send_image_id_proof = findViewById(R.id.upload_image_id_proof);
        result_image_id_proof = findViewById(R.id.checkImage_id_proof);
        progressBar_id_proof = findViewById(R.id.process_bar_row_id_proof);
        progressBar_rc = findViewById(R.id.process_bar_row_rc);
        image_send_lay_rc = findViewById(R.id.command_panel_rc);
        image_send_lay_id_proof = findViewById(R.id.command_panel_id_proof);
        cabCancelBtn = findViewById(R.id.cab_submit_cancel);
        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
    }

    private void eventHandaler() {
        send_image_rc.setOnClickListener(view -> {
            progressBar_rc.setVisibility(View.VISIBLE);
            send_image_rc.setVisibility(View.GONE);
            new UploadDocAsyncTask().execute(String.valueOf(imageFilePath_rc), "rc", "", "pan");

        });

        send_image_id_proof.setOnClickListener(view -> {
            progressBar_id_proof.setVisibility(View.VISIBLE);
            send_image_id_proof.setVisibility(View.GONE);
            new UploadDocAsyncTask().execute(String.valueOf(imageFilePath_id_proof), "", "idp", "id");
        });
        cabCancelBtn.setOnClickListener(view -> {
            if (cabCancelBtn.getText().toString().equals(getString(R.string.submit))){
                showAlertDialog();
            }else if (cabCancelBtn.getText().toString().equals(getString(R.string.cancel))){
                showDialogBackPrase();
            }
        });
    }

    public void teckVendorDocPic(View view) {
        switch (view.getId()) {
            case R.id.selected_img_rc:
                imageFilePath_rc="";
                send_image_rc.setVisibility(View.GONE);
                //  imageFilePath_rc= new File();
                doc_type = "rc";
                selectImageVendorDoc();
                imageName = "rc";
                break;
           /* case R.id.doc_panel_rc:
                imageFilePath_rc="";
                send_image_rc.setVisibility(View.GONE);
                doc_type = "rc";
                selectImageVendorDoc();
                imageName = "rc";
                break;*/
            case R.id.selected_img_id_proof:
                imageFilePath_id_proof="";
                send_image_id_proof.setVisibility(View.GONE);
                doc_type = "id";
                selectImageVendorDoc();
                imageName = "id_proof";
                break;
            /*case R.id.doc_id_proof:
                imageFilePath_id_proof="";
                send_image_id_proof.setVisibility(View.GONE);
                doc_type = "id";
                selectImageVendorDoc();
                imageName = "id_proof";
                break;*/
        }
    }

    private void selectImageVendorDoc() {
        CropImage.startPickImageActivity(UploadVendorDoc.this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //st3
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }
        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK && result != null) {
                final Uri selectedImage = result.getUri();
                Bitmap bm = BitmapFactory.decodeFile(selectedImage.getPath());
                String file = getFilesDir().getAbsolutePath();
                File dir = new File(file, "imageDir");//create Directory
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File myPath = new File(dir, doc_type + "vendor.jpg");//create a file
                try {
                    FileOutputStream fos = null;
                    fos = new FileOutputStream(myPath);
                    // write image in local data base
                    bm.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                if (imageName.equalsIgnoreCase("rc")) {
                    send_image_rc.setVisibility(View.VISIBLE);
                    String imageFileP = myPath.getAbsolutePath();
                    select_image_rc.setImageBitmap(bm);
                    imageFilePath_rc = imageFileP;//new File(imageFileP);

                } else if (imageName.equalsIgnoreCase("id_proof")) {
                    String imageFileP = myPath.getAbsolutePath();
                    send_image_id_proof.setVisibility(View.VISIBLE);
                    select_image_id_proof.setImageBitmap(bm);
                    imageFilePath_id_proof = imageFileP;// new File(imageFileP);
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        //st2 work after select image
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    public class UploadDocAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                String filePath = strings[0];
                String doc_rc = strings[1];
                String doc_id = strings[2];
                String doc = strings[3];
                uploadImage(filePath, doc_rc, doc_id, doc);
            }catch (Exception e){}

            return null;
        }


    }

    private void uploadImage(String filePath, String doctype_rc, String doctype_id, String doc)  {
        try {
            File file = new File(filePath);
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            RequestBody vcabid_post = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(vcabid));
            RequestBody doc_type_post = RequestBody.create(MediaType.parse("text/plain"), doc);
            RequestBody role = RequestBody.create(MediaType.parse("text/plain"), "vendor");
            MultipartBody.Part body = MultipartBody.Part.createFormData("fileInput", file.getName(), reqFile);

            Log.d("Vendor_id_is", String.valueOf(vcabid));

            RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
            Call<UplodImageMdel> call = apiInterface.uploadImagevendor(body, vcabid_post, doc_type_post, role);
            call.enqueue(new Callback<UplodImageMdel>() {
                @Override
                public void onResponse(Call<UplodImageMdel> call, Response<UplodImageMdel> response) {
                    Log.e("rss", response.toString());
                    if (response.isSuccessful()) {
                        try {
                            UplodImageMdel uplodImageMdel = response.body();
                            String mess = uplodImageMdel.getMessage();
                            Toast.makeText(UploadVendorDoc.this, mess, Toast.LENGTH_SHORT).show();
                            String stetus = uplodImageMdel.getStatus();

                            if (stetus.equals("0")) {
                                runOnUiThread(() -> {
                                    if (doctype_rc.equals("rc")) {
                                        index++;
                                        progressBar_rc.setVisibility(View.GONE);
                                        result_image_rc.setVisibility(View.VISIBLE);
                                    }
                                    if (doctype_id.equals("idp")) {
                                        index++;
                                        progressBar_id_proof.setVisibility(View.GONE);
                                        result_image_id_proof.setVisibility(View.VISIBLE);
                                    }
                                });

                            } else if (stetus.equals("1")) {
                                Toast.makeText(UploadVendorDoc.this, mess, Toast.LENGTH_SHORT).show();
                                sohowLaeoutVisibility(doctype_rc,doctype_id);
                              //  Error writing MIME multipart body part to output stream.
                            }
                            if (index == 2) {
                                cabCancelBtn.setText(getString(R.string.submit));
                            }
                        } catch (Exception e) {
                            sohowLaeoutVisibility(doctype_rc,doctype_id);
                        }
                    }
                }

                @Override
                public void onFailure(Call<UplodImageMdel> call, Throwable t) {
                    Toast.makeText(UploadVendorDoc.this, "onFailure", Toast.LENGTH_SHORT).show();
                    sohowLaeoutVisibility(doctype_rc,doctype_id);
                }
            });
        } catch (Exception e) {
            sohowLaeoutVisibility(doctype_rc,doctype_id);
        }

    }

    private void sohowLaeoutVisibility(String doctype_rc, String doctype_id) {
        if (doctype_rc.equals("rc")) {
            progressBar_rc.setVisibility(View.GONE);
            send_image_rc.setVisibility(View.VISIBLE);
        }
        if (doctype_id.equals("idp")) {
            progressBar_id_proof.setVisibility(View.GONE);
            send_image_id_proof.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        // documents.clear();
        showDialogBackPrase();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //   documents.clear();
        showDialogBackPrase();
        return super.onOptionsItemSelected(item);
    }

    private void showDialogBackPrase() {
        new MaterialAlertDialogBuilder(UploadVendorDoc.this,R.style.AlertDialogTheme)
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();

    }

    private void showAlertDialog() {
        TastyToast.makeText(getApplicationContext(),"All Documents Uploaded Successfully",TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
        Utils.clearUploadDir(getApplicationContext()); // clear all temporary files, used to upload.
        finish();
    }


}
