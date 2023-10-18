package com.geekbrains.tests.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

const val TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setup() {
        uiDevice.pressHome()

        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    @Test
    fun test_MainActivityIsStarted() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        Assert.assertNotNull(editText)
    }

    @Test
    fun test_SearchIsPositive() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "UiAutomator"

        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))
        searchButton.click()
        uiDevice.wait(Until.gone(By.res(packageName)), TIMEOUT)

        val changedText= uiDevice.findObject(By.res(packageName, "totalCountTextView"))
        Assert.assertEquals(changedText.text.toString(), "Number of results: 668")
    }

    @Test
    fun test_SearchIsNegative() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = ""

        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))
        searchButton.click()
        uiDevice.wait(Until.gone(By.res(packageName)), TIMEOUT)

        val changedText= uiDevice.findObject(By.res(packageName, "totalCountTextView"))
        Assert.assertEquals(changedText.text.toString(), "")
    }

    @Test
    fun test_OpenDetailsScreen() {
        val toDetails: UiObject2 = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()

        val changedText = uiDevice.wait(Until.findObject(By.res(packageName, "totalCountTextView")), TIMEOUT)
        Assert.assertEquals(changedText.text, "Number of results: 0")
    }

    @Test
    fun test_TotalCount_OnDetailsScreen() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "UiAutomator"

        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))
        searchButton.click()
        uiDevice.wait(Until.gone(By.res(packageName)), TIMEOUT)

        val toDetails: UiObject2 = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()

        val changedText = uiDevice.wait(Until.findObject(By.res(packageName, "totalCountTextView")), TIMEOUT)
        Assert.assertEquals(changedText.text, "Number of results: 668")
    }
}