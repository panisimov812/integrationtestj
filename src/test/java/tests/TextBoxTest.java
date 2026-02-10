package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import steps.TextBoxSteps;

import static org.testng.Assert.assertTrue;

public class TextBoxTest extends BaseTest {

    private final TextBoxSteps steps = new TextBoxSteps();

    @Test(description = "Проверяем, что форма Text Box отправляется и данные отображаются корректно")
    public void testTextBoxFormSubmission() {
        String name = "John Doe";
        String email = "john.doe@example.com";
        String currentAddress = "123 Current St, City";
        String permanentAddress = "456 Permanent Ave, Town";

        steps
                .openTextBoxPage()
                .fillForm(name, email, currentAddress, permanentAddress)
                .submitForm();
        assertTrue(
                steps.verifySubmittedData(name, email, currentAddress, permanentAddress),
                "Данные формы не совпадают с отображаемыми");
    }
}

