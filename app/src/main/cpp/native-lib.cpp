#include <jni.h>
#include <string>
#include <cmath>
#include <sstream>
#include <iomanip>

using namespace std;

string fmt(double x) {
    ostringstream oss;
    if (fabs(x - round(x)) < 1e-9) oss << (long long)round(x);
    else oss << setprecision(6) << x;
    return oss.str();
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_minios_MainActivity_calculate(
        JNIEnv* env, jobject, jdouble a, jdouble b, jstring opStr) {
    const char* op = env->GetStringUTFChars(opStr, nullptr);
    double result = 0;
    switch (op[0]) {
        case '+': result = a + b; break;
        case '-': result = a - b; break;
        case '*': result = a * b; break;
        case '/': result = (b != 0) ? a / b : NAN; break;
        case '^': result = pow(a, b); break;
    }
    env->ReleaseStringUTFChars(opStr, op);
    return env->NewStringUTF(fmt(result).c_str());
}
