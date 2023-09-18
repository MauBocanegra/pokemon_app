# Pokemon app

## Design pattern

This app was created to comply with MVVM design pattern trying to follow the Clean-Architecture guidelines 

## Libraries

- Jetpack compose and MADSkills (UI was coded in compose for latest state of the art Android development)
    - Compose, navigation
- Firebase (user management, user account creation, user login)
- Hilt (Dependency injection)
- HiltViewmodels & AndroidX viewmodels (both are being used in different parts)
- Retrofit (instead of volley as we only wanted to do simple REST calls)
- Coil (for images)
- OkHttp & Json converter (rest api)

## Codebase

Codebase was uploaded to `qa` branch

## Screens

### Splash
Shows the splash with a zooming animation

<img src="https://github.com/MauBocanegra/pokemon_app/assets/12646098/d9205ead-49b2-47f6-a984-eb252dad64f7" height=300>

### Login/Signup
The login and signup screen allow the user to create a new account or login with an existing account
These account are managed by Firebase Mail authentication

<img height=300 src="https://github.com/MauBocanegra/pokemon_app/assets/12646098/22578b48-d18e-4a2a-aca6-5fa43cf36739">
<img height=300 src="https://github.com/MauBocanegra/pokemon_app/assets/12646098/3c6b72ba-3a18-4b6c-842d-8cc08a0a0812">
<img height=300 src="https://github.com/MauBocanegra/pokemon_app/assets/12646098/15083046-3761-400e-813d-70ed70b7927f">
<img height=300 src="https://github.com/MauBocanegra/pokemon_app/assets/12646098/c99be60c-9f0b-4bb8-95c1-42d670e919d6">

<img height=250 src="https://github.com/MauBocanegra/pokemon_app/assets/12646098/6b2c3ad7-e6a5-4ba9-aa78-5de74bbfb55f">

### Home
Home consists in a auto scrollable carousel of images showing 4 pokemon
Charizard, Pikachu, Squirtle, and Bulbasaur
From here the user can opt to Sign out 
If a Pokemon is clicked, it will take the user to the Pokemon's detail page

<img height=300 src="https://github.com/MauBocanegra/pokemon_app/assets/12646098/a8cd8954-eb5f-41e7-a57a-44576c70d50a">

### Pokemon Detail

While all data is loadin, a progress bar can be seen, once all data is ready to be presented a scrollable page will present the most relevant data from that Pokemon

<img height=300 src="https://github.com/MauBocanegra/pokemon_app/assets/12646098/cdb46d02-dfe1-480a-9044-cb568aa87193">
<img height=300 src="https://github.com/MauBocanegra/pokemon_app/assets/12646098/55bc6435-5051-43e5-87e7-0afc9695271b">
<img height=300 src="https://github.com/MauBocanegra/pokemon_app/assets/12646098/4222ced7-2e91-42b9-b7f2-7ec3b1537284">

## Compile and run

In order to compile and run you must create your own Firebase setup and download the `gooogle-services.json` and paste the content in the empty `google-services.json`
