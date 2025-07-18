# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep model classes (misalnya untuk Gson, Moshi, Retrofit)
-keepclassmembers class com.example.model.** {
    *;
}

# Keep ViewModel (karena dipakai lewat reflection oleh Hilt/Dagger)
-keep class * extends androidx.lifecycle.ViewModel

# Hilt
-keep class dagger.hilt.** { *; }
-keepclassmembers class * {
    @dagger.hilt.android.lifecycle.HiltViewModel <init>(...);
}

# Room (hindari error waktu runtime)
-keep class androidx.room.** { *; }
-keep class * extends androidx.room.RoomDatabase

# Gson
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
