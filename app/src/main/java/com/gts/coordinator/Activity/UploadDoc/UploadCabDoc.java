package com.gts.coordinator.Activity.UploadDoc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.gts.coordinator.Model.ContractorData.UploadImage.UplodImageMdel;
import com.gts.coordinator.R;
import com.gts.coordinator.utils.Utils;
import com.sdsmdg.tastytoast.TastyToast;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadCabDoc extends AppCompatActivity {
    //rss
    private ImageView select_image_rc, send_image_rc, result_image_rc, select_image_insurance, send_image_insurance, result_image_insurance, select_image_state_permit, send_image_state_permit, result_image_state_permit, select_image_fitness_certificate, send_image_fitness_certificate, result_image_fitness_certificate, select_image_all_india_permit, send_image_all_india_permit, result_image_all_india_permit;
    //  MyReceiver myReceiver;
    public static final String FILTER_ACTION_KEY = "any_key";
    private static final String TAG = "MyTag";
    private String imageName;
    private String doc_type;
    private String imageFilePath_rc, imageFilePath_insurance, imageFilePath_state_permit, imageFilePath_fitness_certificate, imageFilePath_all_india_permit;
    private ProgressBar progressBar_rc, progressBar_insurance, progressBar_state_permit, progressBar_fitness_certificate, progressBar_all_india_permit;
    long vcabid = 0;
    int index = 0;
    private MaterialButton cabCancelBtn = null;
    private boolean upload_stetus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_cab_doc);
      //  Mint.initAndStartSession(this.getApplication(), "4b1e42e1");
        Toolbar toolbar =  findViewById(R.id.toolbar_ud);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Upload Cab Document");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            vcabid = bundle.getLong("id");
        }

        bindLayout();
        eventHandaler();
    }

    private void bindLayout() {
        select_image_rc = findViewById(R.id.selected_img_rc);
        send_image_rc = findViewById(R.id.upload_image_rc);
        result_image_rc = findViewById(R.id.checkImage_rc);

        select_image_state_permit = findViewById(R.id.selected_img_state_permit);
        send_image_state_permit = findViewById(R.id.upload_image_state_permit);
        result_image_state_permit = findViewById(R.id.checkImage_state_permit);

        select_image_fitness_certificate = findViewById(R.id.selected_img_fitness_certificate);
        send_image_fitness_certificate = findViewById(R.id.upload_image_fitness_certificate);
        result_image_fitness_certificate = findViewById(R.id.checkImage_fitness_certificate);

        select_image_all_india_permit = findViewById(R.id.selected_img_all_india_permit);
        send_image_all_india_permit = findViewById(R.id.upload_image_all_india_permit);
        result_image_all_india_permit = findViewById(R.id.checkImage_all_india_permit);

        select_image_insurance = findViewById(R.id.selected_img_insurance);
        send_image_insurance = findViewById(R.id.upload_image_insurance);
        result_image_insurance = findViewById(R.id.checkImage_insurance);
        progressBar_insurance = findViewById(R.id.process_bar_row_insurance);
        progressBar_rc = findViewById(R.id.process_bar_row_rc);
        progressBar_state_permit = findViewById(R.id.process_bar_row_state_permit);
        progressBar_fitness_certificate = findViewById(R.id.process_bar_row_fitness_certificate);
        progressBar_all_india_permit = findViewById(R.id.process_bar_row_all_india_permit);

        cabCancelBtn = findViewById(R.id.cab_submit_cancel);
        select_image_rc = findViewById(R.id.selected_img_rc);
        send_image_rc = findViewById(R.id.upload_image_rc);
        result_image_rc = findViewById(R.id.checkImage_rc);
        // currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
    }

    private void eventHandaler() {
        //rss
        send_image_rc.setOnClickListener(view -> {
            try {

                if (upload_stetus){
                    progressBar_rc.setVisibility(View.VISIBLE);
                    send_image_rc.setVisibility(View.GONE);
              /*  Intent intent = new Intent(getApplicationContext(),UploadDecumentService.class);
                intent.putExtra("filepath", imageFilePath_rc);
                intent.putExtra("vcabid",vcabid);
                startService(intent);*/
                    //intent.putExtra()
                    // taskImageRc.execute(String.valueOf(imageFilePath_rc), "rc", "", "", "", "", "rc");
                    new UploadDocAsyncTask().execute(String.valueOf(imageFilePath_rc), "rcdata", "", "", "", "", "rc");

                }else {
                    Toast.makeText(this, "Please Wait", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
            }

        });

        send_image_insurance.setOnClickListener(view -> {
            try {
                if (upload_stetus){
                    progressBar_insurance.setVisibility(View.VISIBLE);
                    send_image_insurance.setVisibility(View.GONE);
                    new UploadDocAsyncTask().execute(String.valueOf(imageFilePath_insurance), "", "idp", "", "", "", "insurance");

                }else {
                    Toast.makeText(this, "Please Wait", Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
            }
        });
        send_image_state_permit.setOnClickListener(view -> {
            try {
                if (upload_stetus){
                    progressBar_state_permit.setVisibility(View.VISIBLE);
                    send_image_state_permit.setVisibility(View.GONE);
                    new UploadDocAsyncTask().execute(String.valueOf(imageFilePath_state_permit), "", "", "stp", "", "", "state_permit");

                }else {
                    Toast.makeText(this, "Please Wait", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
            }
        });
        send_image_fitness_certificate.setOnClickListener(view -> {
            try {
                if (upload_stetus){
                    progressBar_fitness_certificate.setVisibility(View.VISIBLE);
                    send_image_fitness_certificate.setVisibility(View.GONE);
                    new UploadDocAsyncTask().execute(String.valueOf(imageFilePath_fitness_certificate), "", "", "", "fit", "", "fitness");

                }else {
                    Toast.makeText(this, "Please Wait", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
            }
        });
        send_image_all_india_permit.setOnClickListener(view -> {
            try {
                if (upload_stetus){
                    progressBar_all_india_permit.setVisibility(View.VISIBLE);
                    send_image_all_india_permit.setVisibility(View.GONE);
                    new UploadDocAsyncTask().execute(String.valueOf(imageFilePath_all_india_permit), "", "", "", "", "allp", "all_ind_permit");

                }else {
                    Toast.makeText(this, "Please Wait", Toast.LENGTH_SHORT).show();
                }
                } catch (Exception e) {
            }

        });


        cabCancelBtn.setOnClickListener(view -> {
            if (cabCancelBtn.getText().toString().equals(getString(R.string.submit))) {
                showAlertDialog();
            } else if (cabCancelBtn.getText().toString().equals(getString(R.string.cancel))) {
                showDialogBackPrase();
            }
        });
    }

    public void teckVendorDocPic(View view) {
        switch (view.getId()) {
            case R.id.selected_img_rc:
                selected_img_rc();
                break;
            /*case R.id.doc_panel_rc:
                selected_img_rc();
                break;*/
            case R.id.selected_img_insurance:
                selected_img_insurance();
                break;
            /*case R.id.doc_insurance:
                selected_img_insurance();
                break;*/
            case R.id.selected_img_state_permit:
                doc_state_permit();
                break;
           /* case R.id.selected_img_state_permit:
                doc_state_permit();
                break;*/
            case R.id.selected_img_fitness_certificate:
                selected_img_fitness_certificate();
                break;
           /* case R.id.doc_fitness_certificate:
                selected_img_fitness_certificate();
                break;*/
            case R.id.selected_img_all_india_permit:
                selected_img_all_india_permit();
                break;
           /* case R.id.doc_all_india_permit:
                selected_img_all_india_permit();
                break;*/
        }
    }

    private void selected_img_all_india_permit() {
        try {
            imageFilePath_all_india_permit = "";
            send_image_all_india_permit.setVisibility(View.GONE);
            doc_type = "all_ind_permit";
            imageName = "allp";
            selectImageVendorDoc();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void selected_img_fitness_certificate() {
        try {
            imageFilePath_fitness_certificate = "";
            send_image_fitness_certificate.setVisibility(View.GONE);
            doc_type = "fitness";
            imageName = "fit";
            selectImageVendorDoc();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void doc_state_permit() {
        try {
            imageFilePath_state_permit = "";
            send_image_state_permit.setVisibility(View.GONE);
            doc_type = "state_permit";
            imageName = "stp";
            selectImageVendorDoc();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void selected_img_insurance() {
        try {
            imageFilePath_insurance = "";
            send_image_insurance.setVisibility(View.GONE);
            doc_type = "insurance";
            imageName = "insu";
            selectImageVendorDoc();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void selected_img_rc() {
        try {
            imageFilePath_rc = "";
            send_image_rc.setVisibility(View.GONE);
            doc_type = "rc";
            imageName = "rci";
            selectImageVendorDoc();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void selectImageVendorDoc() {
        CropImage.startPickImageActivity(UploadCabDoc.this);
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
                //    mCropImageUri = imageUri;
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
                File myPath = new File(dir, doc_type + "cab_.jpg");//create a file
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
                if (imageName.equalsIgnoreCase("rci")) {
                    result_image_rc.setVisibility(View.GONE);
                    send_image_rc.setVisibility(View.VISIBLE);
                    String imageFileP = myPath.getAbsolutePath();
                    select_image_rc.setImageBitmap(bm);
                    imageFilePath_rc = imageFileP;

                } else if (imageName.equalsIgnoreCase("insu")) {
                    send_image_insurance.setVisibility(View.VISIBLE);
                    result_image_insurance.setVisibility(View.GONE);
                    String imageFileP = myPath.getAbsolutePath();
                    select_image_insurance.setImageBitmap(bm);
                    imageFilePath_insurance = imageFileP;
                } else if (imageName.equalsIgnoreCase("stp")) {
                    result_image_state_permit.setVisibility(View.GONE);
                    send_image_state_permit.setVisibility(View.VISIBLE);
                    String imageFileP = myPath.getAbsolutePath();
                    select_image_state_permit.setImageBitmap(bm);
                    imageFilePath_state_permit = imageFileP;
                } else if (imageName.equalsIgnoreCase("fit")) {
                    result_image_fitness_certificate.setVisibility(View.GONE);
                    send_image_fitness_certificate.setVisibility(View.VISIBLE);
                    String imageFileP = myPath.getAbsolutePath();
                    select_image_fitness_certificate.setImageBitmap(bm);
                    imageFilePath_fitness_certificate = imageFileP;
                } else if (imageName.equalsIgnoreCase("allp")) {
                    result_image_all_india_permit.setVisibility(View.GONE);
                    send_image_all_india_permit.setVisibility(View.VISIBLE);
                    String imageFileP = myPath.getAbsolutePath();
                    select_image_all_india_permit.setImageBitmap(bm);
                    imageFilePath_all_india_permit = imageFileP;
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
                String doc_stp = strings[3];
                String doc_fits = strings[4];
                String doc_allp = strings[5];
                String doc = strings[6];
                callApiUploadImage(filePath, doc_rc, doc_id, doc_stp, doc_fits, doc_allp, doc);
            } catch (Exception e) {
            }
            return "Complete";
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(UploadCabDoc.this, "Task Cancel", Toast.LENGTH_SHORT).show();
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(UploadCabDoc.this, "Task " + s, Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            upload_stetus =false;
            super.onPreExecute();
        }
    }

    private void callApiUploadImage(String filePath, String doctype_rc, String doctype_id, String statePermit, String fitness, String allper, String doc) {
        try {
            File file = new File(filePath);
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            RequestBody vcabid_post = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(vcabid));
            RequestBody doc_type_post = RequestBody.create(MediaType.parse("text/plain"), doc);
            RequestBody role = RequestBody.create(MediaType.parse("text/plain"), "cab");
            MultipartBody.Part body = MultipartBody.Part.createFormData("fileInput", file.getName(), reqFile);
            RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
            Call<UplodImageMdel> call = apiInterface.uploadImagecab(body, vcabid_post, doc_type_post, role);
            call.enqueue(new Callback<UplodImageMdel>() {
                @Override
                public void onResponse(Call<UplodImageMdel> call, Response<UplodImageMdel> response) {
                    Log.e("rss", response.toString());
                    if (response.isSuccessful()) {
                        try {
                            upload_stetus =true;
                            UplodImageMdel uplodImageMdel = response.body();
                            String mess = uplodImageMdel.getMessage();
                            Toast.makeText(UploadCabDoc.this, mess, Toast.LENGTH_SHORT).show();
                            String stetus = uplodImageMdel.getStatus();

                            if (stetus.equals("0")) {
                                runOnUiThread(() -> {
                                    if (doctype_rc.equals("rcdata")) {
                                        index++;
                                        progressBar_rc.setVisibility(View.GONE);
                                        result_image_rc.setVisibility(View.VISIBLE);
                                        // taskImageRc.cancel(true);
                                    }
                                    if (doctype_id.equals("idp")) {
                                        index++;
                                        progressBar_insurance.setVisibility(View.GONE);
                                        result_image_insurance.setVisibility(View.VISIBLE);
                                    }
                                    if (statePermit.equals("stp")) {
                                        index++;
                                        progressBar_state_permit.setVisibility(View.GONE);
                                        result_image_state_permit.setVisibility(View.VISIBLE);
                                    }
                                    if (fitness.equals("fit")) {
                                        index++;
                                        progressBar_fitness_certificate.setVisibility(View.GONE);
                                        result_image_fitness_certificate.setVisibility(View.VISIBLE);
                                    }
                                    if (allper.equals("allp")) {
                                        index++;
                                        progressBar_all_india_permit.setVisibility(View.GONE);
                                        result_image_all_india_permit.setVisibility(View.VISIBLE);
                                    }
                                });

                            } else if (stetus.equals("1")) {
                                Toast.makeText(UploadCabDoc.this, mess, Toast.LENGTH_SHORT).show();
                                sohowLaeoutVisibility(doctype_rc, doctype_id, statePermit, fitness, allper);
                            }
                            if (index == 5) {
                                cabCancelBtn.setText(getString(R.string.submit));
                            }
                        } catch (Exception e) {
                            sohowLaeoutVisibility(doctype_rc, doctype_id, statePermit, fitness, allper);
                        }
                    }
                }

                @Override
                public void onFailure(Call<UplodImageMdel> call, Throwable t) {
                    Toast.makeText(UploadCabDoc.this, "onFailure", Toast.LENGTH_SHORT).show();
                    sohowLaeoutVisibility(doctype_rc, doctype_id, statePermit, fitness, allper);
                }
            });
        } catch (Exception e) {
            sohowLaeoutVisibility(doctype_rc, doctype_id, statePermit, fitness, allper);
        }

    }

    private void sohowLaeoutVisibility(String doctype_rc, String doctype_id, String statePermit, String fitness, String allper) {
        if (doctype_rc.equals("rcdata")) {
            progressBar_rc.setVisibility(View.GONE);
            send_image_rc.setVisibility(View.VISIBLE);
        }
        if (doctype_id.equals("idp")) {
            progressBar_insurance.setVisibility(View.GONE);
            send_image_insurance.setVisibility(View.VISIBLE);
        }
        if (statePermit.equals("stp")) {
            progressBar_state_permit.setVisibility(View.GONE);
            send_image_state_permit.setVisibility(View.VISIBLE);
        }
        if (fitness.equals("fit")) {
            progressBar_fitness_certificate.setVisibility(View.GONE);
            send_image_fitness_certificate.setVisibility(View.VISIBLE);
        }
        if (allper.equals("allp")) {
            progressBar_all_india_permit.setVisibility(View.GONE);
            send_image_all_india_permit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        showDialogBackPrase();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showDialogBackPrase();
        return super.onOptionsItemSelected(item);
    }

    private void showDialogBackPrase() {
        new MaterialAlertDialogBuilder(UploadCabDoc.this,R.style.AlertDialogTheme)
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
        Utils.clearUploadDir(getApplicationContext());
        // clear all temporary files, used to upload.
        finish();
    }
}
// - 585