package model;

/**
 * @Author: Fisher
 * @Date: 2019/9/26 8:46 上午
 */

public class RegistUser extends User{

    private String psw1;
    private String verifycode1;

    public RegistUser() {
    }

    public RegistUser(String username, String psw, String nickname, String email, String verifycode) {
        super(username, psw, nickname, email, verifycode);
    }

    public String getPsw1() {
        return psw1;
    }

    public void setPsw1(String psw1) {
        this.psw1 = psw1;
    }

    public String getVerifycode1() {
        return verifycode1;
    }

    public void setVerifycode1(String verifycode1) {
        this.verifycode1 = verifycode1;
    }
}
