package com.example.flat;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.junit.Assert.*;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static org.hamcrest.Matchers.*;
import static androidx.test.espresso.Espresso.pressBack;

@RunWith(AndroidJUnit4.class)
public class RoomListActivityTest {

    static Room sTestRoom;
    static String sBaseEndPoint = "/";
    static ApiQueryBuilder sQueryBuilder;
    static ApiUtil sApiUtil;


    //    run before all tests
    @BeforeClass
    public static void classSetup(){
        sApiUtil = ApiUtil.getInstance();
    }

    @Rule
    public ActivityTestRule<RoomListActivity> mRoomListActivityActivityTestRule =
            new ActivityTestRule<>(RoomListActivity.class);

    @Test
    public void testRoomsActivityLaunched(){
        onView(withId(R.id.tv_head)).check(matches(withText("View over 10+ rentals waiting")));
//        final Room room = sApiUtil.getRoom("2");
    }

//    later
    @Test
    public void testSearchToolBar(){

    }

    @Test
    public void testAdvancedSearch(){
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
//        onView(withId(R.id.action_advanced_search)).perform(click());
        onView(withText("advanced_search")).perform(click());
        onView(withId(R.id.location_search_edit_text)).perform(typeText("Rongai"));
        onView(withId(R.id.room_type_search_edit_text)).perform(typeText("single"), closeSoftKeyboard());
        onView(withId(R.id.search_btn)).perform(click());
        onView(withId(R.id.location)).check(matches(withText("Rongai")));
    }
}