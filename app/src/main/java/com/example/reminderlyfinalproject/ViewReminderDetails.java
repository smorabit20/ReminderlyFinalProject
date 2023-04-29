package com.example.reminderlyfinalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewReminderDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewReminderDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewReminderDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewReminderDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewReminderDetails newInstance(String param1, String param2) {
        ViewReminderDetails fragment = new ViewReminderDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private String reminderName;
    private int reminderId;

    private String reminderLocation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_reminder_details, container, false);

        Bundle args = getArguments();

        if (args != null) {
            reminderName = args.getString("name");
            reminderLocation = args.getString("location");
            reminderId = Integer.parseInt(args.getString("id"));
        }

        TextView nameTextView = view.findViewById(R.id.reminderName);
        TextView idTextView = view.findViewById(R.id.reminderTime2);
        TextView locationTextView = view.findViewById(R.id.reminderLocation);


        nameTextView.setText(reminderName);
        idTextView.setText(String.valueOf(reminderId));
        locationTextView.setText(String.valueOf(reminderLocation));

        //BUTTONS

        //UPDATE REMINDER BUTTON
        view.findViewById(R.id.updateReminderBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: WHAT HAPPENS ONCE WE UPDATE THE BUTTON??
                //Navigation.findNavController(view).navigate(R.id.action_viewReminderDetails_to_viewReminders);
            }
        });

        //RETURN BUTTON
        view.findViewById(R.id.returnBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_viewReminderDetails_to_viewReminders);
            }
        });
        return view;
    }
}