package pages;

import io.qameta.allure.Step;
import locators.Locators;
import static com.codeborne.selenide.Selenide.open;

public class TextBoxPage {

    public static final String FULL_NAME_INPUT = "userName";
    public static final String EMAIL_INPUT = "userEmail";
    public static final String CURRENT_ADDRESS_INPUT = "currentAddress";
    public static final String PERMANENT_ADDRESS_INPUT = "permanentAddress";
    public static final String SUBMIT_BUTTON = "submit";

    // Блок вывода: name/email по id, адреса по css
    public static final String OUTPUT_NAME = "name";
    public static final String OUTPUT_EMAIL = "email";
    public static final String OUTPUT_CURRENT_ADDRESS = "#output #currentAddress";
    public static final String OUTPUT_PERMANENT_ADDRESS = "#output #permanentAddress";

    public TextBoxPage openPage() {
        open("/text-box");
        return this;
    }

    @Step("Вводим полное имя: {name}")
    public TextBoxPage setFullName(String name) {
        Locators.id(FULL_NAME_INPUT).setValue(name);
        return this;
    }

    @Step("Вводим email: {email}")
    public TextBoxPage setEmail(String email) {
        Locators.id(EMAIL_INPUT).setValue(email);
        return this;
    }

    @Step("Вводим текущий адрес: {address}")
    public TextBoxPage setCurrentAddress(String address) {
        Locators.id(CURRENT_ADDRESS_INPUT).setValue(address);
        return this;
    }

    @Step("Вводим постоянный адрес: {address}")
    public TextBoxPage setPermanentAddress(String address) {
        Locators.id(PERMANENT_ADDRESS_INPUT).setValue(address);
        return this;
    }

    @Step("Нажимаем кнопку Submit")
    public TextBoxPage submit() {
        Locators.id(SUBMIT_BUTTON).click();
        return this;
    }

    public String getOutputName() {
        return Locators.id(OUTPUT_NAME).getText();
    }

    public String getOutputEmail() {
        return Locators.id(OUTPUT_EMAIL).getText();
    }

    public String getOutputCurrentAddress() {
        return Locators.css(OUTPUT_CURRENT_ADDRESS).getText();
    }

    public String getOutputPermanentAddress() {
        return Locators.css(OUTPUT_PERMANENT_ADDRESS).getText();
    }
}

