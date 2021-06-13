package com.example.parking_app_andr.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.parking_app_andr.model.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileRepository {
    private final String TAG  = this.getClass().getCanonicalName();
    private final FirebaseFirestore db;
    private final String COLLECTION_NAME = "Profile";
    public MutableLiveData<Profile> profileDb = new MutableLiveData<>();


    public ProfileRepository(){
        db = FirebaseFirestore.getInstance();
    }

    public void addProfile(Profile profile){
        try{
            //    //u_name,u_email,u_phone_num,u_car_plate_num,u_pass_word
            Map<String, Object> data = new HashMap<>();
            data.put("name", profile.getName());
            data.put("Email", profile.getEmail());
            data.put("phone number", profile.getU_phone_num());
            data.put("car plate num",profile.getU_car_plate_num());
            data.put("password",profile.getU_pass_word());

            db.collection(COLLECTION_NAME)
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "onSuccess: Document added successfully" + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: Error creating document on Firestore" + e.getLocalizedMessage() );
                        }
                    });

            //for custom ID
//            db.collection(COLLECTION_NAME).document("jk@jk.com").set(data);
//            https://firebase.google.com/docs/firestore/manage-data/add-data

        }catch(Exception ex){
            Log.e(TAG, "addFriend: " + ex.getLocalizedMessage() );
        }
    }


    public void updateProfile(Profile profile){
        try{
            Map<String, Object> updateInfo = new HashMap<>();
            updateInfo.put("name", profile.getName());
            updateInfo.put("email",profile.getEmail());
            updateInfo.put("password",profile.getU_pass_word());
            updateInfo.put("phoneNumber", profile.getU_phone_num());

            updateInfo.put("car palte num",profile.getU_car_plate_num());

            db.collection(COLLECTION_NAME)
                    .document(profile.getId())
                    .update(updateInfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "onSuccess: Document updated successfully");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Unable to update document");
                        }
                    });

            //TASK - COMPLETE THE REST OF THE FUNCTION CALLS AND OPERATIONS IN THE FLOW TO COMPLETTE THE UPDATE FUNCTIONALITY

        }catch (Exception ex){
            Log.e(TAG, "deleteprofile: Unable to update document " + ex.getLocalizedMessage() );
        }
    }

    public void deleteProfile(String docID){
        try{
            db.collection(COLLECTION_NAME)
                    .document(docID)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "onSuccess: Document successfully deleted");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: Unable to delete document" + e.getLocalizedMessage() );
                        }
                    });
        }catch (Exception ex){
            Log.e(TAG, "deleteFriend: Unable to delete document " + ex.getLocalizedMessage() );
        }
    }


}
















