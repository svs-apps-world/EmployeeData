# EmployeeData

1. Build Tools & Versions Used
	Android Studio 3.4.2
	compileSdkVersion 28
    buildToolsVersion "29.0.2"
    minSdkVersion 16
    targetSdkVersion 28
    kotlin_version = '1.3.61'
	
2. Your Focus Areas.
	Followed the MVVM architecture pattern for structuring the app. Made sure the app works fine on rotation without making duplicate calls. Handled a slight variation of design for landscape mode. 
	Added menus to handle easy access of all formats of data.

3. Copied-in code or copied-in dependencies
	Took some help from stackoverflow for adding gradle dependencies - it was after a long time that I started a project from scratch.
	Downloaded icons from https://material.io/resources/icons/
	Got some design ideas from https://dribbble.com/shots/7241189-Employee-Management-Mobile
	Took some help for unit tests from the current project I am working.
	Placeholder image was taken from https://pixabay.com/images/search/avatar/

4. Tablet / phone focus
	Focus was on phone instead of tablet - but did test in tablet to check everything works fine. For phone both potrait mode and tablet mode are handled appropriately.
	Could have improved UI on tablet mode.

5. How long you spent on the project
	Setting up of project took a while. Adding right dependencies and matching of all the versions. 
	Did the development in 4 to 5 hours. 
	Apart from that added some extra features in an hour or 2 for opening up the image in DialogFragment and adding the filter logic for the list of employees. Unit test took another extra hour.

6. Anything else you want us to know
	Displaying all of the data of the employee in the List did not feel right so showed only highlights on the load. On clicking of the name of the Employee the card expands and shows up some extra data as well.

![](video-to-gif 13mb.gif)
