# ListenUp- Exposing Android's Microphone Vulnerabilities

Is it possible to manipulate a user’s microphone data like many people think is happening on Instagram and Facebook?

Yes, this project shows just one implementation in the form of a live proof of concept application to understand how something like this
actually works with built in functionalities, and can even work without wifi connectivity.

### Specifications

Microphone Permissions are the only user required piece.
- Nearly all social media platforms ask for Microphone permission.

Uses built in Active Speech Recognizer from Google
- WiFi & internet connection not required
- Can run as background task

Uses Facebook Ad API- “Events Logger”
- Discreetly sends user’s speech to advertising API

Uses Facebook Native Ads implementation
- Serves user catered advertisements based on their personal advertising ID.

### Demo
A video demonstration can be found at this link -> https://youtu.be/ovHWN2t2rTI
<img src="https://2.bp.blogspot.com/-XjMoXsCYrm8/XRUU_H2Np5I/AAAAAAAAFyg/FQhMzNoiOaA936HQcIcVkNwwcfMbCE0CQCLcBGAs/s1200/instagram-facebook-not-listening.jpg">

