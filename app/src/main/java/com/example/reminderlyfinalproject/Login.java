package com.example.reminderlyfinalproject;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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

        //BUTTONS
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //LOGIN BUTTON
        view.findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener(){



            @Override
            public void onClick(View v){
               /* JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://mopsdev.bw.edu/~ssavel19/rest.php/users", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //grabs user input username and converts to string variable called username
                        TextView loginUser = view.findViewById(R.id.profileUsername);
                        String username = loginUser.toString();

                        //grabs user input password and convert to string variable
                        TextView loginPassword = view.findViewById(R.id.password);
                        String password = loginPassword.toString();

                        //make list out of "get" usernames and need to iterate through to make sure username works!
                        //List<String> tableUsernames = new ArrayList();
                            try {
                            username = response.getString("username");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int x = 5;
                    }
                }); */

                //username
                EditText username = view.findViewById(R.id.profileUsername);
                String user = username.getText().toString();
                //password
                EditText password = view.findViewById(R.id.password);
                String pass = password.getText().toString();

                if ((user.equals(""))) {
                    username.setBackgroundColor(Color.RED);
                    Toast.makeText(getActivity().getApplicationContext(), "Failed to login. Fill out all information", Toast.LENGTH_LONG).show();
                } else if (pass.equals("")) {
                    password.setBackgroundColor(Color.RED);
                    Toast.makeText(getActivity().getApplicationContext(), "Failed to login. Fill out all information", Toast.LENGTH_LONG).show();
                }

                else {
                    Bundle bundle = new Bundle();
                    bundle.putString(user, pass);
                    Navigation.findNavController(view).navigate(R.id.action_loginBtn_to_viewReminders, bundle);
                }

            }
        });
        return view;
    }
}