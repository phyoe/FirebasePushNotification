package xyz.phyoekhant.padc.firebasepushnotification;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by Phyoe Khant on 8/26/2016.
 */
public class UserModel {

    private static UserModel objInstance;

    private UserVO userVO;

    private UserModel() {
        super();
    }

    public static UserModel getInstance() {
        if (objInstance == null) {
            objInstance = new UserModel();
        }
        return objInstance;
    }

    public void connectWithFacebook(JSONObject facebookLoginUser, String imgUrl, String coverImgUrl){
        userVO = UserVO.initFromFacebookData(facebookLoginUser,imgUrl,coverImgUrl);
    }

}
