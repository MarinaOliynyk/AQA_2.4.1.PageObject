package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private final SelenideElement firstCardButton = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] .button");
    private final SelenideElement secondCardButton = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] .button");

    public MoneyTransferPage chooseFirstCardToRecharge() {
        firstCardButton.click();
        return new MoneyTransferPage();
    }

    public MoneyTransferPage chooseSecondCardToRecharge() {
        secondCardButton.click();
        return new MoneyTransferPage();
    }

    public int getCurrentBalanceOfFirstCard() {
        val currentBalance = $(".list__item [data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']").getText();
        return getBalance(currentBalance);
    }

    public int getCurrentBalanceOfSecondCard() {
        val currentBalance = $(".list__item [data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']").getText();
        return getBalance(currentBalance);
    }

    public int getBalance(String currentBalance) {
        val substring = currentBalance.split(",");
        val getArraysLength = substring[substring.length - 1];
        val value = getArraysLength.replaceAll("\\D+", "");
        return Integer.parseInt(value);
    }
}
