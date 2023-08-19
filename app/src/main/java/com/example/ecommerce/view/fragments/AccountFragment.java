package com.example.ecommerce.view.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerce.R;
import com.example.ecommerce.adapter.AccountItemAdapter;
import com.example.ecommerce.listeners.OnItemClickListener;
import com.example.ecommerce.models.AccountItemsModel;
import com.example.ecommerce.utils.Constants;
import com.example.ecommerce.utils.Utils;
import com.example.ecommerce.view.activities.DashboardActivity;
import com.example.ecommerce.view.activities.SplashActivity;

import java.util.ArrayList;

public class AccountFragment extends Fragment implements OnItemClickListener {
    RecyclerView rvItems;
    ArrayList<AccountItemsModel> accountItemsModels = new ArrayList<>();
    AccountItemAdapter itemListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        rvItems = view.findViewById(R.id.rv_items);

        setItemList();
    }

    private void setItemList() {
        accountItemsModels.add(new AccountItemsModel(10, R.drawable.edit_profile, "Edit Profile"));
        accountItemsModels.add(new AccountItemsModel(20, R.drawable.baseline_settings, "Settings"));
        accountItemsModels.add(new AccountItemsModel(30, R.drawable.address, "Saved Address"));
        accountItemsModels.add(new AccountItemsModel(40, R.drawable.baseline_logout, "Logout"));
        itemListAdapter = new AccountItemAdapter(getContext(), accountItemsModels, this);
        rvItems.setAdapter(itemListAdapter);
    }

    @Override
    public void onItemClick(int position) {
        if (position == 0) {
            Utils.showMessage(getContext(), getString(R.string.edit_profile));
        } else if (position == 1) {
            Utils.showMessage(getContext(), getString(R.string.settings));
        } else if (position == 2) {
            Utils.showMessage(getContext(), getString(R.string.select_language));
        } else if (position == 3) {
            userLogout();
        }
    }

    private void userLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.logout))
                .setMessage(getString(R.string.are_you_sure_you_want_to_logout))
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.PREF_FILENAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Utils.navigateScreen(getActivity(), SplashActivity.class);
                    getActivity().finishAffinity();
                }).setNegativeButton(getString(R.string.no), (dialog, which) -> {
                });
        builder.show();
    }
}