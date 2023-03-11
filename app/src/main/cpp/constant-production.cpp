#include <jni.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_justordinarydiaryapp_utils_JNIUtil_getBaseUrlImpl(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("https://diary-test.ifdenewhallaid.com/");
}