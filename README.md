# ParkingAppAndroid
Main branch

This repository demonstrates an Android application for managing parking functionality for users.
The User and Parking information is stored and retreived using Firebase.

Lajja Palaniswamy: 101343752
Users will be able to Sign In, Sign Out and Sign Up using their credentials(email and password).
Users can also update their profile information later.

![image](https://user-images.githubusercontent.com/73772928/122248628-d289a200-cee5-11eb-9ab6-f83c6130d72d.png)
![image](https://user-images.githubusercontent.com/73772928/122250418-46787a00-cee7-11eb-9b18-a64874f6bff7.png)

![image](https://user-images.githubusercontent.com/73772928/122248935-11b7f300-cee6-11eb-82f4-17ced3d30bd8.png)

![image](https://user-images.githubusercontent.com/73772928/122249019-2300ff80-cee6-11eb-90ef-4009d249db91.png)
![image](https://user-images.githubusercontent.com/73772928/122249139-37dd9300-cee6-11eb-8651-d64b5fd65f60.png)
![image](https://user-images.githubusercontent.com/73772928/122249258-504dad80-cee6-11eb-948e-2bb8d4a64cce.png)













Sandra Roy Aricatt: 101353026

Tab layout: Add Parking and View Parking functionalities are displayed over tab layout.
the menu tab displays:
settings - directs to the update User Profile Functionality
Sign out - directs to the login screen
currently logged in user(email)

Add Parking screen : Login screen is redirected to Add parking screen(which is a fragment and managed by tab layout)
Users can add a parking. 
The default carplate number will be fetched from Profile Collection.
Appropriate validations for carplatenumber, suitenumber,location is given. (hours doesnt need validationa as it is given in spinner)
current date will be loaded every time the screen is onResume, and after each parking is added- so that no parking with same timestamp is saved to firestore.
Users have the option to give current location or specific location they parked.

View Parking Screen : Login screen is redirected to view parking screen(which is the other fragment  managed by tab layout)
all the parking locations and hours will be descending sorted according to date (specific to the user) and will be listed - implemented using recyclerview.
When a parking is added,deleted or updated, the view parking screen is updated.

Update Parking: Each click on the item in view parking screen directs to update parking screen.
all the information related to that parking is loaded from the firestore.
Users are able to update any values according to the validations.
A button enables viewing parking location on map annotated with address.
pressing delete deletes the current parking and redirects to add parking screen.
