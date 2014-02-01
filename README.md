Crash Report
============

Crash Report is andorid project designed to facilitate the get crash report of the application which run across all versions of Android. It works as your app may crash on any Android OS version so When your application crash then it will give a list of application to the user to send that crash logs to the developer email.

How to use this:-
-----------------------

Copy the `CrashActivity.java`, `ExceptionHandler.java`, `Helper.java` in your android project. Now write `Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));` before setContentView in all the activity where your application can crash and finally make entry of Activity `CrashActivity.java` and add the permission to write the crash logs in the external storage in the `AndroidManifest.xml` 

You can change the developer email in the `CrashActivity.java` on which you want to receive the crash logs.


Developed By
-------------------

    Ajay Saini - ajaysaini.sgvu@gmail.com
    
    

