package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selectors.byId;

public class TextBoxPage {

    private final SelenideElement fullNameInput = $(byId("userName"));
    private final SelenideElement emailInput = $(byId("userEmail"));
    private final SelenideElement currentAddressInput = $(byId("currentAddress"));
    private final SelenideElement permanentAddressInput = $(byId("permanentAddress"));
    private final SelenideElement submitButton = $(byId("submit"));

    private final SelenideElement outputName = $(byId("name"));
    private final SelenideElement outputEmail = $(byId("email"));
    private final SelenideElement outputCurrentAddress = $("#output #currentAddress");
    private final SelenideElement outputPermanentAddress = $("#output #permanentAddress");

    public TextBoxPage openPage() {
        open("/text-box");
        return this;
    }

    public TextBoxPage setFullName(String name) {
        fullNameInput.setValue(name);
        return this;
    }

    public TextBoxPage setEmail(String email) {
        emailInput.setValue(email);
        return this;
    }

    public TextBoxPage setCurrentAddress(String address) {
        currentAddressInput.setValue(address);
        return this;
    }

    public TextBoxPage setPermanentAddress(String address) {
        permanentAddressInput.setValue(address);
        return this;
    }

    public TextBoxPage submit() {
        submitButton.click();
        return this;
    }

    public String getOutputName() {
        return outputName.getText();
    }

    public String getOutputEmail() {
        return outputEmail.getText();
    }

    public String getOutputCurrentAddress() {
        return outputCurrentAddress.getText();
    }

    public String getOutputPermanentAddress() {
        return outputPermanentAddress.getText();
    }
}

