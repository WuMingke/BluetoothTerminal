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

#BaseRecyclerViewAdapterHelper
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}

# dagger
-dontwarn dagger.**
-dontwarn com.squareup.javapoet.**
-dontwarn com.google.common.**
-dontwarn com.google.errorprone.annotations.*

#-ButterKnife 7.0
 -keep class butterknife.** { *; }
 -dontwarn butterknife.internal.**
 -keep class **$$ViewBinder { *; }
 -keepclasseswithmembernames class * {
  @butterknife.* <fields>;
 }
 -keepclasseswithmembernames class * {
 @butterknife.* <methods>;
 }

 #eventbus
 -keepattributes *Annotation*
 -keepclassmembers class ** {
     @org.greenrobot.eventbus.Subscribe <methods>;
 }
 -keep enum org.greenrobot.eventbus.ThreadMode { *; }

   # Gson
   -keep class com.google.gson.stream.** { *; }
   -keepattributes EnclosingMethod
   -keep class com.whosssmade.bluetoothterminal.model.bean.**{*;}

   # RxJava RxAndroid
   -dontwarn sun.misc.**
   -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
       long producerIndex;
       long consumerIndex;
   }
   -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
       rx.internal.util.atomic.LinkedQueueNode producerNode;
   }
   -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
       rx.internal.util.atomic.LinkedQueueNode consumerNode;
   }
