<h1 align="center">
  Ayuna
</h1>

<p align="center">
  <img src="https://images.unsplash.com/photo-1589525231707-f2de2428f59c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"/>
</p>

<p align="center">
  <a href="LICENSE"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"></a>
  <a href="https://github.com/utsmannn/ayuna/pulls"><img alt="Pull request" src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat"></a><br>
  <a href="https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/"><img alt="Spring Boot" src="https://img.shields.io/badge/Spring%20Boot-2.7.3-green"></a>
  <a href="https://github.com/Auties00/WhatsappWeb4j"><img alt="WhatsappWeb4j" src="https://img.shields.io/badge/WhatsappWeb4j-3.0RC18-white"></a>
  <a href="https://openjdk.java.net/projects/jdk/17/"><img alt="JDK 17" src="https://img.shields.io/badge/JDK-17%20Preview-orange"></a><br>
  <a href="https://twitter.com/utsmannn"><img alt="Twitter" src="https://img.shields.io/twitter/follow/utsmannn"></a>
  <a href="https://github.com/utsmannn"><img alt="Github" src="https://img.shields.io/github/followers/utsmannn?label=follow&style=social"></a>
  <h3 align="center">Whatsapp Bot Sticker Maker</h3>
</p>


---
<p align="center">
  <img src="https://i.ibb.co/0VQDQf5/ayuna-sticker.jpg" height="600"/>
</p>

---

# How to use
- For start please go to url
```
https://ayuna-sticker.herokuapp.com/start
```

- Send your image
- Ayuna will convert your image to sticker
- **Not yet** support for gif

# How to own my number
## Preparation
- Fork this project
- Setup Java 17 with enable preview
- Setup environment
  - Authentication QR code
    - `USERNAME` 
    - `PASSWORD`
  - Phone number
    - `PHONE_NUMBER`
## On services whatsapp
- Run Project
- Start qr generator in web browser
```
http://localhost:8080/qr
```
- Submit your authentication
## On user whatsapp
- Start endpoint for redirect to whatsapp share api
```
http://localhost:8080/start
```

# Limitation
- On localhost, sometimes socket not connected, so wait a second on your whatsapp screen after scanning QR
- Gif not supported, need ffmpeg executable for convert mp4 to gif (Whatsapp detect sent gif as mp4 format)

# Thanks to 
- [Auties00](https://github.com/Auties00) for https://github.com/Auties00/WhatsappWeb4j

---
```
Copyright 2020 Muhammad Utsman

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```