package model;

/**
 * @Author: Fisher
 * @Date: 2019-09-20 09:30
 */

public class User {

    private String username;
    private String psw;
    private String nickname;
    private String email;
    private String verifycode;

    public User() {
    }

    public User(String username, String psw, String nickname, String email, String verifycode) {
        this.username = username;
        this.psw = psw;
        this.nickname = nickname;
        this.email = email;
        this.verifycode = verifycode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }
}
