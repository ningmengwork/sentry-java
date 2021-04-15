package io.sentry.samples.android

import android.content.Context
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiSelector
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.appcenter.espresso.Factory
import com.microsoft.appcenter.espresso.ReportHelper
import org.junit.After
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
        = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val reportHelper: ReportHelper = Factory.getReportHelper()

    @After
    fun tearDown() {
        reportHelper.label("tearDown")
    }

    // Tests are run in alphabetical order...

    // We crash in a service to test if an envelope was created.
    @Test
    fun a_initSdkAndCrash() {
        reportHelper.label("initSdkAndCrash")
        onView(withId(R.id.native_crash)).perform(scrollTo(), click())
        Thread.sleep(5000)

        // Close either app or android dialog


        val mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val uiObject = mDevice.findObject(UiSelector().text("OK"))
        if (uiObject.exists()) {
            uiObject.click()
        } else {
            mDevice.pressBack()
        }
    }

    // Check if envelope file is here.
    @Test
    fun b_checkIfFileExists() {
        onView(withId(R.id.button_copy_file)).perform(click())
        onView(withId(R.id.text_view_copy_file))
            .check(matches(withText("SUCCESS")))

        reportHelper.label("checkIfFileExists")
    }


}
