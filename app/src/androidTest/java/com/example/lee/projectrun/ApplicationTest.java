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

    @Test
    public void testCodeGeneration(){
        RegisterActivity registerActivity = new RegisterActivity();
        String holder = registerActivity.setCodeHolder();
        assertNotNull("A code is not generating", holder);
    }

    @Test
    public void testCodeGenerationStoring(){
        RegisterActivity registerActivity = new RegisterActivity();
        String holder = registerActivity.setCodeHolder();
        String storingCode = holder;
        assertNotNull("A code is not storing", storingCode);
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

//    @Test
//    public void testEmailSending(){
//        EmailSender emailSender = new EmailSender(this.getApplication(), "mahesh.bhudia@kcl.ac.uk", "test", "test message");
//        boolean holder = emailSender.didItSend();
//        assertTrue("The email failed to send", holder);
//    }

}