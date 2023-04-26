package com.example.reminderlyfinalproject;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class ReminderFragment extends Fragment {

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
        ServiceClient serviceClient = ServiceClient.sharedServiceClient(getActivity().getApplicationContext());
        List<Reminder> reminders = new ArrayList<>();
        MyReminderRecyclerViewAdapter adapter = new MyReminderRecyclerViewAdapter(reminders);
        AuthRequest authRequest = new AuthRequest(Request.Method.GET, "https://mopsdev.bw.edu/~ssavel19/rest.php/reminders", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Type reminderList = new TypeToken<ArrayList<Reminder>>() {}.getType();
                Gson gson = new Gson();
                try {
                    List<Reminder> updatedReminders = gson.fromJson(response.get("data").toString(), reminderList);
                    reminders.clear();
                    reminders.addAll(updatedReminders);
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
        AuthRequest.username = "testing";
        AuthRequest.password = "testpassword";
        serviceClient.addRequest(authRequest);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
}