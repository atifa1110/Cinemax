# 🎬 Cinemax

[![CircleCI](https://dl.circleci.com/status-badge/img/gh/atifa1110/Cinemax/tree/main.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/atifa1110/Cinemax/tree/main)

Cinemax is A movie catalog application built with Jetpack Compose using the The Movie Database (TMDb) API. The app provides users with a smooth and responsive experience for exploring movies and TV series across multiple categories.

## Preview
<img width="166" height="334" alt="Image" src="https://github.com/user-attachments/assets/c4b3d4f5-d5f0-4121-8fd2-364d18086573" />
<img width="167" height="330" alt="Image" src="https://github.com/user-attachments/assets/20fcb35c-b5fa-4b86-96d9-1964510f72cf" />
<img width="167" height="331" alt="Image" src="https://github.com/user-attachments/assets/9d5030c6-7aaf-4691-818c-cc7532262558" />
<img width="167" height="335" alt="Image" src="https://github.com/user-attachments/assets/9f58f776-5f55-4b24-aa56-47d1dbb8a603" />

## ✨ Features

- 🔐 **Login & Authentication** with Firebase
- 🎥 **Movie & TV Show Listing** using TMDB API
- 🔎 **Search functionality**
- 💾 **Wishlist support**
- 🕒 **Detail view with runtime**
- 🧪 **Unit testing** with MockK, Turbine, Robolectric
- 📱 **Jetpack Compose UI**
- ♻️ **Paging 3** for efficient list loading
- 💉 **Hilt Dependency Injection**
- 🔒 **Certificate Pinning** (for secure networking)
- ☁️ **Firebase Remote Config**
- 📊 **Code coverage** using Jacoco
- ⚙️ **CI/CD with CircleCI**: runs tests and builds APK automatically
- 🛡️ **Encrypted Room Database** for secure local data storage (Room + SQLCipher)

---

## 🧪 Testing & CI/CD

This project uses CircleCI to automatically test and build the project on every commit.

- ✅ Unit tests in `core` module
- 🔍 Coverage reports via Jacoco
- 📦 APK builds for release & debug variants

See the latest CI build here:  
🔗 [CircleCI Status](https://dl.circleci.com/status-badge/redirect/gh/atifa1110/Cinemax/tree/main)

---

## 📦 Modules

- `:app` — Main Android UI & navigation
- `:core` — Data layer (use cases, repository, models, network, etc.)

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/atifa1110/Cinemax.git
cd Cinemax
