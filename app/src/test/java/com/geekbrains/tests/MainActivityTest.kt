package com.geekbrains.tests

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.search.MainActivity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {
    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var context: Context

    @Before
    fun before() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    @After
    fun after() {
        scenario.close()
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity { mainActivity ->
            Assert.assertNotNull(mainActivity)
        }
    }

    @Test
    fun activity_IsResumed() {
        Assert.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activityEditTextView_NotNull() {
        scenario.onActivity { mainActivity ->
            val searchEditText = mainActivity.findViewById<EditText>(R.id.searchEditText)
            Assert.assertNotNull(searchEditText)
        }
    }

    @Test
    fun activityButton_AreVisible() {
        scenario.onActivity { mainActivity ->
            val toDetailsActivityButton =
                mainActivity.findViewById<Button>(R.id.toDetailsActivityButton)
            Assert.assertEquals(View.VISIBLE, toDetailsActivityButton.visibility)
        }
    }
    @Test
    fun activityEditText_HasText(){
        scenario.onActivity {
            val editText = it.findViewById<EditText>(R.id.searchEditText)
            editText.setText("search", TextView.BufferType.EDITABLE)
            Assert.assertNotNull(editText.text)
            Assert.assertEquals("search", editText.text.toString())
        }
    }
}