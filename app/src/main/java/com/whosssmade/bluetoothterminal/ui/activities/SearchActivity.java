package com.whosssmade.bluetoothterminal.ui.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.base.BaseActivity;
import com.whosssmade.bluetoothterminal.business.contract.SearchContract;
import com.whosssmade.bluetoothterminal.business.presenter.SearchPresenter;
import com.whosssmade.bluetoothterminal.model.bean.DeviceBean;
import com.whosssmade.bluetoothterminal.model.constant.Constants;
import com.whosssmade.bluetoothterminal.ui.adapters.DeviceAdapter;
import com.whosssmade.bluetoothterminal.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/5/24.
 */

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.hasBondRecycler)
    RecyclerView hasBondRecycler;

    @BindView(R.id.notBondRecycler)
    RecyclerView notBondRecycler;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDiscovery bluetoothDiscovery;
    private DeviceAdapter hasBondAdapter, notBondAdapter;
    private List<DeviceBean> hasBondList, notBondList;

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initInject(Bundle bundle) {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
            showToast("正在打开蓝牙");
        }

        bluetoothDiscovery = new BluetoothDiscovery();

        register();

        hasBondList = new ArrayList<>();
        hasBondRecycler.setLayoutManager(new LinearLayoutManager(this));
        hasBondAdapter = new DeviceAdapter(hasBondList);
        hasBondAdapter.setOnItemClickListener(this);
        hasBondRecycler.setAdapter(hasBondAdapter);

        notBondList = new ArrayList<>();
        notBondRecycler.setLayoutManager(new LinearLayoutManager(this));
        notBondAdapter = new DeviceAdapter(notBondList);
        notBondAdapter.setOnItemClickListener(this);
        notBondRecycler.setAdapter(notBondAdapter);
    }

    private void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(bluetoothDiscovery, intentFilter);
    }

    @OnClick({R.id.search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                register();
                if (hasBondAdapter.getData().size() != 0) {
                    for (int i = 0; i < hasBondAdapter.getData().size(); i++) {
                        hasBondAdapter.remove(i);
                    }
                }

                if (notBondAdapter.getData().size() != 0) {
                    for (int i = 0; i < notBondAdapter.getData().size(); i++) {
                        notBondAdapter.remove(i);
                    }
                }

                if (bluetoothAdapter.isEnabled()) {
                    showToast("正在搜索");
                    bluetoothAdapter.startDiscovery();
                } else {
                    showToast("正在打开蓝牙");
                }
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter == hasBondAdapter) {
            Intent intent = new Intent(this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.DEVICE_BEAN, hasBondAdapter.getData().get(position));
            intent.putExtra(Constants.DEVICE_BEAN, bundle);
            startActivity(intent);
        }

        if (adapter == notBondAdapter) {
            Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            startActivity(intent);
        }
    }

    public class BluetoothDiscovery extends BroadcastReceiver {
        public BluetoothDiscovery() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    DeviceBean deviceBean = new DeviceBean(device.getName(), device.getAddress());
                    hasBondList.add(deviceBean);
                    hasBondAdapter.setNewData(hasBondList);
                } else {
                    DeviceBean deviceBean = new DeviceBean(device.getName(), device.getAddress());
                    notBondList.add(deviceBean);
                    notBondAdapter.setNewData(notBondList);
                }
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                showToast("搜索完成");
                unregisterReceiver(bluetoothDiscovery);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothDiscovery);
    }
}
