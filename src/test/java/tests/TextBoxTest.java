package tests;

import base.BaseTest;
import commons.TextBoxSteps;
import checks.Checks;
import org.testng.annotations.Test;
import testdata.DataGenerator;

public class TextBoxTest extends BaseTest {

    private final TextBoxSteps steps = new TextBoxSteps();
    

    @Test(description = "Проверяем, что форма Text Box отправляется и данные отображаются корректно")
    public void testTextBoxFormSubmission() {
        String name = DataGenerator.generateFullName();
        String email = DataGenerator.generateEmail();
        String currentAddress = DataGenerator.generateCurrentAddress();
        String permanentAddress = DataGenerator.generatePermanentAddress();

        steps
                .openTextBoxPage()
                .fillForm(name, email, currentAddress, permanentAddress)
                .submitForm();
        Checks.checkThat(
                steps.verifySubmittedData(name, email, currentAddress, permanentAddress),
                "Данные формы не совпадают с отображаемыми");
    }
}

