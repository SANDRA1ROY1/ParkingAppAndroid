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
![image](https://user-images.githubusercontent.com/73772928/122251270-f9e16e80-cee7-11eb-9f6f-b1dcb578cf9a.png)
![image](https://user-images.githubusercontent.com/73772928/122251755-6ceae500-cee8-11eb-804c-e5aaa685bcad.png)
![image](https://user-images.githubusercontent.com/73772928/122265546-8004b180-cef6-11eb-8b44-95a347307e07.png)


















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
<img width="460" alt="Add Parking" src="https://user-images.githubusercontent.com/83788153/122302218-cc88c680-cecf-11eb-8cb1-288385b4de42.png">
<img width="460" alt="address_input" src="https://user-images.githubusercontent.com/83788153/122302276-df030000-cecf-11eb-888e-3a0a4e6632e6.png">
<img width="460" alt="validation_addParking" src="https://user-images.githubusercontent.com/83788153/122302308-ec1fef00-cecf-11eb-8756-76686c60f349.png">


View Parking Screen : Login screen is redirected to view parking screen(which is the other fragment  managed by tab layout)
all the parking locations and hours will be descending sorted according to date (specific to the user) and will be listed - implemented using recyclerview.
When a parking is added,deleted or updated, the view parking screen is updated.
<img width="460" alt="ViewParking" src="https://user-images.githubusercontent.com/83788153/122302332-f641ed80-cecf-11eb-82ae-e114643b69b2.png">
<img width="460" alt="updateParking" src="https://user-images.githubusercontent.com/83788153/122302362-fe9a2880-cecf-11eb-8703-c8e9a8b017e6.png">
<img width="460" alt="displayMap" src="https://user-images.githubusercontent.com/83788153/122302384-078afa00-ced0-11eb-981a-e8b690ff9783.png">




Update Parking: Each click on the item in view parking screen directs to update parking screen.
all the information related to that parking is loaded from the firestore.
Users are able to update any values according to the validations.
A button enables viewing parking location on map annotated with address.
pressing delete deletes the current parking and redirects to add parking screen.
