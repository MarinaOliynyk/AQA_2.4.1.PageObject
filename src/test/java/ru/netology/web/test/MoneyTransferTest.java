package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;


public class MoneyTransferTest {
    DashboardPage dashboardPage;

    @BeforeEach
    void setUp() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
        val verificationCode = DataHelper.getVerificationCodeFor();
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferLimitMoneyFromFirstToSecond() {
        val amount = 10000;
        val balanceOfFirstCardBefore = dashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardBefore = dashboardPage.getCurrentBalanceOfSecondCard();
        val moneyTransferPage = dashboardPage.chooseSecondCardToRecharge();
        moneyTransferPage.moneyTransferCard(getFirstCardInformation(), amount);
        val balanceOfFirstCardAfter = dashboardPage.getCurrentBalanceOfSecondCard();
        val balanceOfSecondCardAfter = dashboardPage.getCurrentBalanceOfFirstCard();
        val balanceAfterTransactionOnRecharged = checkBalanceOfRechargedCard(balanceOfSecondCardBefore, amount);
        val balanceAfterTransaction = checkBalanceOfCardFromWhereRechargeWasMade(balanceOfFirstCardBefore, amount);
        assertEquals(balanceAfterTransactionOnRecharged, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransaction, balanceOfSecondCardAfter);
    }

    @Test
    void shouldTransferLimitMoneyFromSecondToFirst() {
        val amount = 10000;
        val balanceOfFirstCardBefore = dashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardBefore = dashboardPage.getCurrentBalanceOfSecondCard();
        val moneyTransferPage = dashboardPage.chooseFirstCardToRecharge();
        moneyTransferPage.moneyTransferCard(getSecondCardInformation(), amount);
        val balanceOfFirstCardAfter = dashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardAfter = dashboardPage.getCurrentBalanceOfSecondCard();
        val balanceAfterTransactionOnRecharged = checkBalanceOfRechargedCard(balanceOfFirstCardBefore, amount);
        val balanceAfterTransaction = checkBalanceOfCardFromWhereRechargeWasMade(balanceOfSecondCardBefore, amount);
        assertEquals(balanceAfterTransactionOnRecharged, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransaction, balanceOfSecondCardAfter);
    }

    @Test
    void shouldTransferAvgMoneyFromSecondToFirst() {
        val amount = 5000;
        val balanceOfFirstCardBefore = dashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardBefore = dashboardPage.getCurrentBalanceOfSecondCard();
        val moneyTransferPage = dashboardPage.chooseFirstCardToRecharge();
        moneyTransferPage.moneyTransferCard(getSecondCardInformation(), amount);
        val balanceOfFirstCardAfter = dashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardAfter = dashboardPage.getCurrentBalanceOfSecondCard();
        val balanceAfterTransactionOnRecharged = checkBalanceOfRechargedCard(balanceOfFirstCardBefore, amount);
        val balanceAfterTransaction = checkBalanceOfCardFromWhereRechargeWasMade(balanceOfSecondCardBefore, amount);
        assertEquals(balanceAfterTransactionOnRecharged, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransaction, balanceOfSecondCardAfter);
    }

    @Test
    void shouldTransferNullMoneyFromSecondToFirst() {
        val amount = 0;
        val balanceOfFirstCardBefore = dashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardBefore = dashboardPage.getCurrentBalanceOfSecondCard();
        val moneyTransferPage = dashboardPage.chooseFirstCardToRecharge();
        moneyTransferPage.moneyTransferCard(getSecondCardInformation(), amount);
        val balanceOfFirstCardAfter = dashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardAfter = dashboardPage.getCurrentBalanceOfSecondCard();
        val balanceAfterTransactionOnRecharged = checkBalanceOfRechargedCard(balanceOfFirstCardBefore, amount);
        val balanceAfterTransaction = checkBalanceOfCardFromWhereRechargeWasMade(balanceOfSecondCardBefore, amount);
        assertEquals(balanceAfterTransactionOnRecharged, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransaction, balanceOfSecondCardAfter);
    }

    @Test
    void shouldTransferOverLimitMoneyFromFirstToSecond() {
        val amount = 20000;
        val moneyTransferPage = dashboardPage.chooseSecondCardToRecharge();
        moneyTransferPage.moneyTransferCard(getFirstCardInformation(), amount);
        moneyTransferPage.errorTransfer();
    }
}
