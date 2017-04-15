package zone.iquest.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;
import zone.iquest.R;
import zone.iquest.Utils.PreferenceHelper;

public class EditDataDialogFragment extends SupportBlurDialogFragment {

    private PreferenceHelper mPreferenceHelper;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.edit_data_dialog_layout, null);

        PreferenceHelper.getInstance().init(getActivity());
        mPreferenceHelper = PreferenceHelper.getInstance();

        final AppCompatEditText editName = (AppCompatEditText) view.findViewById(R.id.editName);
        final AppCompatEditText editEmail = (AppCompatEditText) view.findViewById(R.id.editEmail);
        final AppCompatEditText editPhone = (AppCompatEditText) view.findViewById(R.id.editPhone);
        TextView btnSave = (TextView) view.findViewById(R.id.btnSave);


        editName.setText(mPreferenceHelper.getName(PreferenceHelper.NAME_KEY));
        editEmail.setText(mPreferenceHelper.getEmail(PreferenceHelper.EMAIL_KEY));
        editPhone.setText(mPreferenceHelper.getPhone(PreferenceHelper.PHONE_KEY));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editName.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();

                if (isEmailValid(email)) {
                    if (!name.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {

                        mPreferenceHelper.saveName(PreferenceHelper.NAME_KEY, name);
                        mPreferenceHelper.saveEmail(PreferenceHelper.EMAIL_KEY, email);
                        mPreferenceHelper.savePhone(PreferenceHelper.PHONE_KEY, phone);
                        mPreferenceHelper.saveSplash(PreferenceHelper.SPLASH_KEY, true);

                        InputMethodManager imm = (InputMethodManager) v.getContext().
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        dismiss();
                        Toast.makeText(getContext(),
                                "Данные успешно сохранены!", Toast.LENGTH_LONG)
                                .show();
                    } else {
                        Toast.makeText(getContext(),
                                "Пожалуйста, введите данные!", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(getContext(),
                            "Неверный формат email", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
        builder.setView(view);
        return builder.create();
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
