package com.example.findme;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterMissing extends AppCompatActivity {
    Button uploadimg;
    ImageView image;
    EditText nameofmissing, placeofmissing,phone,age;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    StorageReference reference = FirebaseStorage.getInstance().getReference();
    String api_key = "e7ns9sqdmrkv4hfq2f7ooscujn";
    String api_secret = "rauoedqnn297ba225uflrgoumg";
    Uri ImgUri;
    ProgressBar prog;
    String filePath;
    Map<String, String> parameters = new HashMap<>();
    Detectinterface detectinterface;
    private String pid;
    private String URL = "https://api.skybiometry.com/fc/faces/detect?api_key=e7ns9sqdmrkv4hfq2f7ooscujn&api_secret=rauoedqnn297ba225uflrgoumg";
    private Bitmap bitmap;
    private String tid;
    private String uid;
    private byte[] imgByte;
    ActivityResultLauncher mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if (result != null) {
                image.setImageURI(result);
                ImgUri = result;

                File file = new File(result.getPath());//create path from uri
                final String[] split = file.getPath().split(":");//split the path.
                filePath = split[1];//assign it to a string(your choice).
                try {
                    InputStream inputStream = getContentResolver().openInputStream(result);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("fffffffffff", bitmap.toString());
            }

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_missing);
        uploadimg = (Button) findViewById(R.id.uploadbtn);
        image = (ImageView) findViewById(R.id.image);
        nameofmissing = (EditText) findViewById(R.id.nameofmissing);
        placeofmissing = (EditText) findViewById(R.id.placeofmissing);
        phone = (EditText) findViewById(R.id.Phone);
        age=(EditText)findViewById(R.id.age);
        prog = (ProgressBar) findViewById(R.id.prog);
        prog.setVisibility(View.INVISIBLE);
        requestRead();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");


            }
        });
        uploadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ImgUri != null) {
                    uploadtofirebase(ImgUri);
                    try {
                        upload();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(RegisterMissing.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void uploadtofirebase(Uri uri) {
        StorageReference fileRef = reference.child("images/*" + UUID.randomUUID().toString() + ".png");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Detectinterface detectinterface = Apiclient.getRetrofit().create(Detectinterface.class);

                        Toast.makeText(RegisterMissing.this, "image set in database", Toast.LENGTH_SHORT).show();

                        parameters.put("api_key", "e7ns9sqdmrkv4hfq2f7ooscujn");
                        parameters.put("api_secret", "rauoedqnn297ba225uflrgoumg");
                        parameters.put("Urls", "https://firebasestorage.googleapis.com/v0/b/findme-8939c.appspot.com/o/images%2F*f59e0904-3703-4bbe-bb2c-72b050d6a284?alt=media&token=9db54894-8263-4ac7-85a1-b5f10dc99b64");


                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                Toast.makeText(RegisterMissing.this, "Progress going on", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterMissing.this, "Upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestRead() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // Permission Denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void upload() throws IOException {
        File file = new File(filePath);
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.Companion.create(MediaType.parse("image/jpeg"), file))
                .build();
        Request request = new Request.Builder().url(URL).post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("jjjjjjjjjj", e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d("hhhhhhhhhhhh", response.toString());
                Log.d("hhhhhhhhhhhh", myResponse);

                try {
                    JSONObject object = new JSONObject(myResponse);
                    pid = object.getJSONArray("photos").getJSONObject(0).getString("pid");
                   tid=object.getJSONArray("photos").getJSONObject(0).getJSONArray("tags").getJSONObject(0).getString("tid");
                       save();
                    Log.d("hhhhhhhhhhhhhhh", tid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Model model = new Model(pid, nameofmissing.getText().toString(), placeofmissing.getText().toString(),phone.getText().toString(),age.getText().toString());
                String Modelid = root.push().getKey();
                root.child(Modelid).setValue(model);

            }
        });
    }
    public void save(){
        Saveinterfacce saveinterface=Apiclient.getRetrofit().create(Saveinterfacce.class);
         uid=nameofmissing.getText().toString().trim();
        uid = uid.replaceAll("\\s.*", "");
        uid+="@firstone";
         System.out.println(uid);
        retrofit2.Call<Savereceiver> savereceiverCall=saveinterface.savePosts(api_key,api_secret,uid,tid);
        savereceiverCall.enqueue(new retrofit2.Callback<Savereceiver>() {
            @Override
            public void onResponse(retrofit2.Call<Savereceiver> call, retrofit2.Response<Savereceiver> response) {
                Toast.makeText(RegisterMissing.this, "save successfull", Toast.LENGTH_SHORT).show();

                train();
            }

            @Override
            public void onFailure(retrofit2.Call<Savereceiver> call, Throwable t) {
                Toast.makeText(RegisterMissing.this, "save failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void train() {
        Saveinterfacce saveinterface=Apiclient.getRetrofit().create(Saveinterfacce.class);
        retrofit2.Call<trainreceiver> call=saveinterface.trainimage(api_key,api_secret,uid);
        call.enqueue(new retrofit2.Callback<trainreceiver>() {
            @Override
            public void onResponse(retrofit2.Call<trainreceiver> call, retrofit2.Response<trainreceiver> response) {
                Toast.makeText(RegisterMissing.this, "train done", Toast.LENGTH_SHORT).show();
                Log.d("ppppppppppp", response.toString());
            }

            @Override
            public void onFailure(retrofit2.Call<trainreceiver> call, Throwable t) {
                Toast.makeText(RegisterMissing.this, "train failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}