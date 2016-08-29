package xyz.phyoekhant.padc.firebasepushnotification;

import android.net.Uri;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.plus.model.people.Person;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Phyoe Khant on 8/26/2016.
 */
public class UserVO {

    private String name;

    private String email;

    private String profilePicture;

    private String coverPicture;

    private String facebookId;

    private String googleId;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public static UserVO initFromFacebookData(JSONObject facebookLoginUser, String imgUrl, String coverImgUrl){

        UserVO user = new UserVO();

        try {

            if (facebookLoginUser.has("id")) {
                user.setFacebookId(facebookLoginUser.getString("id"));
            }

            if (facebookLoginUser.has("name")) {
                user.setName(facebookLoginUser.getString("name"));
            }

            if (facebookLoginUser.has("email")) {
                user.setEmail(facebookLoginUser.getString("email"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        user.setProfilePicture(imgUrl);
        user.setCoverPicture(coverImgUrl);

        return user;

    }
}
