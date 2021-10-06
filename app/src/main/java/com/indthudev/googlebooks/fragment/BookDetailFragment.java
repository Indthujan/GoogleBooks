package com.indthudev.googlebooks.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.indthudev.googlebooks.databinding.FragmentBookDetailBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BookDetailFragment extends Fragment {

    FragmentBookDetailBinding binding;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentBookDetailBinding.inflate(inflater, container, false);

        assert getArguments() != null;
        String image = getArguments().getString("image");
        String name = getArguments().getString("name");
        String description = getArguments().getString("description");

        if (image != null) {
            String imageUrl = image.replace("http://", "https://");

            Glide.with(getContext())
                    .load(imageUrl)
                    .into(binding.bookImage);
        }

        binding.bookName.setText(name);
        binding.bookDescription.setText(description);

        return binding.getRoot();
    }
}
