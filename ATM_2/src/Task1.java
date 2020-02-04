
public class Task1 {
	public static void main(String[] args){
		Account a1=new Account();
		Account a2=new Account(1,654321,"ÀîËÄ","987654321987654321","321@bank.com");
		Account.deposit(a1,1000);
	    Account.checkBalance(a1);
	}
}
