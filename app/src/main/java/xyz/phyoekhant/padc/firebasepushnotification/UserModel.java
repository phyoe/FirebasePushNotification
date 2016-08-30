package xyz.phyoekhant.padc.firebasepushnotification;

import org.json.JSONObject;

/**
 * Created by Phyoe Khant on 8/26/2016.
 */
public class UserModel {

    private static UserModel objInstance;

    private UserVO userVO;

    private UserModel() {

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
