package com.example.reminderlyfinalproject;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.reminderlyfinalproject.model.AuthRequest;
import com.example.reminderlyfinalproject.model.Reminder;
import com.example.reminderlyfinalproject.model.ServiceClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ReminderFragment extends Fragment implements SelectListener {
    private Bundle bundle;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReminderFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ReminderFragment newInstance(int columnCount) {
        ReminderFragment fragment = new ReminderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        String submittedUsername = getArguments().getString("user");
        String submittedPassword = getArguments().getString("pass");

        bundle = new Bundle();
        bundle.putString("user", submittedUsername);
        bundle.putString("pass", submittedPassword);

        ServiceClient serviceClient = ServiceClient.sharedServiceClient(getActivity().getApplicationContext());

        List<Reminder> reminders = new ArrayList<>();
        MyReminderRecyclerViewAdapter adapter = new MyReminderRecyclerViewAdapter(reminders, this);
        AuthRequest authRequest = new AuthRequest(Request.Method.GET, "https://mopsdev.bw.edu/~ssavel19/rest.php/reminders", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Type reminderList = new TypeToken<ArrayList<Reminder>>() {}.getType();
                Gson gson = new Gson();

                try {
                    List<Reminder> allReminders = gson.fromJson(response.get("data").toString(), reminderList);
                    List<Reminder> userReminders = new ArrayList<>();
                    for (Reminder reminder : allReminders) {
                        if (reminder.username.equals(submittedUsername)) {
                            userReminders.add(reminder);
                        }
                    }
                    reminders.clear();
                    reminders.addAll(userReminders);
                    adapter.notifyDataSetChanged();
                    int x = 1;
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int x = 1;
            }
        });
        AuthRequest.username = submittedUsername;
        AuthRequest.password = submittedPassword;
        serviceClient.addRequest(authRequest);

        RecyclerView recyclerView = view.findViewById(R.id.list);

        // Set the adapter
        if (recyclerView instanceof RecyclerView) {
            Context context = view.getContext();
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(adapter);
        }

        //create reminder button
        view.findViewById(R.id.newReminderBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_reminderFragment_to_saveReminder, bundle);
            }
        });

        //create reminder button
        view.findViewById(R.id.logOutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_reminderFragment_to_welcomeScreen);
            }
        });

        return view;
    }

    @Override
    public void onItemClicked(Reminder reminderClicked) {
        bundle.putString("name", reminderClicked.reminderName);
        bundle.putInt("id", reminderClicked.reminderId);
        bundle.putString("location", reminderClicked.reminderLocation);
        bundle.putString("time", reminderClicked.reminderTime);
        bundle.putString("date", reminderClicked.reminderDate);
        Navigation.findNavController(getView()).navigate(R.id.action_reminderFragment_to_viewReminderDetails, bundle);
    }
}