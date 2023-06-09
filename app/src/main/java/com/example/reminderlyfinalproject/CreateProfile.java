package com.example.reminderlyfinalproject;

import static android.widget.Toast.LENGTH_LONG;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.reminderlyfinalproject.model.AuthRequest;
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
        ServiceClient serviceClient = ServiceClient.sharedServiceClient(getActivity().getApplicationContext());
        //CREATE BUTTON TO VIEW REMINDERS SCREEN
        view.findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                JSONObject jsonObject = new JSONObject();

                //username
                EditText profileUsername = view.findViewById(R.id.profileUsername);
                String user = profileUsername.getText().toString();
                //password
                EditText password = view.findViewById(R.id.password);
                String pass = password.getText().toString();
                try{
                    jsonObject.put("username", user);
                    jsonObject.put("password", pass);
                } catch (JSONException e){
                    e.printStackTrace();
                }

                if ((user.equals(""))) {
                    Toast.makeText(getActivity().getApplicationContext(),

                            "Failed to Register. Please fill out all sections.", LENGTH_LONG).show();
                }
                else if (pass.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(),"Failed to Register. Please fill out all sections.", LENGTH_LONG).show();
                }
                else{
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://mopsdev.bw.edu/~ssavel19/rest.php/users", jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String username = "";
                            String password = "";
                            try {
                                username = response.getString("username");
                                password = response.getString("password");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Bundle bundle = new Bundle();
                            bundle.putString("username", username);
                            bundle.putString("password", password);
                            Navigation.findNavController(view).navigate(R.id.action_createProfile_to_viewReminders, bundle);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Registering failed. Please try again.", LENGTH_LONG);
                            toast.show();
                        }

                    });
                    serviceClient.addRequest(request);

                            "Failed to login. Please try again", Toast.LENGTH_LONG).show();
                } else if (pass.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Failed to login. Please try again", Toast.LENGTH_LONG).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(user, pass);
                    Navigation.findNavController(view).navigate(R.id.action_createProfile_to_viewReminders, bundle);

                }
                }

            });
        return view;
    }
}
