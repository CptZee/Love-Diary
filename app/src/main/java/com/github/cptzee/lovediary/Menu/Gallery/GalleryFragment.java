package com.github.cptzee.lovediary.Menu.Gallery;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.cptzee.lovediary.Data.Image.ImageAdapter;
import com.github.cptzee.lovediary.Manager.SessionManager;
import com.github.cptzee.lovediary.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    public GalleryFragment() {
        super(R.layout.fragment_gallery);
    }
    private static final int REQUEST_IMAGE_PICK = 2;
    private RecyclerView gallery;
    private TextView indicator;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference folderRef = storage.getReference().child("Pictures").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.gallery_new).setOnClickListener(v-> selectImage());
        indicator = view.findViewById(R.id.gallery_indicator);
        indicator.setText("Loading...");


        Log.d("GalleryHelper", "Current user is: " + FirebaseAuth.getInstance().getCurrentUser().getUid());

        gallery = view.findViewById(R.id.gallery_list);
        gallery.setLayoutManager(new GridLayoutManager(getContext(), 2));
        loadGallery();
    }

    private void loadGallery(){
        folderRef.listAll()
                .addOnSuccessListener(listResult -> {
                    List<StorageReference> fileList = new ArrayList<>();
                    for(StorageReference ref : listResult.getItems()){
                        Log.d("GalleryHelper", "Found " + ref.getName());
                        if(ref.getName().endsWith(".jpg"))
                            fileList.add(ref);
                    }
                    gallery.setAdapter(new ImageAdapter(fileList));
                    if(fileList.size() == 0){
                        indicator.setText("No pictures found, Add your first photo!");
                        indicator.setVisibility(View.VISIBLE);
                        return;
                    }
                    indicator.setVisibility(View.INVISIBLE);
                })
                .addOnFailureListener(exception -> {
                    Log.e("GalleryHelper", "Error retrieving files: " + exception.getMessage());
                });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_IMAGE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            uploadImageToFirebase(imageUri);
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference("Pictures");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUUID = user.getUid();

        StorageReference imageRef = storageRef.child(userUUID + "/" + System.currentTimeMillis() + ".jpg");

        UploadTask uploadTask = imageRef.putFile(imageUri);
        uploadTask.addOnCompleteListener(task -> {
            if (task.isSuccessful())
                Toast.makeText(getContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "Image upload failed", Toast.LENGTH_SHORT).show();
            loadGallery();
        });
    }
}
