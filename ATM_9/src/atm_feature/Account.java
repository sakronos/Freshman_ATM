package atm_feature;
import BAMSException.BalanceNotEnoughException;

import java.io.*;
import java.util.Objects;

public abstract class Account implements Serializable  {
    private static final  long serialVersionUID = 1L;
    public long id;
    private int password;
    private String name;
    private String personId;
    private String email;
    protected double balance; //账户余额

    public double getLoan() {
        return loan;
    }

    public void setLoan(double loan) {
        this.loan = loan;
    }

    protected double loan;

    Account(long i, int p, String n, String pe, String e) throws IOException {
        File idnext=new File("D:\\C\\Java\\ATM_8\\idnext.txt");
        if (!idnext.exists()){
            try {
                idnext.createNewFile();
                RandomAccessFile out=new RandomAccessFile(idnext,"rw");
                out.writeLong(100000);
                out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        id = getId_top();
        Id_topPlus();
        password = p;
        name = n;
        personId = pe;
        email = e;
        balance = 0;
    }
    public long getId_top() throws IOException {
        File idnext=new File("D:\\C\\Java\\ATM_8\\idnext.txt");
        RandomAccessFile in=new RandomAccessFile(idnext,"r");
        long idtop=in.readLong();
        in.close();
        return idtop;
    }
    public void Id_topPlus() throws IOException {
        File idnext=new File("D:\\C\\Java\\ATM_8\\idnext.txt");
        RandomAccessFile out=new RandomAccessFile(idnext,"rw");
        long temp=out.readLong()+1;
        out.seek(0);
        out.writeLong(temp);
        out.close();
    }

    protected int getPassword() {
        return password;
    }

    public long getId() {
        return id;
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

    public void setPassword(int password) {
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

    protected void setBalance(double num) {
        balance = num;
    }

    public static void checkBalance(Account a) {
        System.out.println(a.id + " " + a.balance);
    }

    public final static Account deposit(Account a, double num) {
        a.setBalance(num + a.balance);
        return a;
    }

    public abstract boolean withdraw(double num) throws BalanceNotEnoughException;

    @Override
    public String toString() {
        return "id:" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                password == account.password &&
                Double.compare(account.balance, balance) == 0 &&
                Double.compare(account.loan, loan) == 0 &&
                Objects.equals(name, account.name) &&
                Objects.equals(personId, account.personId) &&
                Objects.equals(email, account.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, name, personId, email, balance, loan);
    }

    public boolean Eq(Account a) {  //判断身份证号是否一致来判定个人名下所有的账户
        if (personId.equals(a.personId)) return true;
        else return false;
    }
}