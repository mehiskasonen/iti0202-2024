package ee.taltech.iti0202.sportsclub.users.wallet;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WalletTest {

    @Test
    void getAmount() {
        Wallet wallet = new Wallet(BigDecimal.valueOf(30));
        BigDecimal expected = new BigDecimal("30");
        assertEquals(expected, wallet.getAmount());
    }

    @Test
    void setAmount() {
        Wallet wallet = new Wallet(BigDecimal.valueOf(30));
        wallet.setAmount(BigDecimal.valueOf(40));
        BigDecimal expected = new BigDecimal("40");
        assertEquals(expected, wallet.getAmount());
    }

    @Test
    void addToWallet() {
        Wallet wallet = new Wallet(BigDecimal.valueOf(30));
        wallet.addToWallet(BigDecimal.valueOf(10));
        BigDecimal expected = new BigDecimal("40");
        assertEquals(expected, wallet.getAmount());
    }

    @Test
    void subtractFromMemberWallet() {
        Wallet wallet = new Wallet(BigDecimal.valueOf(30));
        wallet.subtractFromMemberWallet(BigDecimal.valueOf(10));
        BigDecimal expected = new BigDecimal("20");
        assertEquals(expected, wallet.getAmount());
    }
}
