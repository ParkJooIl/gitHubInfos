package userinfo.github.sundaypark.githubuserinfo.listiner;

import java.util.ArrayList;

public interface ConnectionListiner {
    static final int ResultCodeOK = 0; // 결과 받아오기 성공
    static final int ResultCodeNoname = 1; // 이름조회불가
    static final int ResultCodeError = 2; // 기타사유 통신 실패
    public void OnResults(int code , ArrayList<Object> items);
}
