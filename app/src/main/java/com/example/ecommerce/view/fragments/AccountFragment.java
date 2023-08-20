package com.example.ecommerce.view.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerce.R;
import com.example.ecommerce.adapter.AccountItemAdapter;
import com.example.ecommerce.contract.AccountContract;
import com.example.ecommerce.contract.AccountContractImpl;
import com.example.ecommerce.listeners.OnItemClickListener;
import com.example.ecommerce.models.AccountItemsModel;
import com.example.ecommerce.utils.Constants;
import com.example.ecommerce.utils.Utils;
import com.example.ecommerce.view.activities.DashboardActivity;
import com.example.ecommerce.view.activities.SplashActivity;

import java.util.ArrayList;

public class AccountFragment extends Fragment implements OnItemClickListener, AccountContract.View {
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

        AccountContractImpl accountContract = new AccountContractImpl(this);
        accountContract.loadAccountItems();
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

    @Override
    public void showAccountItems(ArrayList<AccountItemsModel> accountItemsModelArrayList) {
        Utils.showMessage(getContext(), "LoadMethod Calling " + accountItemsModelArrayList.size());
        if (accountItemsModelArrayList.size() > 0 && accountItemsModelArrayList != null) {
            itemListAdapter = new AccountItemAdapter(getContext(), accountItemsModelArrayList, this);
            rvItems.setAdapter(itemListAdapter);
        }
    }
}