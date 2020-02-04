class Task3 {
    public static void main(String[] args) {
        Bank.getInstance().register(123456,"Li Zheyuan","320000199903230000","20171344016@nuist.edu.cn","SavingAccount");
        Bank.getInstance().register(123456,"Alice","320000199803230000","alice@nuist.edu.cn","CreditAccount");
        Bank.getInstance().register(123456,"Lance","320000199903230000","lizheyuan.ufo@live.com","SavingAccount");
        Bank.getInstance().login(100000,123457);
        Bank.getInstance().login(100000,123456);
        Bank.getInstance().deposit(100000,6000);
        Bank.getInstance().withdraw(100000,5900);
        Bank.getInstance().setCeiling(100001,1200);
        Bank.getInstance().transfer(100000,100002,50);
    }
}