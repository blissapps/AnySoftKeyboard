package com.anysoftkeyboard;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import android.os.SystemClock;
import android.view.inputmethod.EditorInfo;

import com.anysoftkeyboard.api.KeyCodes;
import com.menny.android.anysoftkeyboard.AskGradleTestRunner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.util.ServiceController;

@RunWith(AskGradleTestRunner.class)
public class AnySoftKeyboardClipboardTest {

    private TestableAnySoftKeyboard mAnySoftKeyboardUnderTest;

    @Before
    public void setUp() throws Exception {
        ServiceController<TestableAnySoftKeyboard> anySoftKeyboardController = Robolectric.buildService(TestableAnySoftKeyboard.class);
        mAnySoftKeyboardUnderTest = anySoftKeyboardController.attach().create().get();

        final EditorInfo editorInfo = TestableAnySoftKeyboard.createEditorInfoTextWithSuggestions();
        mAnySoftKeyboardUnderTest.setInputView(mAnySoftKeyboardUnderTest.onCreateInputView());
        mAnySoftKeyboardUnderTest.onStartInput(editorInfo, false);
        mAnySoftKeyboardUnderTest.onStartInputView(editorInfo, false);

        Robolectric.flushBackgroundThreadScheduler();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSelectsAllText() {
        TestInputConnection inputConnection = (TestInputConnection) mAnySoftKeyboardUnderTest.getCurrentInputConnection();
        final String expectedText = "testing something very long";
        inputConnection.commitText(expectedText, 1);

        Assert.assertEquals("", inputConnection.getSelectedText(0).toString());
        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.CLIPBOARD_SELECT_ALL);
        Assert.assertEquals(expectedText, inputConnection.getSelectedText(0).toString());
    }

    @Test
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void testClipboardCopy() {
        TestInputConnection inputConnection = (TestInputConnection) mAnySoftKeyboardUnderTest.getCurrentInputConnection();
        final String expectedText = "testing something very long";
        inputConnection.commitText(expectedText, 1);
        inputConnection.setSelection("testing ".length(), "testing something".length());
        Assert.assertEquals("something", inputConnection.getSelectedText(0).toString());

        ClipboardManager clipboardManager = (ClipboardManager) RuntimeEnvironment.application.getSystemService(Service.CLIPBOARD_SERVICE);
        Assert.assertNull(clipboardManager.getPrimaryClip());

        Assert.assertEquals(expectedText, inputConnection.getCurrentTextInInputConnection());
        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.CLIPBOARD_COPY);

