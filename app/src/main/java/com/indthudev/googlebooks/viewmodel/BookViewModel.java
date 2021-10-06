package com.indthudev.googlebooks.viewmodel;

import android.app.Application;

import com.indthudev.googlebooks.model.VolumeResponse;
import com.indthudev.googlebooks.repository.BookRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class BookViewModel extends AndroidViewModel {

    private BookRepository bookRepository;
    private LiveData<VolumeResponse> volumeResponseLiveData;

    public BookViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {

        bookRepository = new BookRepository();
        volumeResponseLiveData = bookRepository.getVolumesResponseLiveData();
    }

    public void searchVolumes(String keyword, String author) {
        bookRepository.searchVolumes(keyword, author);
    }

    public LiveData<VolumeResponse> getVolumeResponseLiveData() {
        return volumeResponseLiveData;
    }
}
