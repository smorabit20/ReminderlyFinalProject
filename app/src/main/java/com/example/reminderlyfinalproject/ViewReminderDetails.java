package com.example.reminderlyfinalproject;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.reminderlyfinalproject.model.AuthRequest;
import com.example.reminderlyfinalproject.model.ServiceClient;

import org.json.JSONException;
import org.json.JSONObject;

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
    private String reminderTime;
    private String reminderDate;
    private int reminderId;

    private String reminderLocation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_reminder_details, container, false);

        String submittedUsername = getArguments().getString("user");
        String submittedPassword = getArguments().getString("pass");

        Bundle bundle = new Bundle();
        bundle.putString("user", submittedUsername);
        bundle.putString("pass", submittedPassword);
        ServiceClient serviceClient = ServiceClient.sharedServiceClient(getActivity().getApplicationContext());


        reminderName = getArguments().getString("name");
        reminderLocation = getArguments().getString("location");
        reminderId = getArguments().getInt("id");
        reminderTime = getArguments().getString("time");
        reminderDate = getArguments().getString("date");

        EditText nameView = view.findViewById(R.id.reminderName);
        EditText timeView = view.findViewById(R.id.reminderTime2);
        EditText locationView = view.findViewById(R.id.reminderLocation);
        EditText dateView = view.findViewById(R.id.editDate);

        nameView.setText(reminderName);
        timeView.setText(reminderTime);
        dateView.setText(reminderDate);
        locationView.setText(String.valueOf(reminderLocation));

        //BUTTONS

        //UPDATE REMINDER BUTTON
        view.findViewById(R.id.updateReminderBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: WHAT HAPPENS ONCE WE UPDATE THE BUTTON??
                EditText newName = getView().findViewById(R.id.reminderName);
                String updatedName = newName.getText().toString();

                EditText newTime = getView().findViewById(R.id.reminderTime2);
                String updatedTime = newTime.getText().toString();

                EditText newLocation = getView().findViewById(R.id.reminderLocation);
                String updatedLocation = newLocation.getText().toString();

                EditText newDate = getView().findViewById(R.id.editDate);
                String updateDate = newDate.getText().toString();

                if ((updatedName.equals(""))) {
                    newName.setBackgroundColor(Color.RED);
                    Toast.makeText(getActivity().getApplicationContext(), "Failed to update. Please fill out all sections.", Toast.LENGTH_LONG).show();

                } else if (updatedTime.equals("")) {
                    newTime.setBackgroundColor(Color.RED);
                    Toast.makeText(getActivity().getApplicationContext(), "Failed to update. Please fill out all sections.", Toast.LENGTH_LONG).show();

                } else if (updatedLocation.equals("")) {
                    newLocation.setBackgroundColor(Color.RED);
                    Toast.makeText(getActivity().getApplicationContext(), "Failed to update. Please fill out all sections.", Toast.LENGTH_LONG).show();

                } else if (updateDate.equals("")) {
                    newDate.setBackgroundColor(Color.RED);
                    Toast.makeText(getActivity().getApplicationContext(), "Failed to update. Please fill out all sections.", Toast.LENGTH_LONG).show();

                }
                else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("username", submittedUsername);
                        jsonObject.put("reminderId", reminderId);
                        jsonObject.put("reminderName", updatedName);
                        jsonObject.put("reminderTime", updatedTime);
                        jsonObject.put("reminderLocation", updatedLocation);
                        jsonObject.put("reminderDate", updateDate);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AuthRequest authRequest = new AuthRequest(Request.Method.PUT, "https://mopsdev.bw.edu/~ssavel19/rest.php/reminders/5", jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getActivity().getApplicationContext(), "Successfully updated!", Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity().getApplicationContext(), "Failed to update. Please try again later.", Toast.LENGTH_LONG).show();
                        }
                    });
                    AuthRequest.username = submittedUsername;
                    AuthRequest.password = submittedPassword;
                    serviceClient.addRequest(authRequest);
                    Navigation.findNavController(view).navigate(R.id.action_viewReminderDetails_to_reminderFragment, bundle);
                }

            }
        });

        //RETURN BUTTON
        view.findViewById(R.id.returnBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_viewReminderDetails_to_reminderFragment, bundle);
            }
        });
        return view;
    }
}