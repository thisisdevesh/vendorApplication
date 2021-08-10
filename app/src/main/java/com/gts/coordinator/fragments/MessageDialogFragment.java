package com.gts.coordinator.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gts.coordinator.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class MessageDialogFragment extends DialogFragment implements View.OnClickListener {
    //    public final static String ARG_HELP_LAYOUT= "ARG_HLP_LAYOUT", ARG_CLOSE_VIEW="ARG_CLOSE_VIEW";
    private int layout = -1, viewClose = -1;
    private String title, message;
    ImageView imageView;
    private View closeView;
    private boolean isCancellable;

    public static MessageDialogFragment newInstance(int layout, int viewClose, boolean isCancellable) {
        MessageDialogFragment hlpFrag = new MessageDialogFragment();
//        hlpFrag.setStyle(STYLE_NO_TITLE,0);
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("ARG_HLP_LAYOUT", layout);
        args.putInt("ARG_CLOSE_VIEW", viewClose);
        args.putBoolean("ARG_IS_CANCELLABLE", isCancellable);
        hlpFrag.setArguments(args);

        return hlpFrag;
    }

    public static MessageDialogFragment newInstance(String title, String message, boolean isCancellable) {
        MessageDialogFragment hlpFrag = new MessageDialogFragment();
//        hlpFrag.setStyle(STYLE_NO_TITLE,0);
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("ARG_HLP_TITLE", title);
        args.putString("ARG_HLP_MSG", message);
        args.putBoolean("ARG_IS_CANCELLABLE", isCancellable);
        hlpFrag.setArguments(args);

        return hlpFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setStyle(STYLE_NO_TITLE, 0);
        Bundle b = getArguments();
        if (b != null) {
            layout = b.getInt("ARG_HLP_LAYOUT");
            viewClose = b.getInt("ARG_CLOSE_VIEW");
//            Log.i("MessageDialogFragment","onCreate():layout="+layout+";\tviewClose="+viewClose);
            title = b.getString("ARG_HLP_TITLE");
            message = b.getString("ARG_HLP_MSG");
            isCancellable = b.getBoolean("ARG_IS_CANCELLABLE");
//            Log.i("MessageDialogFragment","onCreate():driverName="+driverName+";\tmessage="+message);
        }
        setCancelable(isCancellable);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Log.i("MessageDialogFragment","onCreateView(): layout="+layout);
        View v = inflater.inflate(R.layout.fragment_message_dialog, container, false);
        try {

            if (message == null) {
               // v = inflater.inflate(layout, container, false);
//            v.findViewById(viewClose).setOnClickListener(this);
                closeView = v.findViewById(viewClose);
            } else {
                v = inflater.inflate(R.layout.fragment_message_dialog, container, false);
                imageView = v.findViewById(R.id.id_image_view_dialog);
                if (title == null || "".equals(title)) {
                    v.findViewById(R.id.title).setVisibility(View.GONE);
                    v.findViewById(R.id.divider).setVisibility(View.GONE);
                } else {
                    ((TextView) v.findViewById(R.id.title)).setText(title);
                    if (title.equals(getString(R.string.error))) {
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_info_icon));
                    } else if (title.equals(getString(R.string.successful))) {
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_successful_icon));
                    } else if (title.equals(getString(R.string.info))) {
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_info_icon));
                    } else {
                        imageView.setVisibility(View.GONE);
                    }
                }
                ((TextView) v.findViewById(R.id.message)).setText(message);
                closeView = v.findViewById(R.id.btn_close);
            }
            closeView.setOnClickListener(this);
        }catch (Exception e){

        }

        return v;
    }

    @Override
    public void onClick(View v) {
//        if(v==closeView)
        if (v == closeView) {
            dismiss();
        }
    }

    public void show(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
//        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        android.app.Fragment prev = fm.findFragmentByTag("MsgAlert");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        super.show(ft, "MsgAlert");
//        ft.commit();
    }

    @Override
    public void onResume() {
        //   Log.v(TAG, "onResume");
        if (getShowsDialog()) {
            // Set the width of the dialog to the width of the screen in portrait mode
            DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
            int dialogWidth = Math.min(metrics.widthPixels - 30, metrics.heightPixels);
            getDialog().getWindow().setLayout(dialogWidth, WRAP_CONTENT);
        }
        super.onResume();
    }
}
