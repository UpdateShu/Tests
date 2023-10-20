package com.geekbrains.tests.automator

import android.content.Context
import androidx.test.core.app.ApplicationProvider

import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class OpenOtherAppsTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Test
    fun test_OpenContacts() {
        uiDevice.pressHome()
        uiDevice.swipe(500, 1500, 500, 0, 5)

        val appViews = UiScrollable(UiSelector().scrollable(true))
        val calendarApp = appViews
            .getChildByText(UiSelector().className(TextView::class.java.name), "Contacts"
            )
        calendarApp.clickAndWaitForNewWindow()
        val settingsValidation =
            uiDevice.findObject(UiSelector().packageName("com.android.contacts"))
        Assert.assertTrue(settingsValidation.exists())
    }

    @Test
    fun test_OpenGallery() {
        uiDevice.pressHome()
        uiDevice.swipe(400, 1000, 300, 200, 5)

        val appViews = UiScrollable(UiSelector().scrollable(true))
        val galleryApp = appViews
            .getChildByText(UiSelector().className(TextView::class.java.name), "Gallery"
            )
        galleryApp.clickAndWaitForNewWindow()
        val settingsValidation =
            uiDevice.findObject(UiSelector().packageName("com.android.gallery3d"))
        Assert.assertTrue(settingsValidation.exists())
    }

    @Test
    fun test_OpenYoutube() {
        uiDevice.pressHome()
        uiDevice.swipe(300, 1200, 100, 100, 5)

        val appViews = UiScrollable(UiSelector().scrollable(true))
        val youtubeApp = appViews
            .getChildByText(UiSelector().className(TextView::class.java.name), "Youtube"
            )
        youtubeApp.clickAndWaitForNewWindow()
        val settingsValidation =
            uiDevice.findObject(UiSelector().packageName("com.google.android.youtube"))
        Assert.assertTrue(settingsValidation.exists())
    }
}
