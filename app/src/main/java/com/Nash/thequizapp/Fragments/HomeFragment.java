package com.Nash.thequizapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Nash.thequizapp.MVVM.Modal;
import com.Nash.thequizapp.MVVM.RecyclerAdapter;
import com.Nash.thequizapp.MVVM.ViewModal;
import com.Nash.thequizapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnItemClicked {


    //Recycler View
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    //ViewModal
    ViewModal viewModal;

    //Navigation
    NavController navController;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Recycler View Init
        adapter = new RecyclerAdapter(this);
        recyclerView = view.findViewById(R.id.home_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        //Navigation
        navController = Navigation.findNavController(view);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModal = new ViewModelProvider(getActivity()).get(ViewModal.class);
        viewModal.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Modal>>() {
            @Override
            public void onChanged(List<Modal> modalList) {
                adapter.setModalList(modalList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void selectedItem(int position) {
           HomeFragmentDirections.ActionHomeFragmentToDetailFragment action = HomeFragmentDirections.actionHomeFragmentToDetailFragment();
           action.setPosition(position);
           navController.navigate(action);
    }
}
