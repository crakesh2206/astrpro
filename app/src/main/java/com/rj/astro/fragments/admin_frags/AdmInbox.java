package com.rj.astro.fragments.admin_frags;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.rj.astro.R;
import com.rj.astro.activities.admin.AdminText;
import com.rj.astro.activities.user.AddQuestion;
import com.rj.astro.androidRecyclerView.MessageAdapter;
import com.rj.astro.databases.DbHelper;
import com.rj.astro.databases.PrefManager;
import com.rj.astro.databases.Questions;
import com.rj.astro.simplified.MyFirebaseMessagingService;
import com.rj.astro.util.NetworkStateChecker;
import com.rj.astro.volly.AppController;
import com.rj.astro.volly.ConstantLinks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Codefingers-1 on 06-06-2017.
 */

public class AdmInbox extends Fragment {



    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MessageAdapter mAdapter;
    private List<Questions> messageList;

    private DbHelper dbHelper;
    ImageView mSend;
    private PrefManager pRef;
    private EditText mEditSent;
    private ProgressBar mProgress;
    private RelativeLayout mNodata;

    public static AdmInbox newInstance() {
        AdmInbox f = new AdmInbox();
        return (f);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DbHelper(getActivity());
        pRef = new PrefManager(getActivity());
        getActivity().registerReceiver(new NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_inbox, container, false);
        messageList = new ArrayList<>();
        mRecyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);
        mSend = (ImageView) root.findViewById(R.id.imgsend);
        mNodata = (RelativeLayout) root.findViewById(R.id.noitem);
        mEditSent = (EditText) root.findViewById(R.id.editsend);
        mProgress = (ProgressBar) root.findViewById(R.id.progedit);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        messageList = dbHelper.getAllQuestionsForAdmin(Integer.parseInt(AdminText.uid));
        mAdapter = new MessageAdapter(getActivity(), messageList,pRef.isUserisAdmin());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    sentToServer("", "", mEditSent.getText().toString(), AdminText.uid, AddQuestion.setCreated(), "admin", pRef.getUserName());

            }
        });
        getQuestionListFromServer();
        return root;
    }
    private BroadcastReceiver mMsgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equalsIgnoreCase(MyFirebaseMessagingService.NEW_MESSAGE)){
                Bundle extra = intent.getExtras();
                String qID = extra.getString(MyFirebaseMessagingService.QID);
                String sCat = extra.getString(MyFirebaseMessagingService.CAT);
                String sSubCat = extra.getString(MyFirebaseMessagingService.SUBCAT);
                String sQues = extra.getString(MyFirebaseMessagingService.QUESS);
                String sUsertype = extra.getString(MyFirebaseMessagingService.USER_TY);
                String sUserId = extra.getString(MyFirebaseMessagingService.USER_I_D);
                String sTym = extra.getString(MyFirebaseMessagingService.TYM);

                Questions q = new Questions();
                q.KEY_QID = qID;
                q.KEY_USER_ID = sUserId;
                q.KEY_CATAGORY = sCat;
                q.KEY_SUB_CATAGORY = sSubCat;
                q.KEY_QUESTION = sQues;
                q.KEY_USER_ID = sUserId;
                q.KEY_USERTYPE = sUsertype;
                q.KEY_TIME =sTym;
               dbHelper.createQUESTION_ADMIN(q);

                messageList.clear();
                messageList.addAll(dbHelper.getAllQuestionsForAdmin(Integer.parseInt(pRef.getUserId())));

                mAdapter.notifyDataSetChanged();
                mLayoutManager.scrollToPosition(messageList.size());

            }
        }
    };

    public void getQuestionListFromServer() {


        // Tag used to cancel the request
        String obj_req = "obj_req";

        JsonObjectRequest objJsonreq = new JsonObjectRequest(Request.Method.POST,
                ConstantLinks.USER_ALL_QUESTIONS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error = response.getString("error");

                    if (error.equals("false")) {
                          messageList.clear();
                        JSONArray resType = response.getJSONArray("questions");
                        for (int i = 0; i < resType.length(); i++) {
                            JSONObject obj = resType.getJSONObject(i);
                            Questions ques = new Questions();
                            ques.KEY_QID = obj.getString("qid");
                            ques.KEY_CATAGORY = obj.getString("cat");
                            ques.KEY_SUB_CATAGORY = obj.getString("sub_cat");
                            ques.KEY_QUESTION = obj.getString("ques");
                            ques.KEY_TIME = obj.getString("tm");
                            ques.KEY_USER_ID = obj.getString("user_id");
                            ques.KEY_USERTYPE = obj.getString("usertype");
                            ques.KEY_TOWHO = obj.getString("towho");
                            dbHelper.createQUESTION_ADMIN(ques);
                            messageList.add(ques);

                        }




                    } else {
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                mAdapter.notifyDataSetChanged();
                mRecyclerView.getLayoutManager().smoothScrollToPosition(mRecyclerView,null, messageList.size()-1);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(objJsonreq, obj_req);

    }


    private void sentToServer(String catagory, String subCatagory, final String ques, final String userId, final String time, final String usertype, final String userName) {
        // Tag used to cancel the request
        String tag_sendmsg = "strMSG";
        saveNameToLocalStorage(ques,userId,time ,usertype,userName,"admin",NAME_NOT_SYNCED_WITH_SERVER);
//        mProgress.setVisibility(View.VISIBLE);

        StringRequest strReqTeacherLogin = new StringRequest(Request.Method.POST,
                ConstantLinks.SINGLE_SEND_USER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.has("error")) {
                        //if there is a success
                        //storing the name to sqlite with status synced
                        getQuestionListFromServer();

                        saveNameToLocalStorage(ques,userId,time ,usertype,userName,"admin",NAME_SYNCED_WITH_SERVER);

                    } else {
                        //if there is some error
                        //saving the name to sqlite with status unsynced
                        // saveNameToLocalStorage(ques,userId,time ,usertype,userName,"admin",NAME_NOT_SYNCED_WITH_SERVER);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                mEditSent.setText("");


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("eerror", "Error: " + error.getMessage());

                //on error storing the name to sqlite with status unsynced
                //  saveNameToLocalStorage(ques,userId,time ,usertype,userName,"admin",NAME_NOT_SYNCED_WITH_SERVER);


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ques", ques);
                params.put("user_id",userId);
                params.put("time", time);
                params.put("usertype", usertype);
                params.put("username", "Admin");
                params.put("towho", AdminText.uid); //32 admin

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReqTeacherLogin, tag_sendmsg);

    }

    private void saveNameToLocalStorage(String ques, String userId, String time, String usertype, String userName, String toWhom, int nameSyncedWithServer) {

    }

    public String convertTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

}
