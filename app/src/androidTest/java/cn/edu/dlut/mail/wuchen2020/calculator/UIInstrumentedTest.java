package cn.edu.dlut.mail.wuchen2020.calculator;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 * <br/>
 * Used to test UI
 */
@RunWith(AndroidJUnit4.class)
public class UIInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("cn.edu.dlut.mail.wuchen2020.calculator", appContext.getPackageName());
    }
}