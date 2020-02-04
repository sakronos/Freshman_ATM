public class Account {
    private long id;
    private  String password;
    private  String name;
    private  String personId;
    private String email;
    private double balance;

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPersonId() {
        return personId;
    }

    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public Account deposit(double money) {
        setBalance(money+getBalance());
        return this;
    }

    public Account withdraw(double money) {
        setBalance(getBalance()-money);
        return this;

    }
    public Account() {

    }

    public Account(long id, String password, String name, String personId, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.personId = personId;
        this.email = email;
    }
}
