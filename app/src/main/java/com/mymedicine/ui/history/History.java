package com.mymedicine.ui.history;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mymedicine.Myrecycleadapter;
import com.mymedicine.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class History extends Fragment {
    ImageView upload;
    Button btn1;
    RecyclerView rv;
    private Uri uri;
    private ProgressDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.history, container, false);

        upload = root.findViewById(R.id.upload);
        btn1 = root.findViewById(R.id.btn1);
        rv = root.findViewById(R.id.rv);


        GridLayoutManager manager1 = new GridLayoutManager(getActivity(), 3);

        rv.setLayoutManager(manager1);


        dialog = new ProgressDialog(getActivity());

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //cameraIntent.setType("image/*");
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, 1000);
                }

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri != null) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    String fileName = new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());

                    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
                    final StorageReference riversRef = mStorageRef.child("prescription/" +fileName + "." + getFileExtension(uri));
                    dialog.setMessage("Uploading Pic");
                    dialog.show();
                    riversRef.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()) {
                                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri downloadPhotoUrl) {
                                        updateUser(downloadPhotoUrl.toString());
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        getdp();

        return root;

    }

    private void getdp() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Prescription").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        dialog.setMessage("Fetching Your Prescriptions");
        dialog.show();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> list = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    list.add(child.getValue().toString());
                }
                Myrecycleadapter adapter = new Myrecycleadapter(getActivity(), list);
                rv.setAdapter(adapter);
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    private void updateUser(String dp) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Prescription");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        dialog.setMessage("Uploading");
        myRef.child(auth.getCurrentUser().getUid()).push().setValue(dp).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("failed", e.toString());
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        Log.e("method", "run");
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                uri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    upload.setImageBitmap(bitmapImage);
                    Log.e("image", "success");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("image", "failed");
                }
            }
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


}