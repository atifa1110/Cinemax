# 🎬 Cinemax

[![CircleCI](https://dl.circleci.com/status-badge/img/gh/atifa1110/Cinemax/tree/main.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/atifa1110/Cinemax/tree/main)

Cinemax is a modern Android movie browsing application built with **Jetpack Compose**, **Firebase**, and **TMDB API**. It features user authentication, movie search, wishlist management, and runtime detail viewing.

---

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
