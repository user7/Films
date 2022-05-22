package com.geekbrains.films

import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.films.view.filmlistfragment.FilmListAdapter
import com.geekbrains.films.view.filmlistfragment.FilmListFragment
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FilmListFragmentEspressoTest {

    private lateinit var scenario: FragmentScenario<FilmListFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()
    }

    @After
    fun close() {
        scenario.close()
    }

    @Test
    fun fragment_testSearch() {
        onView(withId(R.id.edit_search)).perform(ViewActions.click())
        onView(withId(R.id.edit_search))
            .perform(ViewActions.replaceText("roller blade seven"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.button_search)).perform(ViewActions.click())
        onView(isRoot()).perform(delay())
        onView(withId(R.id.recyclerview_films))
            .perform(
                RecyclerViewActions.scrollTo<FilmListAdapter.ViewHolder>(
                    hasDescendant(withText("The Roller Blade Seven"))
                )
            )
    }

    private fun delay(): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $2 seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(2000)
            }
        }
    }
}