package com.mad.studymate.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mad.studymate.R;
import com.mad.studymate.ViewPagerAdapter;
import com.mad.studymate.activity.AddTaskActivity;
import com.mad.studymate.activity.ViewNoteActivity;
import com.mad.studymate.activity.ViewTaskActivity;
import com.mad.studymate.cardview.adapter.NoteCardAdapter;
import com.mad.studymate.cardview.adapter.TaskCardAdapter;
import com.mad.studymate.cardview.model.Note;
import com.mad.studymate.cardview.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment {

    ActionBar actionBar;

    //add notes fab
    FloatingActionButton fab;

    private RecyclerView mRecyclerView;
    private TaskCardAdapter mAdapter;
    private List<Task> taskList;

    private OnFragmentInteractionListener mListener;

    public TasksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Not Completed Tasks");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        //getting the recyclerview from xml
        mRecyclerView = view.findViewById(R.id.idTasksRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Populate the groups
        taskList = new ArrayList<>();
        taskList.add(new Task("Read JS book", 1, "4:30 to 5:30"));
        taskList.add(new Task("Write a letter", 2, "5:30 to 6:00"));

        //set adapter to recyclerview
        mAdapter = new TaskCardAdapter(taskList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        //card clicked event with sending necessary data to the answering activity.
        mAdapter.setOnItemClickListener(new TaskCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Task task = taskList.get(position);
                Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
                intent.putExtra("title", task.getTaskTitle());
                startActivity(intent);
            }
        });

        //fab click listner to open add note activity
        fab = view.findViewById(R.id.idTaskAddFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                startActivity(intent);
            }
        });

        //bottom navigation
        BottomNavigationView navigation = view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        return view;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_groups:
                    actionBar.setTitle("Not Completed Tasks");
                    fragment = new TasksFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_users:
                    actionBar.setTitle("Completed Tasks");
                    fragment = new CompletedTaskFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
