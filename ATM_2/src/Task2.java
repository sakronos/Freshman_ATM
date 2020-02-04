class SavingAccount extends Account {
	SavingAccount(long i, int p, String n, String pe, String e) {
		super(i, p, n, pe, e);
	}
}

class CreditAccount extends Account {
	protected double ceiling=0;
    private double used_quota=0;
	CreditAccount(long i, int p, String n, String pe, String e) {
		super(i, p, n, pe, e);
	}

	public double getCeiling() {
		return ceiling;
	}

	public CreditAccount setCeiling(CreditAccount a, double m) {
		ceiling = m;
		return a;
	}

	public double getUsed_quota() {
		return used_quota;
	}

	private void setUsed_quota(double used_quota) {
		this.used_quota = used_quota;
	}

	public static Account withdraw(CreditAccount b, double num) {
	    CreditAccount a=b;
		if (num <= a.balance + a.ceiling-a.used_quota) {
			if (num <= a.balance)
            {a.setBalance(a.balance - num);

            }
			else {
				a.setBalance(0);
				a.setUsed_quota(num-a.balance);
			}
			return a;
		} else
			System.out.println("Failed.");
		return null;
	}

	public static Account repay(CreditAccount a, double num) { // 还款方法
		if (num <= a.used_quota) {
			a.used_quota -= num;
		} else {
			a.setUsed_quota(0);
			deposit(a, num - a.used_quota);

		}
		return a;

	}
}

class Bank {
	//TODO 改写为Arrays
	private Account[] Accounts = new Account[100];
    private double SumOfBalance=0;
	private double SumOfCeiling=0;
	public Bank() {

	}
	private static int quantity = 0;
	//密码，名字，身份证，邮箱，账户类型
	public Account register(int p,String n,String pe,String e,String type) {
		quantity++;

		if(type=="SavingAccount") {
			Accounts[(int)(Account.id_top-100000)] =new SavingAccount(Account.id_top,p,n,pe,e);
			System.out.println("SavingAccount "+Accounts[(int)(Account.id_top-100001)]+" is created.");
			return Accounts[(int)(Account.id_top-100001)];
		}
		else
		{
			Accounts[(int)(Account.id_top-100000)]=new CreditAccount(Account.id_top,p,n,pe,e);
			//SumOfCeiling+=1000;
			System.out.println("CreditAccount "+Accounts[(int)(Account.id_top-100001)]+" is created.");
			return Accounts[(int)(Account.id_top-100001)];
		}

	}
	public Account login(long i,int p) {  //ID+密码
		if (Accounts[(int)i-100000].getPassword()==p) {
			System.out.println(Accounts[(int)i-100000]+" log in successfully.");
			return Accounts[(int)i-100000];
		}
		System.out.println("Failed, please try again.");
        return null;
    }
    public  Account deposit(long id,double num){
	    Account t=Accounts[(int)id-100000];
	    t.setBalance(t.balance+num);
		System.out.println(t+" 余额： "+t.getBalance());
	    SumOfBalance+=num;
	    return  t;
    }
    public Account withdraw(long id,double num) {
        if(Account.withdraw(Accounts[(int)id-100000],num)!=null) {
			SumOfBalance-=num;
			System.out.println(Accounts[(int)id-100000]+" 余额： "+Accounts[(int)id-100000].getBalance());
		}
        return Accounts[(int)id-100000];
    }
    public Account setCeiling(long id,double num) {
	    if(Accounts[(int)id-100000] instanceof CreditAccount) {
	        CreditAccount tr=(CreditAccount) Accounts[(int)id-100000];
	        SumOfCeiling-=tr.ceiling;
            SumOfCeiling+=num;
            tr.ceiling=num;
			System.out.println(tr+" 透支额度："+tr.getCeiling());
            return  tr;
        }
        return null;
    }
    public boolean transfer(long from,long to,double money) {
	    if(Accounts[(int)from-100000] instanceof  CreditAccount) return false;
	    else {
	        if (Accounts[(int)from-100000].getBalance()<money) return false;
	        else {
	            Account.withdraw(Accounts[(int)from-100000],money);
	            Account.deposit(Accounts[(int)to-100000],money);
	            return true;
            }
        }
    }
    public double getSumOfBalance() {
        return SumOfBalance;
    }
    public double getSumOfCeiling() {
        return SumOfCeiling;
    }
}

public class Task2 {
	public static void main(String[] args) {
		Bank bank=new Bank();
		bank.register(123456,"Li Zheyuan","320000199903230000","20171344016@nuist.edu.cn","SavingAccount");
		bank.register(123456,"Alice","320000199803230000","alice@nuist.edu.cn","CreditAccount");
		bank.register(123456,"Lance","320000199903230000","lizheyuan.ufo@live.com","SavingAccount");
		bank.login(100000,123457);
		bank.login(100000,123456);
		bank.deposit(100000,6000);
		bank.withdraw(100000,5900);
		bank.setCeiling(100001,1200);
		bank.transfer(100000,100002,50);
	}
}
