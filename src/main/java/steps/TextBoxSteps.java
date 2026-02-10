package steps;

import io.qameta.allure.Step;
import pages.TextBoxPage;

public class TextBoxSteps {

    private final TextBoxPage textBoxPage = new TextBoxPage();

    @Step("Открываем страницу Text Box")
    public TextBoxSteps openTextBoxPage() {
        textBoxPage.openPage();
        return this;
    }

    @Step("Заполняем форму Text Box с именем='{name}', email='{email}', текущим адресом='{currentAddress}', постоянным адресом='{permanentAddress}'")
    public TextBoxSteps fillForm(String name, String email, String currentAddress, String permanentAddress) {
        textBoxPage
                .setFullName(name)
                .setEmail(email)
                .setCurrentAddress(currentAddress)
                .setPermanentAddress(permanentAddress);
        return this;
    }

    @Step("Отправляем форму Text Box")
    public TextBoxSteps submitForm() {
        textBoxPage.submit();
        return this;
    }

    @Step("Проверяем, что данные отображаются корректно")
    public boolean verifySubmittedData(String name, String email, String currentAddress, String permanentAddress) {
        return textBoxPage.getOutputName().contains(name)
                && textBoxPage.getOutputEmail().contains(email)
                && textBoxPage.getOutputCurrentAddress().contains(currentAddress)
                && textBoxPage.getOutputPermanentAddress().contains(permanentAddress);
    }
}

