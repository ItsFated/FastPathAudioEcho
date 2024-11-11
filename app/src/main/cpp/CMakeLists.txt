# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -stdlib=libc++")

#file(GLOB CPP_FILES "./*.cpp", "./SLESEcho/*.cpp", "./AAudioEcho/*.cpp", "./OboeEcho/*.cpp")
file(GLOB CPP_FILES "./*.cpp", "./SLESEcho/*.cpp", "./OboeEcho/*.cpp")

#include_directories("./", "./SLESEcho/", "./AAudioEcho/", "./OboeEcho/")
include_directories("./", "./SLESEcho/", "./OboeEcho/")


# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.


add_library( # Sets the name of the library.
        native-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        ${CPP_FILES})

# 1. 设置 oboe 根目录路径变量
set(OBOE_DIR ${CMAKE_CURRENT_SOURCE_DIR}/../../../oboe)

# 2. 添加头文件路径
include_directories(
        ${OBOE_DIR}/include
        ${OBOE_DIR}/include/oboe
)

# 3. 添加预编译的 oboe 库
add_library(oboe SHARED IMPORTED)

# 4. 根据不同 ABI 设置 SO 库路径
if(${ANDROID_ABI} STREQUAL "arm64-v8a")
    set_target_properties(oboe PROPERTIES IMPORTED_LOCATION
            ${OBOE_DIR}/libs/arm64-v8a/liboboe.so)
elseif(${ANDROID_ABI} STREQUAL "armeabi-v7a")
    set_target_properties(oboe PROPERTIES IMPORTED_LOCATION
            ${OBOE_DIR}/libs/armeabi-v7a/liboboe.so)
elseif(${ANDROID_ABI} STREQUAL "x86")
    set_target_properties(oboe PROPERTIES IMPORTED_LOCATION
            ${OBOE_DIR}/libs/x86/liboboe.so)
elseif(${ANDROID_ABI} STREQUAL "x86_64")
    set_target_properties(oboe PROPERTIES IMPORTED_LOCATION
            ${OBOE_DIR}/libs/x86_64/liboboe.so)
endif()

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        native-lib
        OpenSLES
#        aaudio
        oboe
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})