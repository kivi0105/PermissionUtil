# PermissionUtil
Android 6.0 权限封装

## How to
To get a Git project into your build: [JitPack](https://jitpack.io/)
- Step 1. Add the JitPack repository to your build file
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
- step 2:Add the dependency

```
compile 'com.github.kivi0105:PermissionUtil:master-SNAPSHOT'
```

- step 3:Use PermissionUtil

```
Sample:open the camera


private String[] permissions = new String[]{Manifest.permission.CAMERA};
...
PermissionUtil.with(MainActivity.this)
                        .addPermissions(permissions)  //permissions;
                        .addExplanation("explanation") //explanation,if need
                        .addCallback(new PermissionCallback() { //callback
                            @Override
                            public void granted() {
                                openCamera();      
                            }

                            @Override
                            public void denied(String str) {
                                Log.i(TAG, "denied: " + str);
                            }
                        })
                        .request();
```