        //text stays the same
        Assert.assertEquals(expectedText, inputConnection.getCurrentTextInInputConnection());
        //and clipboard has the copied text
        Assert.assertEquals(1, clipboardManager.getPrimaryClip().getItemCount());
        Assert.assertEquals("something", clipboardManager.getPrimaryClip().getItemAt(0).getText().toString());
    }

    @Test
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void testClipboardCut() {
        TestInputConnection inputConnection = (TestInputConnection) mAnySoftKeyboardUnderTest.getCurrentInputConnection();
        final String originalText = "testing something very long";
        final String textToCut = "something";
        final String expectedText = "testing  very long";
        inputConnection.commitText(originalText, 1);
        inputConnection.setSelection("testing ".length(), "testing something".length());
        Assert.assertEquals(textToCut, inputConnection.getSelectedText(0).toString());

        ClipboardManager clipboardManager = (ClipboardManager) RuntimeEnvironment.application.getSystemService(Service.CLIPBOARD_SERVICE);

        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.CLIPBOARD_CUT);

        //text without "something"
        Assert.assertEquals(expectedText, inputConnection.getCurrentTextInInputConnection());
        //and clipboard has the copied text
        Assert.assertEquals(1, clipboardManager.getPrimaryClip().getItemCount());
        Assert.assertEquals("something", clipboardManager.getPrimaryClip().getItemAt(0).getText().toString());
    }

    @Test
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void testClipboardPaste() {
        TestInputConnection inputConnection = (TestInputConnection) mAnySoftKeyboardUnderTest.getCurrentInputConnection();

        ClipboardManager clipboardManager = (ClipboardManager) RuntimeEnvironment.application.getSystemService(Service.CLIPBOARD_SERVICE);
        final String expectedText = "some text";
        clipboardManager.setPrimaryClip(new ClipData("ask", new String[]{"text"}, new ClipData.Item(expectedText)));

        Assert.assertEquals("", inputConnection.getSelectedText(0).toString());

        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.CLIPBOARD_PASTE);
        Assert.assertEquals(expectedText, inputConnection.getCurrentTextInInputConnection());
    }

    @Test
    public void testSelectionExpending_AtEndOfInput() {
        TestInputConnection inputConnection = (TestInputConnection) mAnySoftKeyboardUnderTest.getCurrentInputConnection();
        inputConnection.commitText("some text in the input connection", 1);

        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.CLIPBOARD_SELECT);
        Assert.assertEquals("", inputConnection.getSelectedText(0).toString());

        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.ARROW_LEFT);
        Assert.assertEquals("n", inputConnection.getSelectedText(0).toString());
        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.ARROW_LEFT);
        Assert.assertEquals("on", inputConnection.getSelectedText(0).toString());
        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.ARROW_RIGHT);
        Assert.assertEquals("on", inputConnection.getSelectedText(0).toString());
    }

    @Test
    public void testSelectionExpending_AtMiddleOfInput() {
        TestInputConnection inputConnection = (TestInputConnection) mAnySoftKeyboardUnderTest.getCurrentInputConnection();
        inputConnection.commitText("some text in the input connection", 1);
        inputConnection.setSelection("some ".length(), "some ".length());

        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.CLIPBOARD_SELECT);
        Assert.assertEquals("", inputConnection.getSelectedText(0).toString());

        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.ARROW_RIGHT);
        Assert.assertEquals("t", inputConnection.getSelectedText(0).toString());
        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.ARROW_RIGHT);
        Assert.assertEquals("te", inputConnection.getSelectedText(0).toString());
        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.ARROW_LEFT);
        Assert.assertEquals(" te", inputConnection.getSelectedText(0).toString());
    }

    @Test
    public void testSelectionExpendingCancel() {
        TestInputConnection inputConnection = (TestInputConnection) mAnySoftKeyboardUnderTest.getCurrentInputConnection();
        inputConnection.commitText("some text in the input connection", 1);
        inputConnection.setSelection("some ".length(), "some ".length());

        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.CLIPBOARD_SELECT);
        Assert.assertEquals("", inputConnection.getSelectedText(0).toString());

        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.ARROW_RIGHT);
        Assert.assertEquals("t", inputConnection.getSelectedText(0).toString());
        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.ARROW_RIGHT);
        Assert.assertEquals("te", inputConnection.getSelectedText(0).toString());

        mAnySoftKeyboardUnderTest.simulateKeyPress('k');
        //selection was replaced with space
        Assert.assertEquals("", inputConnection.getSelectedText(0).toString());
        Assert.assertEquals("some kxt in the input connection", inputConnection.getCurrentTextInInputConnection());
        Assert.assertEquals("some k".length(), inputConnection.getCurrentStartPosition());
        //and we are no longer is select state
        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.ARROW_RIGHT);
        Assert.assertEquals("", inputConnection.getSelectedText(0).toString());
    }

    @Test
    public void testSelectionExpendingWithAlreadySelectedText() {
        TestInputConnection inputConnection = (TestInputConnection) mAnySoftKeyboardUnderTest.getCurrentInputConnection();
        inputConnection.commitText("some text in the input connection", 1);
        inputConnection.setSelection("some ".length(), "some text".length());
        //we already have selection set
        Assert.assertEquals("text", inputConnection.getSelectedText(0).toString());

        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.CLIPBOARD_SELECT);
        Assert.assertEquals("text", inputConnection.getSelectedText(0).toString());

        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.ARROW_RIGHT);
        Assert.assertEquals("text ", inputConnection.getSelectedText(0).toString());
        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.ARROW_RIGHT);
        Assert.assertEquals("text i", inputConnection.getSelectedText(0).toString());
        mAnySoftKeyboardUnderTest.simulateKeyPress(KeyCodes.ARROW_LEFT);
        Assert.assertEquals(" text i", inputConnection.getSelectedText(0).toString());
    }
}