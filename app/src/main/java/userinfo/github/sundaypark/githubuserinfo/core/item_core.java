package userinfo.github.sundaypark.githubuserinfo.core;

public class item_core {
    public static final int ITEMCODE_USER = 1;
    public static final int ITEMCODE_REPO = 2;
    private int item_type = ITEMCODE_REPO;
    public int getTYPE(){
        return item_type;
    }
}
