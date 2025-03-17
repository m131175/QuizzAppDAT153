Test Case Documentation

This section documents the primary test cases for QuizzAppDat153. Each test case includes a description, expected result, the implementing test method, and the actual result.

1. MainActivityTest

testGalleryButtonOpensGalleryActivity
Description:
This test verifies that clicking the "Gallery" button in the MainActivity correctly opens the GalleryActivity.

Expected Result:
The GalleryActivity should be displayed after clicking the button.

Test Method:
testGalleryButtonOpensGalleryActivity() (in MainActivityTest.java)

Actual Result: Passed


2. GalleryActivityTest

testAddingImageIncreasesListSize
Description:
This test verifies that adding a new image entry increases the total number of images displayed in the gallery.

Steps:
Open GalleryActivity.
Click on the "Add Entry" button.
Enter a name for the image.
Stub the image picker intent to select an image.
Click the "Add Entry" button.
Verify that the image appears in the gallery.

Expected Result:
The number of images in the gallery should increase by 1 after adding an image.

Test Method:
testAddingImageIncreasesListSize() (in GalleryActivityTest.java)

Actual Result: Passed

3. QuizActivityTest

testScoreUpdatesCorrectly
Description:
This test ensures that the quiz score updates correctly when selecting correct and incorrect answers.

Expected Result:
The initial score should be 0/0.
Selecting a correct answer should increase both the score and attempt count.
Selecting an incorrect answer should only increase the attempt count.

Test Method:
testScoreUpdatesCorrectly() (in QuizActivityTest.java)

Actual Result: Failed

Issue: The correctAnswer variable does not reset correctly when loading a new question.
Fix: Ensure correctAnswer is reassigned when loadNextQuestion() runs.
