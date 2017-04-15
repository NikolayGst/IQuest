package zone.iquest.Fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import zone.iquest.R;

public class AboutFragment extends Fragment implements View.OnClickListener {

    private ImageView mMap;
    private ImageView mVk;
    private ImageView mInstagram;
    private ImageView mFb;
    private Button mButtonOurRules;

    public AboutFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        initView(view);

        mMap.setOnClickListener(this);
        mVk.setOnClickListener(this);
        mInstagram.setOnClickListener(this);
        mFb.setOnClickListener(this);
        mButtonOurRules.setOnClickListener(this);

        return view;
    }

    private void initView(View view) {
        mMap = (ImageView) view.findViewById(R.id.map);
        mVk = (ImageView) view.findViewById(R.id.vk);
        mInstagram = (ImageView) view.findViewById(R.id.insta);
        mFb = (ImageView) view.findViewById(R.id.fb);
        mButtonOurRules = (Button) view.findViewById(R.id.btnOurRules);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map:
                break;
            case R.id.vk:
                intentVk();
                break;
            case R.id.insta:
                intentInsta();
                break;
            case R.id.fb:
                intentFb();
                break;
            case R.id.btnOurRules:
                showRules();
                break;
        }
    }

    private void intentVk() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("vkontakte://profile/iquest_mel"));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://vk.com/iquest_mel"));
            startActivity(intent);
        }
    }

    private void intentInsta() {
        Uri uri = Uri.parse("http://instagram.com/_u/iquestmelitopol");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.instagram.android");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/iquestmelitopol")));
        }
    }

    private void intentFb() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://profile/100011022154255"));
            startActivity(intent);

        } catch (Exception e) {

            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.facebook.com/iquest.melitopol")));
        }
    }

    private void showRules() {

        RulesDialogFragment rulesDialog = new RulesDialogFragment();
        rulesDialog.show(getActivity().getSupportFragmentManager(), "fragment_rules");
    }
}
