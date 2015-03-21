INTRODUCTION
------------
This app allows for the digital tracking of patient statuses for hospital workers.
A user (nurse or physician) logs into the app with their user information on the
"Emergency Room" screen. Following a successful log-in, patients can be accessed 
by entering their health card number in the provided text field. If necessary, 
nurses can add new patients providing the patient'sname, date of birth and health 
card number. This is done by using the "Add Patient" button on the "Health Card Search" 
screen. This option is not available for physicians. To log out, return to the
"Health Card Search" screen and select the "Logout" button.

Nurses can view a list of patient's in the emergency room, sorted by urgency. This is 
done by using the "List Urgent Patients" button on the "Health Card Search" screen,
which appears immediately following a successful login.


MAIN FEATURES
------------
When a valid patient's health card is entered into the search:

Nurses have the ability to:
-Check in the patient, which records their arrival time.
-Update a Patient's vitals (heart rate, temperature, blood pressure)
-Check a patient's record (containing past vitals and prescriptions)
-Record the date/time at which a patient sees a doctor. This also
acts as a "checkout" for the patient. This also resets the patient's
urgency

Physicians have the ability to:
-Check a patient's record (containing past vitals and prescriptions)
-Write a new prescription for the patient, which contains the name
and description of the prescription. This information will show up 
in the patient's record.

REQUIREMENTS
------------
This app requires an Android device running at least Android 4.0 (Ice Cream Sandwich)
firmware at minimum, with Android 4.3 (Jelly Bean) being the recommended version.


INSTALLATION
------------
Import the android project into Eclipse, and run it on an emulator. To make sure the 
app has correctly installed onto your emulator, view the "Android" section of 
the console after running the app and monitor the feedback given.


CONFIGURATION
-------------
This app has no configurable features. If at any time, a screen without a button is present, the 
android back button is to be used to return to the previous screen. 