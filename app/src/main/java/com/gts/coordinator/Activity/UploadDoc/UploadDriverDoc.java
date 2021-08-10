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

public class UploadDriverDoc extends AppCompatActivity {
    private ImageView select_image_driver_front, send_image_driver_front, result_image_driver_front, select_image_driver_back, send_image_driver_back,
            result_image_driver_back, select_image_address_proof, send_image_address_proof, result_image_address_proof;
    private String imageName;
    // private Uri mCropImageUri;
    private String doc_type;
    private String imageFilePath_driver_front, imageFilePath_driver_back, imageFilePath_address_proof;
    private ProgressBar progressBar_driver_front, progressBar_driver_back, progressBar_address_proof;
    // private RelativeLayout image_send_lay_driver_front, image_send_lay_address_proof;
    long vcabid;
    int index = 0;
    MaterialButton cabCancelBtn;
    //  private boolean isMandatory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_driver_doc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ud);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Upload Driver Document");
        //getActionBar().setTitle();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //  role = bundle.getString("role");
            vcabid = bundle.getLong("id");
            //  isMandatory = bundle.getBoolean("is_mandatory", true);
        }
        bindLayout();
        eventHandaler();
    }

    private void bindLayout() {
        select_image_driver_front = findViewById(R.id.selected_img_driving_licence_front);
        send_image_driver_front = findViewById(R.id.upload_image_driving_licence_front);
        result_image_driver_front = findViewById(R.id.checkImage_driving_licence_front);

        select_image_driver_back = findViewById(R.id.selected_img_driving_licence_back);
        send_image_driver_back = findViewById(R.id.upload_image_driving_licence_back);
        result_image_driver_back = findViewById(R.id.checkImage_driving_licence_back);

        select_image_address_proof = findViewById(R.id.selected_img_address_proof);
        send_image_address_proof = findViewById(R.id.upload_image_address_proof);
        result_image_address_proof = findViewById(R.id.checkImage_address_proof);
        progressBar_address_proof = findViewById(R.id.process_bar_row_address_proof);
        progressBar_driver_front = findViewById(R.id.process_bar_row_driving_licence_front);
        progressBar_driver_back = findViewById(R.id.process_bar_row_driving_licence_back);
        // image_send_lay_driver_front = findViewById(R.id.command_panel_driver_front);

        cabCancelBtn = findViewById(R.id.cab_submit_cancel);
        // currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
    }

    private void eventHandaler() {
        send_image_driver_front.setOnClickListener(view -> {
            progressBar_driver_front.setVisibility(View.VISIBLE);
            send_image_driver_front.setVisibility(View.GONE);
            new UploadDocAsyncTask().execute(String.valueOf(imageFilePath_driver_front), "licf", "", "", "dl");

        });
        send_image_driver_back.setOnClickListener(view -> {
            progressBar_driver_back.setVisibility(View.VISIBLE);
            send_image_driver_back.setVisibility(View.GONE);
            new UploadDocAsyncTask().execute(String.valueOf(imageFilePath_driver_back), "", "licb", "", "dl_bk");

        });

        send_image_address_proof.setOnClickListener(view -> {
            progressBar_address_proof.setVisibility(View.VISIBLE);
            send_image_address_proof.setVisibility(View.GONE);
            new UploadDocAsyncTask().execute(String.valueOf(imageFilePath_address_proof), "", "", "adf", "add");
        });
        cabCancelBtn.setOnClickListener(view -> {
                    if (cabCancelBtn.getText().toString().equals("Submit")) {
                        TastyToast.makeText(getApplicationContext(), "All Documents Uploaded Successfully", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                        Utils.clearUploadDir(getApplicationContext());
                        finish();
                    } else showDialogBackPrase();
                }
        );
    }

    public void teckVendorDocPic(View view) {
        switch (view.getId()) {
            case R.id.selected_img_driving_licence_front:
                drivingLycFrunt();
                break;
           /* case R.id.doc_driving_licence_front:
              drivingLycFrunt();
                break;*/
            case R.id.selected_img_driving_licence_back:
                drivingLycBack();
                break;
            /*case R.id.doc_driving_licence_back:
               drivingLycBack();
                break;*/
            case R.id.selected_img_address_proof:
                addressProof();
                break;
            /*case R.id.doc_address_proof:
                addressProof();
                break;*/
        }
    }

    private void addressProof() {
        imageFilePath_address_proof = "";
        send_image_address_proof.setVisibility(View.GONE);
        doc_type = "add";
        selectImageVendorDoc();
        imageName = "adf";

    }

    private void drivingLycBack() {
        imageFilePath_driver_back = "";
        send_image_driver_back.setVisibility(View.GONE);
        doc_type = "dl_bk";
        selectImageVendorDoc();
        imageName = "licb";
    }

    private void drivingLycFrunt() {
        imageFilePath_driver_front = "";
        send_image_driver_front.setVisibility(View.GONE);
        doc_type = "dl";
        selectImageVendorDoc();
        imageName = "licf";
    }

    private void selectImageVendorDoc() {
        CropImage.startPickImageActivity(UploadDriverDoc.this);
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
                // mCropImageUri = imageUri;
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
                File myPath = new File(dir, doc_type + "driver.jpg");//create a file
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
                if (imageName.equalsIgnoreCase("licf")) {
                    send_image_driver_front.setVisibility(View.VISIBLE);
                    String imageFileP = myPath.getAbsolutePath();
                    select_image_driver_front.setImageBitmap(bm);
                    imageFilePath_driver_front = imageFileP;

                } else if (imageName.equalsIgnoreCase("licb")) {
                    send_image_driver_back.setVisibility(View.VISIBLE);
                    String imageFileP = myPath.getAbsolutePath();
                    select_image_driver_back.setImageBitmap(bm);
                    imageFilePath_driver_back = imageFileP;

                } else if (imageName.equalsIgnoreCase("adf")) {
                    String imageFileP = myPath.getAbsolutePath();
                    send_image_address_proof.setVisibility(View.VISIBLE);
                    select_image_address_proof.setImageBitmap(bm);
                    imageFilePath_address_proof = imageFileP;
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
                String doc_driver_front = strings[1];
                String doc_driver_back = strings[2];
                String adddess_proof = strings[3];
                String doc = strings[4];
                uploadImage(filePath, doc_driver_front, doc_driver_back, adddess_proof, doc);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }
    }

    private void uploadImage(String filePath, String doctype_driver_front, String doctype_driver_back, String address_proof, String doc) {
        try {
            File file = new File(filePath);
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            RequestBody vcabid_post = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(vcabid));
            RequestBody doc_type_post = RequestBody.create(MediaType.parse("text/plain"), doc);
            RequestBody role = RequestBody.create(MediaType.parse("text/plain"), "driver");
            MultipartBody.Part body = MultipartBody.Part.createFormData("fileInput", file.getName(), reqFile);

            Log.d("upload_doc File Path",file.toString());
            Log.d("upload_doc vcabid_post", String.valueOf(vcabid));
            Log.d("upload_doc docType",doc.toString());
            Log.d("upload_doc role","driver");


            RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
            Call<UplodImageMdel> call = apiInterface.uploadImage(body, vcabid_post, doc_type_post, role);
            call.enqueue(new Callback<UplodImageMdel>() {
                @Override
                public void onResponse(Call<UplodImageMdel> call, Response<UplodImageMdel> response) {
                    Log.e("rss", response.toString());
                    if (response.isSuccessful()) {
                        try {
                            UplodImageMdel uplodImageMdel = response.body();
                            String mess = uplodImageMdel.getMessage();
                            Toast.makeText(UploadDriverDoc.this, mess, Toast.LENGTH_SHORT).show();
                            String stetus = uplodImageMdel.getStatus();

                            if (stetus.equals("0")) {
                                runOnUiThread(() -> {
                                    if (doctype_driver_front.equals("licf")) {
                                        index++;
                                        progressBar_driver_front.setVisibility(View.GONE);
                                        result_image_driver_front.setVisibility(View.VISIBLE);
                                    }
                                    if (doctype_driver_back.equals("licb")) {
                                        index++;
                                        progressBar_driver_back.setVisibility(View.GONE);
                                        result_image_driver_back.setVisibility(View.VISIBLE);
                                    }
                                    if (address_proof.equals("adf")) {
                                        index++;
                                        progressBar_address_proof.setVisibility(View.GONE);
                                        result_image_address_proof.setVisibility(View.VISIBLE);
                                    }
                                });

                            } else if (stetus.equals("1")) {
                                Toast.makeText(UploadDriverDoc.this, mess, Toast.LENGTH_SHORT).show();
                                sohowLaeoutVisibility(doctype_driver_front, doctype_driver_back, address_proof);
                            }
                            if (index == 3) {
                                cabCancelBtn.setText("Submit");

                                // Toast.makeText(UploadDriverDoc.this, "", Toast.LENGTH_SHORT).show();
                                //  showAlertDialog();
                            }
                        } catch (Exception e) {
                            sohowLaeoutVisibility(doctype_driver_front, doctype_driver_back, address_proof);
                        }
                    }
                }


                @Override
                public void onFailure(Call<UplodImageMdel> call, Throwable t) {
                    Toast.makeText(UploadDriverDoc.this, "onFailure", Toast.LENGTH_SHORT).show();
                    sohowLaeoutVisibility(doctype_driver_front, doctype_driver_back, address_proof);
                }
            });

        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        // documents.clear();
        showDialogBackPrase();
    }

    private void sohowLaeoutVisibility(String doctype_driver_front, String doctype_driver_back, String address_proof) {
        if (doctype_driver_front.equals("licf")) {
            progressBar_driver_front.setVisibility(View.GONE);
            send_image_driver_front.setVisibility(View.VISIBLE);
        }
        if (doctype_driver_back.equals("licb")) {
            progressBar_driver_back.setVisibility(View.GONE);
            send_image_driver_back.setVisibility(View.VISIBLE);
        }
        if (address_proof.equals("adf")) {
            progressBar_address_proof.setVisibility(View.GONE);
            send_image_address_proof.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //   documents.clear();
        showDialogBackPrase();
        return super.onOptionsItemSelected(item);
    }

    private void showDialogBackPrase() {

        new MaterialAlertDialogBuilder(UploadDriverDoc.this,R.style.AlertDialogTheme)
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
      /*  AlertDialog.Builder builder = new AlertDialog.Builder(UploadDriverDoc.this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();*/
    }

   /* private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadDriverDoc.this);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.fragment_message_dialog, null, false);
        ImageView imageView = view.findViewById(R.id.id_image_view_dialog);
        TextView message = view.findViewById(R.id.message);
        TextView title = view.findViewById(R.id.title);
        Button exit = view.findViewById(R.id.btn_close);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_successful_icon));
        title.setText(getString(R.string.info));
        message.setText("All Documents Uploaded Successfully");
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.clearUploadDir(UploadDriverDoc.this); // clear all temporary files, used to upload.
                finish();
            }
        });
        AlertDialog dialog;
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.setView(view);
        dialog.show();
    }*/

    //421
}
