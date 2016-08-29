package xyz.phyoekhant.padc.firebasepushnotification;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener  {

    @BindView(R.id.btn_connect_with_facebook)
    Button btnConnectWithFacebook;

    @BindView(R.id.btn_connect_with_google)
    Button btnConnectWithGoogle;

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    @BindView(R.id.iv_profile_cover)
    ImageView ivProfileCover;

    @BindView(R.id.tv_name)
    TextView tvUsername;

    @BindView(R.id.tv_email)
    TextView tvEmail;

    protected static final int RC_GOOGLE_SIGN_IN = 1236;

    private CallbackManager mCallbackManager;
    private AccessTokenTracker mAccessTokenTracker;

    protected GoogleApiClient mGoogleApiClient;


    public MainActivityFragment() {
    }

    public static MainActivityFragment newInstance() {
        MainActivityFragment fragment = new MainActivityFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        mCallbackManager = CallbackManager.Factory.create();
        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    Log.d(FirebasePushNotificationApp.TAG, "Logout from Facebook");
                }
            }
        };

        btnConnectWithGoogle.setOnClickListener(this);

        btnConnectWithFacebook.setOnClickListener(this);
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        processFacebookInfo(loginResult);
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(FirebasePushNotificationApp.getContext())
                .enableAutoManage(getActivity(),this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .addApi(Plus.API)
                .build();
        return rootView;
    }
    private void processFacebookInfo(LoginResult loginResult) {

        final AccessToken accessToken = loginResult.getAccessToken();
        FacebookUtils.getInstance().requestFacebookLoginUser(accessToken, new FacebookUtils.FacebookGetLoginUserCallback() {
            @Override
            public void onSuccess(final JSONObject facebookLoginUser) {
                FacebookUtils.getInstance().requestFacebookProfilePhoto(accessToken, new FacebookUtils.FacebookGetPictureCallback() {
                    @Override
                    public void onSuccess(final String profilePhotoUrl) {
                        FacebookUtils.getInstance().requestFacebookCoverPhoto(accessToken, new FacebookUtils.FacebookGetPictureCallback() {
                            @Override
                            public void onSuccess(final String coverPhotoUrl) {

                                onLoginWithFacebook(facebookLoginUser, profilePhotoUrl, coverPhotoUrl);

                            }
                        });
                    }
                });
            }
        });

    }

    private void onLoginWithFacebook(JSONObject facebookLoginUser, String imageUrl, String coverImageUrl) {

        try {

            if (facebookLoginUser.has("name")){
                tvUsername.setText(facebookLoginUser.getString("name"));
            }

            if (facebookLoginUser.has("email")){
                tvEmail.setText(facebookLoginUser.getString("email"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Glide.with(ivProfile.getContext()).load(imageUrl).centerCrop().crossFade().error(R.mipmap.ic_launcher).into(ivProfile);
        Glide.with(ivProfileCover.getContext()).load(coverImageUrl).centerCrop().crossFade().error(R.mipmap.ic_launcher).into(ivProfileCover);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_connect_with_facebook:
                connectToFacebook();
                break;

            case R.id.btn_connect_with_google:
                connectToGoogle();
                break;
        }

    }

    private void connectToGoogle() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RC_GOOGLE_SIGN_IN);

    }


    private void connectToFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(FacebookUtils.FACEBOOK_LOGIN_PERMISSIONS));
        } else {
            LoginManager.getInstance().logOut();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GOOGLE_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            Person registeringUser = null;
            if (mGoogleApiClient.hasConnectedApi(Plus.API)) {
                registeringUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            }

            processGoogleInfo(result, registeringUser);
        }

    }

    private void processGoogleInfo(GoogleSignInResult result, Person registeringUser) {

        if (result.isSuccess()) {

            GoogleSignInAccount signInAccount = result.getSignInAccount();
            onLoginWithGoogle(signInAccount, registeringUser);

        } else {
            // Signed out, show unauthenticated UI.
        }

    }

    private void onLoginWithGoogle(GoogleSignInAccount signInAccount, Person registeringUser) {

        tvUsername.setText(signInAccount.getDisplayName());
        tvEmail.setText(signInAccount.getEmail());

        Uri imageUri = signInAccount.getPhotoUrl();
        if (imageUri != null){
            Glide.with(ivProfile.getContext()).load(imageUri.toString()).centerCrop().into(ivProfile);
        }

        if (registeringUser != null){
            Glide.with(ivProfile.getContext()).load(registeringUser.getCover().getCoverPhoto().getUrl()).centerCrop().into(ivProfileCover);
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        mAccessTokenTracker.startTracking();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAccessTokenTracker.stopTracking();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
