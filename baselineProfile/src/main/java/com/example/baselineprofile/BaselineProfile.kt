package com.example.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfile {

    @get:Rule
    val baselineRule = BaselineProfileRule()

    @Test
    fun generate() = baselineRule.collect(
        packageName = "com.example.profilecardapp"
    ) {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        device.pressHome()

        startActivityAndWait()

        device.waitForIdle()

        device.findObject(By.text("Open Profile"))?.click()

        device.waitForIdle()

        val content = device.findObject(By.res("android:id/content"))
        content?.setGestureMargin(device.displayWidth / 10)
        content?.swipe(Direction.UP, 0.7f)

        device.pressBack()

        device.findObject(By.text("Load Users"))?.click()

        device.waitForIdle()
    }
}
