package BAMSException;

public class BalanceNotEnoughException extends ATMException {
    public BalanceNotEnoughException() {
    }

    public BalanceNotEnoughException(String message) {
        super(message);
    }
}
