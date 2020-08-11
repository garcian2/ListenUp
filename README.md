# ListenUp- Exposing Android's Microphone Vulnerabilities

This project serves to demonstrate vulnerabilites on Android API 29 and subsequent builds until patched.
Is it possible to manipulate a user’s microphone data like many people think is happening on Instagram and Facebook?
Yes. This project shows just one implementation in the form of a live proof of concept application to understand how something like this
actually works.

#Specifications
Microphone Permissions are the only user required piece.
    -Nearly all social media platforms ask for Microphone permission.
Uses built in Active Speech Recognizer from Google
    -WiFi & internet connection not required
    -Can run as background task
Uses Facebook Ad API- “Events Logger”
    -Discreetly sends user’s speech to advertising API
Uses Facebook Native Ads implementation
    -Serves user catered advertisements based on their personal advertising ID.

#Demo
https://youtu.be/ovHWN2t2rTI
