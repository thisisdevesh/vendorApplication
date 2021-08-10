package com.gts.coordinator.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gts.coordinator.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSearch extends Fragment implements View.OnClickListener{
    private TextView search_edit;
    private SearchView searchView;
    private RecyclerView recyclerView;


    public FragmentSearch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_search, container, false);
        search_edit =view.findViewById(R.id.search_edit);
        searchView=view.findViewById(R.id.search_click);
        recyclerView=view.findViewById(R.id.rev_search);
        search_edit.setOnClickListener(this);
        searchView.setOnClickListener(this);
        searchView.setFocusable(true);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                /*searchView.clearFocus();
                _searchView.setQuery("", false);
                _searchView.setFocusable(false);
                _searchMenuItem.collapseActionView();
                isSearchFragmentVisible(false);*/
                Toast.makeText(getActivity(), "Search View Close", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_edit:
              //  searchView.setVisibility(View.VISIBLE);

              //  search_edit.setVisibility(View.GONE);
                break;
            case R.id.search_click:
                break;
        }
    }
}
