# Fab application
Test application with draggable fab button (with snapping to edge ability)

**[Debug Apk](https://github.com/kosyakoff/fab/blob/main/fab-v1.apk)** - APK for testing

###Architecture:
The application uses a very basic type of MVVM for the representation layer (global activity viewmodel which is mutually used by all child fragments). 

Also, it has some utils classes to mitigate common boilerplate code, connected with Android views and fragments lifecycle observing issues.
For FAB button we used a modified version of a standard FAB

###Additional components: 
Hilt, Jepack Navigation, ViewBindingPropertyDelegate.
