package com.example.reminderlyfinalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.reminderlyfinalproject.model.ServiceClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateProfile newInstance(String param1, String param2) {
        CreateProfile fragment = new CreateProfile();
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
        //BUTTONS
        View view = inflater.inflate(R.layout.fragment_create_profile, container, false);

        //CREATE BUTTON TO VIEW REMINDERS SCREEN
        view.findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText username = view.findViewById(R.id.createdUsername);
                String user = username.getText().toString();
                EditText password = view.findViewById(R.id.createdPassword);
                String pass = password.getText().toString();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Failed to login. Please fill out all sections.", Toast.LENGTH_LONG).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("username", user);
                        jsonObject.put("password", pass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://mopsdev.bw.edu/~ssavel19/rest.php/login", jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String status = response.getString("status");
                                if (status.equals("success")) {
                                    // Login was successful, navigate to the next screen
                                    Bundle bundle = new Bundle();
                                    bundle.putString("username", user);
                                    Navigation.findNavController(view).navigate(R.id.action_createProfile_to_viewReminders, bundle);
                                } else {
                                    // Login failed, display an error message to the user
                                    Toast.makeText(getActivity().getApplicationContext(), "Failed to login. Please check your credentials and try again.", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity().getApplicationContext(), "Failed to login. Please try again later.", Toast.LENGTH_LONG).show();}
                    });
                    ServiceClient serviceClient = ServiceClient.sharedServiceClient(getActivity().getApplicationContext());
                    serviceClient.addRequest(request);
                }
            }
        });
        return view;
    }
}