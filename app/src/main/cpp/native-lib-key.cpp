#include <jni.h>
#include <string>

extern "C" jstring
Java_com_example_appclonecheck_MainActivity_getAPIKey(JNIEnv* env, jobject /* this */) {
    std::string api_key = "YOUR_AWESOME_API_KEY_GOES_HERE";
    return env->NewStringUTF(api_key.c_str());
}