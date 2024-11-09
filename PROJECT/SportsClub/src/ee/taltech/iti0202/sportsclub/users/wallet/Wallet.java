package ee.taltech.iti0202.sportsclub.users.wallet;

import java.math.BigDecimal;

public class Wallet {
    private BigDecimal walletAmount;

    /**
     * Class constructor for users wallet.
     * @param walletAmount initial amount of money the wallet has.
     */
    public Wallet(BigDecimal walletAmount) {
        this.walletAmount = walletAmount;
    }

    /**
     * Getter for wallet amount.
     * @return amount of money in wallet.
     */
    public BigDecimal getAmount() {
        return walletAmount;
    }

    /**
     * Setter for wallet amount.
     * @param amount the wallet is to be set to.
     */
    public void setAmount(BigDecimal amount) {
        this.walletAmount = amount;
    }

    /**
     * @param amount to be added to wallet.
     */
    public void addToWallet(BigDecimal amount) {
        this.walletAmount = walletAmount.add(amount);
    }

    /**
     * @param amount to be subtracted from wallet.
     */
    public void subtractFromMemberWallet(BigDecimal amount) {
        if (walletAmount.compareTo(amount) >= 0) {
            this.walletAmount = walletAmount.subtract(amount);
        } else {
            throw new IllegalArgumentException("Insufficient funds: Cannot subtract "
                    + amount + "from wallet with balance " + walletAmount);
        }
    }
}
