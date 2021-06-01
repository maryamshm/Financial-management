package ir.maryamsh.financialmanagement;

public class Users {
    private String name,email,pass;

    public Users (){}

    public Users(String name,String pass,String email){
        this.name=name;
        this.email=email;
        this.pass=pass;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
