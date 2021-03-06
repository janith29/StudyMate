package com.mad.studymate.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.studymate.R;
import com.mad.studymate.activity.AddNoteActivity;
import com.mad.studymate.activity.AnswerQuizActivity;
import com.mad.studymate.activity.ViewNoteActivity;
import com.mad.studymate.cardview.adapter.NoteCardAdapter;
import com.mad.studymate.cardview.adapter.QuizCardAdapter;
import com.mad.studymate.cardview.model.Note;
import com.mad.studymate.cardview.model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    //add notes fab
    FloatingActionButton fab;

    private RecyclerView mRecyclerView;
    private NoteCardAdapter mAdapter;
    private List<Note> noteList;

    private OnFragmentInteractionListener mListener;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        //getting the recyclerview from xml
        mRecyclerView = view.findViewById(R.id.idNotesRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Populate the groups
        noteList = new ArrayList<>();
        noteList.add(new Note("How to write good code", "Programming", 5));
        noteList.add(new Note("Software Development Life Cycle(SDLC)","Software Engineering", 3));
        noteList.add(new Note("How to learn faster", "Mind", 1));

        //set adapter to recyclerview
        mAdapter = new NoteCardAdapter(noteList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        //card clicked event with sending necessary data to the answering activity.
        mAdapter.setOnItemClickListener(new NoteCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Note note = noteList.get(position);
                Intent intent = new Intent(getActivity(), ViewNoteActivity.class);
                intent.putExtra("title", note.getNoteTitle());
                startActivity(intent);
            }
        });

        //fab click listner to open add note activity
        fab = view.findViewById(R.id.idNoteAddFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddNoteActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
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
