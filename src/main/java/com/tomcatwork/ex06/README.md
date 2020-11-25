## Lifecycle 接口
Catalina包含了很多組件，当Catalina启动时，这些组件也会一起启动，同样当Catalina关闭时，这些组件也会随之关闭。
通过实现org.apache.catalina.lifecycle接口，可以达到统一启动/关闭这些组件的效果
实现了org.apache.catalina.lifecycle接口的组件可以触发一个或者多个下面的事件：
- BEFORE_START_EVENT
- START_EVENT
- AFTER_START_EVENT
- BEFORE_STOP_EVENT
- STOP_EVENT
- AFTER_STOP_EVENT
当组件启动时，正常会触发前3个事件；而关闭组件时，会触发后3个事件

## LifecycleEvent类
生命周期事件

## LifecycleListener接口
生命周期事件监听器

## LifecycleSupport类
工具类，帮助组件管理监听器，并触发相应的生命周期事件