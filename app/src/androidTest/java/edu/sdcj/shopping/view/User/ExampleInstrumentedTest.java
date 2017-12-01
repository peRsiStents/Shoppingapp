package edu.sdcj.shopping.view.User;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import edu.sdcj.shopping.dao.UserDao;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        UserDao userDao=new UserDao(appContext);
       // userDao.insert("scott","tiger","男","24","22222","jinan");
        //userDao.insert("smith","123456","男","24","22222","jinan");
        //assertEquals("edu.sdcj.shopping.view.User", appContext.getPackageName());
        assertEquals(true,userDao.queryLogin("scott","tiger"));
    }
}
