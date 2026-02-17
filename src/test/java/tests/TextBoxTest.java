package tests;

import base.BaseTest;
import commons.TextBoxSteps;
import checks.Checks;
import org.testng.annotations.Test;
import static testdata.DataGenerator.*;

public class TextBoxTest extends BaseTest {

    private final TextBoxSteps textBoxSteps = new TextBoxSteps();


    @Test(description = "Проверяем, что форма Text Box отправляется и данные отображаются корректно")
    public void testTextBoxFormSubmission() {
        String name = generateFullName();
        String email = generateEmail();
        String currentAddress = generateCurrentAddress();
        String permanentAddress = generatePermanentAddress();

        textBoxSteps
                .openTextBoxPage()
                .fillForm(name, email, currentAddress, permanentAddress)
                .submitForm();
        Checks.checkThat(
                textBoxSteps.verifySubmittedData(name, email, currentAddress, permanentAddress),
                "Данные формы не совпадают с отображаемыми");
    }
}

