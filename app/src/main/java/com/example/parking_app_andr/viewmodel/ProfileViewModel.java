package com.example.parking_app_andr.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.parking_app_andr.model.Profile;
import com.example.parking_app_andr.repository.ProfileRepository;


import java.util.List;

public class ProfileViewModel extends AndroidViewModel {
    private static ProfileViewModel ourInstance;
    private final ProfileRepository profileRepository = new ProfileRepository();
    public MutableLiveData<Profile> matchedFriend;

    public static ProfileViewModel getInstance(Application application){
        if (ourInstance == null){
            ourInstance = new ProfileViewModel(application);
        }
        return ourInstance;
    }

    private ProfileViewModel(Application application){
        super(application);

    }



    public void deleteProfile(String docID){
        this.profileRepository.deleteProfile(docID);
    }
}
