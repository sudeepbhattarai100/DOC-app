package com.example.doctorappointmentsystem;

import com.example.doctorappointmentsystem.activity.bll.DoctorBLL;
import com.example.doctorappointmentsystem.activity.bll.LoginBLL;
import com.example.doctorappointmentsystem.activity.bll.SignupBLL;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test

    public void login() {
         LoginBLL loginBLL = new LoginBLL();
        boolean result = loginBLL.login("brother", "1234");
        assertEquals(true, result);
    }

@Test
    public void loginFail() {
        LoginBLL loginBLL = new LoginBLL();
        boolean result = loginBLL.login("sude", "asdfgh");
        assertEquals(false, result);
    }


    @Test
    public void signup() {
        SignupBLL signupBLL = new SignupBLL();
        boolean result = signupBLL.login("sudeepaeee@gmail.com", "sudeepaeee", "sudeep", "asdfgh", "bhattarai", "dhading");
        assertEquals(true, result);
    }


    @Test
    public void Doctorlogin() {
        DoctorBLL doctorBLL = new DoctorBLL();
        boolean result = doctorBLL.login("mohan", "asdfgh");
        assertEquals(true, result);
    }

    @Test
    public void DoctorloginFail() {
        DoctorBLL doctorBLL = new DoctorBLL();
        boolean result = doctorBLL.login("mohan1", "asdfgh");
        assertEquals(false, result);
    }




}
