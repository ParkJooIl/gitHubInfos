package userinfo.github.sundaypark.githubuserinfo.core;

public class item_userinfo extends item_core{
    String login ;
    String avatar_url ;
    String url;

    @Override
    public int getTYPE() {
        return ITEMCODE_USER;
    }
    public String getlogin(){
        return login;
    }
    public String getavatar_url(){
        return avatar_url;
    }

}
