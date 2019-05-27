package com.whosssmade.bluetoothterminal.ui.adapters;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.whosssmade.bluetoothterminal.R;

import java.util.List;

/**
 * Created by Administrator on 2019/5/24.
 */

public class DeviceAdapter extends BaseQuickAdapter<BluetoothDevice, BaseViewHolder> {

    public DeviceAdapter(@Nullable List<BluetoothDevice> data) {
        super(R.layout.adapter_device, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BluetoothDevice item) {

        TextView name = helper.getView(R.id.name);
        name.setText(item.getName());

        TextView mac = helper.getView(R.id.mac);
        mac.setText(item.getAddress());

    }
}
