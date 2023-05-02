package com.example.reminderlyfinalproject;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.reminderlyfinalproject.model.AuthRequest;
import com.example.reminderlyfinalproject.model.ServiceClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaveReminder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaveReminder extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SaveReminder() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaveReminder.
     */
    // TODO: Rename and change types and number of parameters
    public static SaveReminder newInstance(String param1, String param2) {
        SaveReminder fragment = new SaveReminder();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save_reminder, container, false);
        ServiceClient serviceClient = ServiceClient.sharedServiceClient(getActivity().getApplicationContext());

        String submittedUsername = getArguments().getString("user");
        String submittedPassword = getArguments().getString("pass");

        Bundle bundle = new Bundle();

        bundle.putString("user", submittedUsername);
        bundle.putString("pass", submittedPassword);

        //BUTTONS

        //ADD REMINDER BUTTON
        view.findViewById(R.id.createReminderBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();

                //reminder name
                EditText createdName = view.findViewById(R.id.name);
                String createdReminderName = createdName.getText().toString();

                //reminder location
                EditText createdLocation = view.findViewById(R.id.location);
                String createdReminderLocation = createdLocation.getText().toString();

                //reminder time
                EditText createdTime = view.findViewById(R.id.time);
                String createdReminderTime = createdTime.getText().toString();

                //reminder date
                EditText createdDate = view.findViewById(R.id.createReminderDate);
                String createdReminderDate = createdDate.getText().toString();

                if ((createdReminderName.equals("") || createdReminderLocation.equals("") || createdReminderTime.equals("") || createdReminderDate.equals(""))) {
                    createdName.setBackgroundColor(Color.RED);
                    createdLocation.setBackgroundColor(Color.RED);
                    createdTime.setBackgroundColor(Color.RED);
                    createdDate.setBackgroundColor(Color.RED);
                    Toast.makeText(getActivity().getApplicationContext(), "Successfully created a new reminder!", Toast.LENGTH_LONG).show();
                }

                else {
                    try {
                        jsonObject.put("username", submittedUsername);
                        jsonObject.put("reminderName", createdReminderName);
                        jsonObject.put("reminderLocation", createdReminderLocation);
                        jsonObject.put("reminderTime", createdReminderTime);
                        jsonObject.put("reminderDate", createdReminderDate);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    AuthRequest authRequest = new AuthRequest(Request.Method.POST, "https://mopsdev.bw.edu/~ssavel19/rest.php/reminders", jsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getActivity().getApplicationContext(), "Successfully created a new reminder!", Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity().getApplicationContext(), "Failed to create new reminder. Try again later.", Toast.LENGTH_LONG).show();}
                    });
                    AuthRequest.username = submittedUsername;
                    AuthRequest.password = submittedPassword;

                    serviceClient.addRequest(authRequest);
                    Navigation.findNavController(view).navigate(R.id.action_saveReminder_to_reminderFragment, bundle);
                }
                }
        });

        //CANCEL CREATE REMINDER BUTTON
        view.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_saveReminder_to_reminderFragment, bundle);
            }
        });

        return view;
    }
}