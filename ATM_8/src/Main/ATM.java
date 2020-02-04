package Main;

import BAMSException.LoanException;
import BAMSException.LoginException;
import BAMSException.RegisterException;
import atm_feature.Bank;

import java.io.IOException;

public class ATM {
    public static void main(String[] args) throws IOException, RegisterException, LoginException, ClassNotFoundException, LoanException {
        Bank.getInstance().Deserialize();
        //Bank.getInstance().deposit(100002,500);
        Bank.getInstance().login(100003,123456);
        Bank.getInstance().requestLoan(100003,500);
    }
}
