package com.nanodevs.elegance.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
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

import com.nanodevs.elegance.Adapters.CustomerAdapter;
import com.nanodevs.elegance.Pojo.Customer;
import com.nanodevs.elegance.R;
import com.nanodevs.elegance.classes.InternetConnection;

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


        if (InternetConnection.checkConnection(getContext())){

            homeViewModel.getAllCustomers().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
                @Override
                public void onChanged(List<Customer> customers) {

                    customerAdapter = new CustomerAdapter(customers, getContext());
                    recyclerView.setAdapter(customerAdapter);

                }
            });

        }
        else{
            Toast.makeText(getContext(), "Can not Load Data Without Internet!", Toast.LENGTH_SHORT).show();
        }



        return root;
    }




    @Override
    public void onStart() {
        super.onStart();

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
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                if (InternetConnection.checkConnection(getContext())) {

                    if (recyclerView.getAdapter() != null) {

                        customerAdapter.getFilter().filter(newText);
                        Toast.makeText(getContext(), "Searching Items", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), " No Data!!", Toast.LENGTH_SHORT).show();
                    }



                } else {
                    Toast.makeText(getContext(), "Enable Internet To Search Items", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean newViewFocus)
            {
                if (!newViewFocus)
                {
                    Toast.makeText(getContext(), "Closed", Toast.LENGTH_SHORT).show();
                }
            }
        });






    }


}
