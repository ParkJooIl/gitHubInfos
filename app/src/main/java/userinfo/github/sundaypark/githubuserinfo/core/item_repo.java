package userinfo.github.sundaypark.githubuserinfo.core;

public class item_repo extends item_core {
    String description;
    String name;
    String stargazers_count;
    public item_repo() {
    }
    public String getname(){
        return name;
    }
    public String getdescription(){
        return description;
    }
    public String getstargazers_count(){
        return stargazers_count;
    }
}
