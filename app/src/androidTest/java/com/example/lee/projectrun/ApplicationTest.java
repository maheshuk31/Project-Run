package com.example.lee.projectrun;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.Test;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    /*----------------------------------MainActivity Class Tests-----------------------------------*/
    @Test
    public void testLoginKingsIDScenario1BeginningWithAnyOtherLetterButK(){
        MainActivity mainActivity = new MainActivity();
        boolean holder = mainActivity.getIsValidKingsID("s1234567");
        assertFalse("The King's ID login allows a person to to begin with another letter rather then only k", holder);
    }

    @Test
    public void testLoginKingsIDScenario2BeginningWithSmallK(){
        MainActivity mainActivity = new MainActivity();
        boolean holder = mainActivity.getIsValidKingsID("k1234567");
        assertTrue("The King's ID login does not allow small case k's", holder);
    }

    @Test
    public void testLoginKingsIDScenario3BeginningWithBigK(){
        MainActivity mainActivity = new MainActivity();
        boolean holder = mainActivity.getIsValidKingsID("K1234567");
        assertTrue("The King's ID login does not allow capital K's", holder);
    }

    @Test
    public void testLoginKingsIDScenario4LettersAfterK(){
        MainActivity mainActivity = new MainActivity();
        boolean holder = mainActivity.getIsValidKingsID("K1234j67");
        assertFalse("The King's ID login allows letters after the K", holder);
    }

    @Test
    public void testLoginKingsIDNumberOfCharactersLessThan8(){
        MainActivity mainActivity = new MainActivity();
        boolean holder = mainActivity.getIsValidKingsID("K123456");
        assertFalse("The King's ID login allows letters then 8 characters", holder);
    }

    @Test
    public void testLoginKingsIDNumberOfCharactersGreaterThan8() {
        MainActivity mainActivity = new MainActivity();
        boolean holder = mainActivity.getIsValidKingsID("K12345678");
        assertFalse("The King's ID login allows more then 8 characters", holder);
    }

    /*----------------------------------MainActivity Class Ends------------------------------------*/

    /*--------------------------------RegisterActivity Class Tests---------------------------------*/
    @Test
    public void testCodeGeneration(){
        RegisterActivity registerActivity = new RegisterActivity();
        String holder = registerActivity.setCodeHolder(4);
        assertNotNull("Random code is not generating", holder);
    }

    @Test
    public void testCodeGenerationStoring(){
        RegisterActivity registerActivity = new RegisterActivity();
        String holder = registerActivity.setCodeHolder(4);
        String storingCode = holder;
        assertNotNull("Random code is not storing", storingCode);
    }

    @Test
    public void testKingsIDScenario1BeginningWithAnyOtherLetterButK(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidKingsID("s1234567");
        assertFalse("The King's ID allows a person to to begin with another letter rather then only k", holder);
    }

    @Test
    public void testKingsIDScenario2BeginningWithSmallK(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidKingsID("k1234567");
        assertTrue("The King's ID does not allow small case k's", holder);
    }

    @Test
    public void testKingsIDScenario3BeginningWithBigK(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidKingsID("K1234567");
        assertTrue("The King's ID does not allow capital K's", holder);
    }

    @Test
    public void testKingsIDScenario4LettersAfterK(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidKingsID("K1234j67");
        assertFalse("The King's ID allows letters after the K", holder);
    }

    @Test
    public void testKingsIDNumberOfCharactersLessThan8(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidKingsID("K123456");
        assertFalse("The King's ID allows letters then 8 characters", holder);
    }

    @Test
    public void testKingsIDNumberOfCharactersGreaterThan8(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidKingsID("K12345678");
        assertFalse("The King's ID allows more then 8 characters", holder);
    }

    @Test
    public void testEmailRegexScenario1Domain(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidEmail("anyone@kcl.ac.uk");
        assertTrue("The email method does not have the correct regular expression for the KCL domain",
                holder);
    }

    @Test
    public void testEmailRegexScenario2NameVersion(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidEmail("john.doe@kcl.ac.uk");
        assertTrue("The email method does not have the correct regular expression for the a persons name",
                holder);
    }

    @Test
    public void testEmailRegexScenario3KIDVersion(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidEmail("k1234567@kcl.ac.uk");
        assertTrue("The email method does not have the correct regular expression for the K ID number",
                holder);
    }

    @Test
    public void testPasswordRegexScenario1NumberOfCharacters(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidPassword("test1");
        assertFalse("The password allows less then 6 characters", holder);
    }

    @Test
    public void testPasswordRegexScenario2HavingANumber(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidPassword("testing");
        assertFalse("The password doesn't require a number", holder);
    }

    @Test
    public void testPasswordRegexScenario3VariousLettersAndNumbers(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidPassword("TestinG123");
        assertTrue("The password doesn't allow a assortment of letters and numbers", holder);
    }

    /*---------------------------------RegisterActivity Class Ends---------------------------------*/

    /*-----------------------------ForgotPasswordActivity Class Tests------------------------------*/
    @Test
    public void testForgetFieldKingsIDScenario1BeginningWithAnyOtherLetterButK(){
        ForgotPasswordActivity forgotPasswordActivity = new ForgotPasswordActivity();
        boolean holder = forgotPasswordActivity.getIsValidForgotString("s1234567");
        assertFalse("The King's ID allows a person to to begin with another letter rather then only k", holder);
    }

    @Test
    public void testForgetFieldKingsIDScenario2BeginningWithSmallK(){
        ForgotPasswordActivity forgotPasswordActivity = new ForgotPasswordActivity();
        boolean holder = forgotPasswordActivity.getIsValidForgotString("k1234567");
        assertTrue("The King's ID does not allow small case k's", holder);
    }

    @Test
    public void testForgetFieldKingsIDScenario3BeginningWithBigK(){
        ForgotPasswordActivity forgotPasswordActivity = new ForgotPasswordActivity();
        boolean holder = forgotPasswordActivity.getIsValidForgotString("K1234567");
        assertTrue("The King's ID does not allow capital K's", holder);
    }

    @Test
    public void testForgetFieldKingsIDScenario4LettersAfterK(){
        ForgotPasswordActivity forgotPasswordActivity = new ForgotPasswordActivity();
        boolean holder = forgotPasswordActivity.getIsValidForgotString("K1234j67");
        assertFalse("The King's ID allows letters after the K", holder);
    }

    @Test
    public void testForgetFieldKingsIDNumberOfCharactersLessThan8(){
        ForgotPasswordActivity forgotPasswordActivity = new ForgotPasswordActivity();
        boolean holder = forgotPasswordActivity.getIsValidForgotString("K123456");
        assertFalse("The King's ID allows letters then 8 characters", holder);
    }

    @Test
    public void testForgetFieldKingsIDNumberOfCharactersGreaterThan8(){
        ForgotPasswordActivity forgotPasswordActivity = new ForgotPasswordActivity();
        boolean holder = forgotPasswordActivity.getIsValidForgotString("K12345678");
        assertFalse("The King's ID allows more then 8 characters", holder);
    }

    @Test
    public void testForgetFieldEmailRegexScenario1Domain(){
        ForgotPasswordActivity forgotPasswordActivity = new ForgotPasswordActivity();
        boolean holder = forgotPasswordActivity.getIsValidForgotString("anyone@kcl.ac.uk");
        assertTrue("The email method does not have the correct regular expression for the KCL domain",
                holder);
    }

    @Test
    public void testForgetFieldEmailRegexScenario2NameVersion(){
        ForgotPasswordActivity forgotPasswordActivity = new ForgotPasswordActivity();
        boolean holder = forgotPasswordActivity.getIsValidForgotString("john.doe@kcl.ac.uk");
        assertTrue("The email method does not have the correct regular expression for the a persons name",
                holder);
    }

    @Test
    public void testForgetFieldEmailRegexScenario3KIDVersion(){
        ForgotPasswordActivity forgotPasswordActivity = new ForgotPasswordActivity();
        boolean holder = forgotPasswordActivity.getIsValidForgotString("k1234567@kcl.ac.uk");
        assertTrue("The email method does not have the correct regular expression for the K ID number",
                holder);
    }

    /*-----------------------------ForgotPasswordActivity Class Ends-------------------------------*/
//    @Test
//    public void testEmailSending(){
//        EmailSender emailSender = new EmailSender(this.getApplication(), "mahesh.bhudia@kcl.ac.uk", "test", "test message");
//        boolean holder = emailSender.didItSend();
//        assertTrue("The email failed to send", holder);
//    }

}
