package com.whosssmade.bluetoothterminal.ui.adapters;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.model.bean.DeviceBean;

import java.util.List;

/**
 * Created by Administrator on 2019/5/24.
 */

public class DeviceAdapter extends BaseQuickAdapter<DeviceBean, BaseViewHolder> {

    public DeviceAdapter(@Nullable List<DeviceBean> data) {
        super(R.layout.adapter_device, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceBean item) {

        TextView name = helper.getView(R.id.name);
        name.setText(item.getName());

        TextView mac = helper.getView(R.id.mac);
        mac.setText(item.getMac());

    }
}
