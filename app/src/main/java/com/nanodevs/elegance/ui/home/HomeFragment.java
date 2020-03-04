package com.nanodevs.elegance.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanodevs.elegance.Adapters.CustomerAdapter;
import com.nanodevs.elegance.MainActivity;
import com.nanodevs.elegance.Pojo.Customer;
import com.nanodevs.elegance.R;
import com.nanodevs.elegance.ui.slideshow.SlideshowViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private HomeViewModel homeViewModel;
    private CustomerAdapter customerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        this.setHasOptionsMenu(true);
        init(root);
        homeViewModel.getAllCustomers().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {

                customerAdapter = new CustomerAdapter(customers, getContext());
                recyclerView.setAdapter(customerAdapter);

            }
        });

        return root;
    }

    private void init(View root) {

        recyclerView = root.findViewById(R.id.recyclerViewCustomers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (isOnline()){
                    customerAdapter.getFilter().filter(newText);
                    Toast.makeText(getContext(), "Searching Items", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Enable Internet To Search Items", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

    }

    private boolean isOnline() {
        try {
            return Runtime.getRuntime().exec("/system/bin/ping -c 1 8.8.8.8").waitFor() == 0; //  "8.8.8.8" is the server to ping
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
