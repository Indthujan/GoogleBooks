package com.indthudev.googlebooks.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indthudev.googlebooks.R;
import com.indthudev.googlebooks.adapter.BookSearchResultAdapter;
import com.indthudev.googlebooks.databinding.FragmentBooksearchBinding;
import com.indthudev.googlebooks.model.Volume;
import com.indthudev.googlebooks.model.VolumeResponse;
import com.indthudev.googlebooks.viewmodel.BookViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

public class BookSearchFragment extends Fragment implements BookSearchResultAdapter.OnItemClickListener {

    private BookViewModel bookViewModel;
    private BookSearchResultAdapter adapter;
    private List<Volume> volumeList = new ArrayList<>();
    FragmentBooksearchBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new BookSearchResultAdapter(getActivity(), volumeList, BookSearchFragment.this);

        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        bookViewModel.init();
        bookViewModel.getVolumeResponseLiveData().observe(this, new Observer<VolumeResponse>() {
            @Override
            public void onChanged(VolumeResponse volumeResponse) {
                if (volumeResponse != null) {
                    adapter.setResults(volumeResponse.getItems());
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentBooksearchBinding.inflate(inflater, container, false);

        binding.fragmentBooksearchSearchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.fragmentBooksearchSearchResultsRecyclerView.setAdapter(adapter);

        binding.fragmentBooksearchSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                performSearch();
            }
        });

        return binding.getRoot();
    }

    public void performSearch() {

        String keyWord = binding.fragmentBooksearchKeyword.getEditableText().toString();
        String author = binding.fragmentBooksearchAuthor.getEditableText().toString();

        bookViewModel.searchVolumes(keyWord, author);
    }

    @Override
    public void onItemClick(Volume volume, int position) {

        Bundle bundle = new Bundle();
        bundle.putString("image", volume.getVolumeInfo().getImageLinks().getSmallThumbnail());
        bundle.putString("name", volume.getVolumeInfo().getTitle());
        bundle.putString("description", volume.getVolumeInfo().getDescription());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_bookSearchFragment_to_bookDetailFragment4, bundle);
    }
}
