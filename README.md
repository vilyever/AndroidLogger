# AndroidLogger
应用中查看log

## Import
[JitPack](https://jitpack.io/)

Add it in your project's build.gradle at the end of repositories:

```gradle
repositories {
  // ...
  maven { url "https://jitpack.io" }
}
```

Step 2. Add the dependency in the form

```gradle
dependencies {
  compile 'com.github.vilyever:AndroidLogger:1.0.0'
}
```

## Usage
```java

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LoggerDisplay.initialize(this); // 初始化，然后每个Activity都会显示log按钮
    }
}

```

## License
[Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)