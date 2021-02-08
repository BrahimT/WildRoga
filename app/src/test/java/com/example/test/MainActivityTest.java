package com.example.test;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;




public class MainActivityTest extends TestCase {

    //https://www.youtube.com/watch?v=_TR6QcRozAg consulted

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityTestRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    private ActivityScenario<MainActivity> mainActivity = null;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityTestRule.getScenario();
    }

    @After
    public void tearDown() throws Exception {
    }

}
