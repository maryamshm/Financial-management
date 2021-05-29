package ir.maryamsh.financialmanagement;

public class NewTransaction {
    String name,price,type,date,des;
    String id;
    public NewTransaction(String name, String price, String date, String des, String type, String id){
        this.name=name;
        this.price=price;
        this.type=type;
        this.date=date;
        this.des=des;
        this.id=id;
    }
    public  NewTransaction(){}
    public String getDate() {
        return date;
    }

    public String getId(){return id;}

    public String getDes() {
        return des;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id){ this.id=id; }
}
