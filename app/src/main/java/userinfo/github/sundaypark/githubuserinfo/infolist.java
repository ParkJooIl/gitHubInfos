package userinfo.github.sundaypark.githubuserinfo;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import userinfo.github.sundaypark.githubuserinfo.adapter.UserListView;
import userinfo.github.sundaypark.githubuserinfo.listiner.ConnectionListiner;


public class infolist extends AppCompatActivity implements ConnectionListiner {
    ArrayList<Object> item_cores  = new ArrayList<>();
    RecyclerView recyclerView ;
    UserListView mUserListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.projectlist);
        recyclerView = (RecyclerView)findViewById(R.id.repos);
        mUserListAdapter = new UserListView(this,item_cores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mUserListAdapter);

        Onload(getIntent());
    }
    private void Onload(Intent intentinfo){
        if(intentinfo != null){
            Uri uri = intentinfo.getData();
            if(uri != null){
                new aSyncuserinfo(this).execute(uri.getPath().substring(1));
            }else{
                Toast.makeText(this, "스키마가 없습니다 . 기본 테스터 아이디를 보여줍니다 , ", Toast.LENGTH_LONG).show();
                new aSyncuserinfo(this).execute("JooilPark");
            }

        }else{
            Toast.makeText(this, "스키마가 없습니다 . 기본 테스터 아이디를 보여줍니다 , ", Toast.LENGTH_LONG).show();
            new aSyncuserinfo(this).execute("JooilPark");

        }
    }


    @Override
    public void OnResults(int code, ArrayList<Object> items) {
        switch (code){
            case ConnectionListiner.ResultCodeError:
                Toast.makeText(this, "네트워크 혹은 알수 없는 문제가 발생했습니다 ", Toast.LENGTH_LONG).show();
                finish();;
                break;
            case ConnectionListiner.ResultCodeNoname:
                Toast.makeText(this, "조회되지 않는 사용자 입니다 .", Toast.LENGTH_LONG).show();
                finish();
                break;
            default:
                Toast.makeText(this, "조회 성공 ", Toast.LENGTH_LONG).show();
                item_cores.clear();;
                item_cores.addAll(items);
                mUserListAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
