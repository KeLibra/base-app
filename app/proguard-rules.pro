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

-keep class kotlin.** { *; }
-keep class org.jetbrains.** { *; }

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法
-optimizations !class/unboxing/enum

-dontusemixedcaseclassnames # 是否使用大小写混合
-dontskipnonpubliclibraryclasses
-verbose # 混淆时是否记录日志

-dontoptimize
-dontpreverify  # 混淆时是否做预校验


# 各种 bean不混淆
-keep public class * extends cn.vastsky.lib.base.common.base.bean.BaseBean
-keep public class cn.vastsky.libs.common.model.**{*;}
-keep public class cn.vastsky.onlineshop.installment.router.**{*;}

-keepattributes InnerClasses
-keep class com.alibaba.android.vlayout.ExposeLinearLayoutManagerEx{*;}
-keep class com.alibaba.android.vlayout.**{*;}
-keep class android.support.v7.widget.RecyclerView$LayoutParams{*;}
-keep class android.support.v7.widget.RecyclerView$ViewHolder{*;}
-keep class android.support.v7.widget.ChildHelper{*;}
-keep class android.support.v7.widget.ChildHelper$Bucket{*;}
-keep class android.support.v7.widget.RecyclerView$LayoutManager{*;}


-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keepattributes Signature
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

#-library dyuproject
-keeppackagenames java.util.**
-dontwarn java.util.**

-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}
-keepclasseswithmembers class * {    # 保持自定义控件类不被混淆
    public <init>(android.content.Context);
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keep class * extends android.app.Application {*;}

# 保留Serializable 序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
   static final long serialVersionUID;
   private static final java.io.ObjectStreamField[] serialPersistentFields;
   !static !transient <fields>;
   private void writeObject(java.io.ObjectOutputStream);
   private void readObject(java.io.ObjectInputStream);
   java.lang.Object writeReplace();
   java.lang.Object readResolve();
}

# 对R文件下的所有类及其方法，都不能被混淆
-keepclassmembers class **.R$* {
    public static <fields>;
}

# 对于带有回调函数onXXEvent的，不能混淆
-keepclassmembers class * {
    void *(**On*Event);
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**
-keep class android.support.** { *; }

-keep class com.nineoldandroids.animation.** {*;}

-keep public class java.util.**{ *; }

-keepattributes *JavascriptInterface*

# 抑制警告
-ignorewarnings
-dontnote
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*


-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.FragmentActivity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Instrumentation
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService


-keepclasseswithmembernames class * {
 native <methods>;
}
-keepclasseswithmembers class * {
  public <init>(android.content.Context, android.util.AttributeSet);
 }
-keepclasseswithmembers class * {
  public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
  public void *(android.view.View);
}
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


##Glide
-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.**{*;}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public class * extends com.bumptech.glide.module.LibraryGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.GeneratedAppGlideModuleImpl { *; }

# 人脸认证和活体
-keep public class com.megvii.**{*;}


# 支付宝相关的
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}


# okhttp
-keepattributes SourceFile,LineNumberTable
-keep class com.parse.*{ *; }
-dontwarn com.parse.**
-dontwarn com.squareup.picasso.**
-keepclasseswithmembernames class * {
    native <methods>;
}


# Okio
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }

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


-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


-keep class com.nineoldandroids.animation.** {*;}

# keep jar start *********************************************
#-libraryjars libs/android-support-v4.jar
-dontwarn android.support.v4.**
-keep class android.support.v4.**  { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-dontwarn android.support.v7.**
-keep class android.support.v7.widget.**  { *; }
-keep public class * extends android.app.Fragment

#-libraryjars libs/okio.jar
-dontwarn okio.**
-keep class okio.**{*;}

-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.**{*;}
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
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

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod

#-libraryjars protobuf
-dontwarn com.google.**
-keep class com.google.protobuf.** { *; }

-keep class com.umeng.** {*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class cn.blackfish.android.financialmarket.R$*{
public static final int *;
}
#-libraryjars libs/znet.jar
-dontwarn znetframework.**
-keep class znetframework.**{*;}
# keep jar end *********************************************

#-library dyuproject
-keeppackagenames java.util.**
-dontwarn java.util.**

# Keep multidex.
-dontwarn android.support.multidex.**
-keep class android.support.multidex.** {*;}

-keep class com.facebook.** {*;}
-dontwarn com.facebook.**

-keepclasseswithmembers class * {
 public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
 public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers enum * {
 public static **[] values();
 public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
 public static final android.os.Parcelable$Creator *;
}

# keep 泛型
-keepattributes Signature
-keepattributes EnclosingMetho

# Understand the @Keep support annotation.
-keep class android.support.annotation.Keep
-keep @android.support.annotation.Keep class * {*;}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

# ButterKnife--begin
# Retain generated class which implement ViewBinder.
-keep public class * implements butterknife.internal.ViewBinder { public <init>(); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinder.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }
# ButterKnife--end

-keepclasseswithmembernames class * {
 native <methods>;
}

-dontwarn net.jcip.annotations.**
-dontwarn org.apache.commons.**
-dontwarn java.nio.file.**
-dontwarn org.codehaus.mojo.**

-keep class net.jcip.annotations.**{*;}
-keep class org.apache.commons.**{*;}
-keep class java.nio.file.**{*;}
-keep class org.codehaus.mojo.**{*;}

#eventbus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(Java.lang.Throwable);
}
#eventbus end


-keep class * extends android.app.Fragment {
  public void setUserVisibleHint(boolean);
  public void onHiddenChanged(boolean);
  public void onResume();
  public void onPause();
}
-keep class android.support.v4.app.Fragment {
  public void setUserVisibleHint(boolean);
  public void onHiddenChanged(boolean);
  public void onResume();
  public void onPause();
}
-keep class * extends android.support.v4.app.Fragment {
  public void setUserVisibleHint(boolean);
  public void onHiddenChanged(boolean);
  public void onResume();
  public void onPause();
}
-keep class com.growingio.android.sdk.collection.GrowingIOInstrumentation{
  public *;
  static <fields>;
}

#友盟
-keep class com.umeng.** {*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}


# okhttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# retrofit2
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement


#-optimizationpasses 7
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontoptimize
-dontusemixedcaseclassnames
-verbose
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**
#-overloadaggressively


#------------------  下方是android平台自带的排除项，这里不要动         ----------------

-keep public class * extends android.app.Activity{
	public <fields>;
	public <methods>;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepattributes *Annotation*

-keepclasseswithmembernames class *{
	native <methods>;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#------------------  下方是共性的排除项目         ----------------
# 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
# 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除

-keepclasseswithmembers class * {
    ... *JNI*(...);
}

-keepclasseswithmembernames class * {
	... *JRI*(...);
}

-keep class **JNI* {*;}


-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class java.awt.** { *; }
-dontwarn com.sun.jna.**
-keep class com.sun.jna.** { *; }

#-- udcredit  NEED --
-keep class com.face.** {*;}
-keep class cn.com.bsfit.** {*;}
-keep class com.android.snetjob.** {*;}
-keep class com.udcredit.** { *; }
-keep class com.authreal.** { *; }
-keep class com.hotvision.** { *; }
-keep class com.google.gson.stream.** { *; }

