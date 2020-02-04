class Account {
	public static long id_top=100000;
    public long id;
	private int password;
	private String name;
	private String personId;
	private String email;
	protected double balance; //’Àªß”‡∂Ó

	Account() {
		id=0;
		password=123456;
		name="’≈»˝";
		personId="123456789123456789";
		email="123@bank.com";
	}
	Account(long i,int p,String n,String pe,String e) {
		id=id_top++;
		password=p;
		name=n;
		personId=pe;
		email=e;
		balance=0;
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
		balance=num;
	}
	public static void checkBalance(Account a) {
		System.out.println(a.id+" "+a.balance);
	}
	public static Account deposit(Account a,double num) {
		a.setBalance(num+a.balance);
		return a;
	}
	public  static Account withdraw(Account a,double num) {
		if(num<=a.balance) {a.setBalance(a.balance-num); return a;}
		else System.out.println("Failed.");
		return null;
	}

	@Override
	public String toString() {
		return "id:"+id;
	}
}