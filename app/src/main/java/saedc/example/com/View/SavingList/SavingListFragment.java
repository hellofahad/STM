package saedc.example.com.View.SavingList;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import saedc.example.com.Model.Entity.Saving;
import saedc.example.com.R;
import saedc.example.com.View.AddAndEditSaving.AddAndEditSavingFragment;
import saedc.example.com.View.SavingActivity;

/**
 * Created by saedc on 11/02/18.
 */

public class SavingListFragment extends Fragment implements Recyclerviewclick , LifecycleOwner {
    ArrayList<Saving> savingList = new ArrayList<>();
    SavingListViewModle viewModel;
    SavingRecyclerView adapter;
    View view;

    @BindView(R.id.saving_recyclerview)
    RecyclerView savingRecyclerView;


    private LifecycleRegistry registry=new LifecycleRegistry(this);
    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return (registry);
    }


    @Override
    public void onStart() {
        super.onStart();

        registry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();

        registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();

        registry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();

        registry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }


    public static SavingListFragment newInstance() {

        return new SavingListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_saving_list, container, false);
        ButterKnife.bind(this, view);

        adapter = new SavingRecyclerView(getActivity(), savingList, this);
        savingRecyclerView.setAdapter(adapter);
        savingRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(SavingListViewModle.class);
        subscribeSavings();
    }
    private void subscribeSavings() {
        viewModel.Getsavings().observe(this, new Observer<List<Saving>>() {
            @Override
            public void onChanged(final List<Saving> savingss) {
                adapter.updateItems(savingss);
            }
        });
    }

    @Override
    public void onItemClick(Saving clickedSpending) {


        ((SavingActivity)getActivity()).showFragment(AddAndEditSavingFragment.newInstance(clickedSpending));

    }

    @Override
    public void onItemLongClick(int longClickedSpendingId) {

        viewModel.DeleteSaving(longClickedSpendingId);

    }


}
