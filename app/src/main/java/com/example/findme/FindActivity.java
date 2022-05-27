package com.example.findme;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FindActivity extends AppCompatActivity {
    Button uploadimg;
    ImageView image;
   TextView nameofmissing, placeofmissing,po,ag;
   Uri ImgUri;
   String filePath;
   Bitmap bitmap;
   String confidence;
   private String pid;
   String ur;
   String URL="https://api.skybiometry.com/fc/faces/recognize?api_key=e7ns9sqdmrkv4hfq2f7ooscujn&api_secret=rauoedqnn297ba225uflrgoumg&uids=all@firstone";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        uploadimg=(Button)findViewById(R.id.uploadbtn);
        image=(ImageView)findViewById(R.id.image);
        nameofmissing=(TextView)findViewById(R.id.nameofmissing);
        placeofmissing=(TextView)findViewById(R.id.placeofmissing);
        po=(TextView)findViewById(R.id.phone);
        ag=(TextView)findViewById(R.id.age);
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

                    try {
                        upload();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(FindActivity.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
                }
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
                    JSONArray obj = object.getJSONArray("photos").getJSONObject(0).getJSONArray("tags").getJSONObject(0).getJSONArray("uids");
                    pid=object.getJSONArray("photos").getJSONObject(0).getJSONArray("tags").getJSONObject(0).getJSONArray("uids").getJSONObject(0).getString("uid");
                    confidence=object.getJSONArray("photos").getJSONObject(0).getJSONArray("tags").getJSONObject(0).getJSONArray("uids").getJSONObject(0).getString("confidence");
                     display();

                    Log.d("hhhhhhhhhhhhhhh", pid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

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
    public void display(){
        DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nama="Not found";
                String placee="Not found";
                String Ph="Not found";
                String age="Not found";
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Fakemodel model=dataSnapshot.getValue(Fakemodel.class);

                    System.out.println(model.name);
                    String n=model.name.replaceAll("\\s.*", "");
                    if((pid+"GO").equals(n.trim()+"@firstoneGO")){
                         nama=model.name;
                         placee=model.place;
                         Ph=model.phone;


                    }
                }
                if(Integer.valueOf(confidence)<=50){
                    nama="....";
                    placee="....";
                    Ph="....";
                    age=".....";
                    Toast.makeText(FindActivity.this, "Failure to recognise please try again", Toast.LENGTH_SHORT).show();
                }
                nameofmissing.setText("person name is "+nama);
                placeofmissing.setText("person found at "+placee);
                po.setText("Phone number "+Ph);
                ag.setText("Age is "+age);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FindActivity.this, "Some error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}