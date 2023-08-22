
-verbose
-flattenpackagehierarchy

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

#-repackageclasses
#-optimizationpasses 10

#-=-=-=-=-==-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- Use This Code to Don't affect any class in this package -=-=-=-==-=-=-=-=-=-=-=

#-keep class cn.jzvd.** { *; }
#-keepclassmembers class cn.jzvd.** { *; }

-keep class org.apache.xerces.** { *; }
-keepclassmembers class org.apache.xerces.** { *; }

-keep class io.reactivex.** { *; }
-keepclassmembers class io.reactivex.** { *; }

-keep class rx.** { *; }
-keepclassmembers class rx.** { *; }

#-keep class com.fftools.speedtest.internet.adapter.ServerListAdapter.** { *; }
#-keepclassmembers class com.fftools.speedtest.internet.adapter.ServerListAdapter.** { *; }

-keep class com.moneyclub.loanclub.getmoney.club_callbacks.** { *; }
-keepclassmembers class com.moneyclub.loanclub.getmoney.club_callbacks.** { *; }


-keep class io.paperdb.** { *; }
-keep class com.esotericsoftware.** { *; }
-dontwarn com.esotericsoftware.**
-keep class de.javakaffee.kryoserializers.** { *; }
-dontwarn de.javakaffee.kryoserializers.**

#-=-=-=-=-==-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- End Of Code -=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
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