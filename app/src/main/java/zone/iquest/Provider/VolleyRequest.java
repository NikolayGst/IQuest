package zone.iquest.Provider;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zone.iquest.Fragment.CalendarFragment;
import zone.iquest.Fragment.SeanceDialogFragment;
import zone.iquest.Fragment.SeanceFragment;
import zone.iquest.MainActivity;
import zone.iquest.Model.Seance;
import zone.iquest.R;
import zone.iquest.Utils.AppConfig;
import zone.iquest.Utils.PreferenceHelper;


public class VolleyRequest {

    private static final String ARG_QUEST = "quest";
    private static final String ARG_QUEST_DATE = "quest_date";
    private static final String ARG_QUEST_ID = "quest_id";
    private static final String TAG = VolleyRequest.class.getSimpleName();
    private ProgressDialog pDialog;
    private PreferenceHelper mPreferenceHelper;

    public void getDay(final Context context, final int questId, final List<HashMap<String, String>> listDate) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Загрузка данных ...");
        pDialog.setCancelable(false);
        String str_tag = "str_tag";
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.urlGetDay, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArrayQuestDay = new JSONArray(response);
                    for (int i = 0; i < jsonArrayQuestDay.length(); i++) {
                        JSONObject jsonObjQuestDay = jsonArrayQuestDay.getJSONObject(i);
                        JSONObject quest = jsonObjQuestDay.getJSONObject("quest");
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("mDate", quest.getString("date"));
                        hashMap.put("mStatus", quest.getString("status"));
                        listDate.add(hashMap);
                    }
                    hideDialog();
                    CalendarFragment.getInstance().setDate();
                } catch (JSONException e) {
                    Log.d(TAG, "JSONException: " + e.getMessage());
                    hideDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("quest_id", String.valueOf(questId));
                return params;
            }
        };


        //тайм аут для запроса.
        timeout(stringRequest, 10000);

        AppController.getInstance().addToRequestQueue(stringRequest, str_tag);
    }

    public void getNewStatus(final Context context, final int questId,
                             final String date, final List<Seance> seanceList, final RecyclerView recyclerView) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Загрузка данных ...");
        pDialog.setCancelable(false);
        String str_tag = "str_tag";
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.urlGetQuestList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArrayQuestDay = new JSONArray(response);
                    for (int i = 0; i < jsonArrayQuestDay.length(); i++) {
                        Seance seance = new Seance();
                        JSONObject jsonObjQuestDay = jsonArrayQuestDay.getJSONObject(i);
                        JSONObject quest = jsonObjQuestDay.getJSONObject("quest");
                        int seanceId = quest.getInt("seance_id");
                        int status = quest.getInt("status");
                        seance.setSeanceId(seanceId);
                        seance.setStatus(status);
                        seanceList.set(seanceId - 1, seance);
                    }

                    hideDialog();
                    recyclerView.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    Log.d(TAG, "JSONException: " + e.getMessage());
                    hideDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("date", date);
                params.put("quest_id", String.valueOf(questId));
                return params;
            }
        };

        //тайм аут для запроса.
        timeout(stringRequest, 10000);

        AppController.getInstance().addToRequestQueue(stringRequest, str_tag);
    }

    public void registerSeance(final Context context, final SeanceDialogFragment fragment,
                               final String date, final int questId, final int seanceId, final String questName) {
        PreferenceHelper.getInstance().init(context);
        mPreferenceHelper = PreferenceHelper.getInstance();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Регистрация сеанса...");
        pDialog.setCancelable(false);
        String str_tag = "str_tag";
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.urlRegisterSeance, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("msg");
                    if (status.equals("Successfully")) {
                        Toast.makeText(context, "Ваша заявка на рассмотрении",
                                Toast.LENGTH_LONG).show();
                        fragment.dismiss();

                        Bundle bundle = new Bundle();
                        bundle.putString(ARG_QUEST, questName);
                        bundle.putInt(ARG_QUEST_ID, questId);
                        bundle.putString(ARG_QUEST_DATE, date);

                        SeanceFragment seanceFragment = new SeanceFragment();
                        seanceFragment.setArguments(bundle);

                        ((MainActivity) context).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, seanceFragment)
                                .addToBackStack("quest")
                                .commit();

                        ((MainActivity) context).getSupportFragmentManager().popBackStackImmediate();

                        Log.d(TAG, "onResponse: " + ((MainActivity) context).getSupportFragmentManager().getBackStackEntryCount());
                    } else {
                        Toast.makeText(context, "Ошибка регестрации",
                                Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.d(TAG, "JSONException: " + e.getMessage());
                    hideDialog();
                }
                hideDialog();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String username = mPreferenceHelper.getName(PreferenceHelper.NAME_KEY);
                String email = mPreferenceHelper.getName(PreferenceHelper.EMAIL_KEY);
                String telephone = mPreferenceHelper.getName(PreferenceHelper.PHONE_KEY);
                params.put("date", date);
                params.put("quest_id", String.valueOf(questId));
                params.put("seance_id", String.valueOf(seanceId));
                params.put("username", username);
                params.put("email", email);
                params.put("telephone", telephone);
                return params;
            }
        };

        //тайм аут для запроса.
        timeout(stringRequest, 10000);

        AppController.getInstance().addToRequestQueue(stringRequest, str_tag);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void timeout(Request request, final int timeOut) {
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return timeOut;
            }

            @Override
            public int getCurrentRetryCount() {
                return timeOut;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

}
