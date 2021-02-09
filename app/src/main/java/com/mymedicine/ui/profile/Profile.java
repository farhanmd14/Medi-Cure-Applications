package com.mymedicine.ui.profile;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
import com.mymedicine.R;
import com.mymedicine.User;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class Profile extends Fragment {
    Button update;
    ImageView img_user;
    EditText name, email, gender, dob;

    ProgressDialog dialog;
    Uri uri;
    User user;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.profile, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
        update = root.findViewById(R.id.update);
        img_user = root.findViewById(R.id.img_user);
        name = root.findViewById(R.id.name);
        email = root.findViewById(R.id.email);
        gender = root.findViewById(R.id.gender);
        dob = root.findViewById(R.id.dob);
        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (uri != null) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
                    final StorageReference riversRef = mStorageRef.child("dp/" + auth.getCurrentUser().getUid() + "." + getFileExtension(uri));

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
                } else {
                    updateUser(null);
                }
            }

        });

        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //cameraIntent.setType("image/*");
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, 1000);
                }
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        dialog.setMessage("Fetching Your Profile");
        dialog.show();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                try {
                    name.setText(user.getName());
                    email.setText(user.getEmail());
                    gender.setText(user.getGender());
                    dob.setText(user.getDob());
                    Glide.with(getActivity()).load(user.getDp())
                            .addListener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    dialog.dismiss();
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    dialog.dismiss();
                                    return false;
                                }
                            }).into(img_user);
                } catch (Exception ignored) {
                    user = new User();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return root;

    }

    private void updateUser(String s) {
        //user.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        user.setDp(s);
        user.setName(name.getText().toString());
        user.setDob(dob.getText().toString());
        //user.setEmail(email.getText().toString());
        //user.setGender(gender.getText().toString());
        if (name.getText().toString().isEmpty()) {
            name.setError("Can Not Be Empty");
        }
        if (dob.getText().toString().isEmpty()) {
            dob.setError("Can Not Be Empty");
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        dialog.setMessage("Updating Profile");
        myRef.child(user.getId()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                    img_user.setImageBitmap(bitmapImage);
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