package com.whosssmade.bluetoothterminal.ui.fragments;

import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.TextView;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.base.BaseFragment;
import com.whosssmade.bluetoothterminal.business.contract.ItemContract1;
import com.whosssmade.bluetoothterminal.business.presenter.ItemPresenter1;
import com.whosssmade.bluetoothterminal.ui.dialogs.SetValueDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class ItemFragment1 extends BaseFragment<ItemPresenter1> implements ItemContract1.View {

    @BindView(R.id.pulse1)
    TextView pulse1;

    @BindView(R.id.frequency1)
    TextView frequency1;

    @BindView(R.id.pulse2)
    TextView pulse2;

    @BindView(R.id.frequency2)
    TextView frequency2;

    @BindView(R.id.pulse3)
    TextView pulse3;

    @BindView(R.id.frequency3)
    TextView frequency3;

    @BindView(R.id.pulse4)
    TextView pulse4;

    @BindView(R.id.frequency4)
    TextView frequency4;

    private SetValueDialog setValueDialog;


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item_1;
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.pulse1, R.id.frequency1,
            R.id.pulse2, R.id.frequency2,
            R.id.pulse3, R.id.frequency3,
            R.id.pulse4, R.id.frequency4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pulse1:
                dialogSetTextView(pulse1);
                break;
            case R.id.frequency1:
                dialogSetTextView(frequency1);
                break;
            case R.id.pulse2:
                dialogSetTextView(pulse2);
                break;
            case R.id.frequency2:
                dialogSetTextView(frequency2);
                break;
            case R.id.pulse3:
                dialogSetTextView(pulse3);
                break;
            case R.id.frequency3:
                dialogSetTextView(frequency3);
                break;
            case R.id.pulse4:
                dialogSetTextView(pulse4);
                break;
            case R.id.frequency4:
                dialogSetTextView(frequency4);
                break;
        }
    }

    private void dialogSetTextView(TextView textView) {
        if (setValueDialog == null) {
            setValueDialog = new SetValueDialog(getContext());
        }
        setValueDialog.setTextView(textView);
        setValueDialog.show();
    }
}
