# 说明

参考原文[Android native音频：录制播放的实现以及低延迟音频方案](https://blog.csdn.net/zuguorui/article/details/107897428)，主要是测试他的项目 [FastPathAudioEcho](https://github.com/zuguorui/FastPathAudioEcho) 项目是否能实现耳返低延迟。Fork了一份代码，在AndroidStudio环境中编译通过。

去掉了AAudio的适配，我需要跑在Android 5.0的系统上，而AAudio只支持Android8.0以上的系统。Oboe使用了 [Oboe 1.4.3](https://github.com/google/oboe/releases/tag/1.4.3) 主要为了用一个早于作者开源时候的版本。

改用 main 分支
