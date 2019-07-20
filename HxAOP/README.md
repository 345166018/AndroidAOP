[toc]


# 1 什么是AOP
AOP为Aspect Oriented Programming的缩写，意为：面向切面编程，通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术。

**1 ObjectOriented Programming：**
面向对象编程 把功能或问题模块化，每个模块处理自己
的家务事 。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190629145846502.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2hvbmd4dWU4ODg4,size_16,color_FFFFFF,t_70)

**2 AOP：**

1、Aspect Oriented Programming 面向切面编程 通过预编译方式和运行期动态代理实现程序功能的统一维护

2、在运行时，编译时，类加载期，动态地将代码切入到类的指定方法、指定位置上的编程思想。

3、AOP在编程历史上可以说是里程碑式的，对OOP编程是一种十分有益的补充。

4、AOP像OOP一样，只是一种编程方法论，AOP并没有规定说，实现AOP协议的代码，要用什么方式去实现。

5、OOP侧重静态，名词，状态，组织，数据，载体是空间；

6、AOP侧重动态，动词，行为，调用，算法，载体是时间；

---




# 2 AspectJ 简介

介绍：AspectJ是一个面向切面编程的框架。AspectJ是对java的扩展，而且是完全兼容java的,AspectJ定义了AOP语法，它有一个专门的编译器用来生成遵守Java字节编码规范的Class文件。AspectJ还支持原生的Java，只需要加上AspectJ提供的注解即可。在Android开发中，一般就用它提供的注解和一些简单的语法就可以实现绝大部分功能上的需求了。

## 2.1 AOP中的术语

|术语名  | 作用 |
|--|--|
|  Joinpoint(连接点)| 所谓连接点是指那些被拦截到的点 |
|Pointcut(切入点)|所谓切入点是指我们要对哪些 Joinpoint 进行拦截的定义|
|Advice(通知/增强)|所谓通知是指拦截到 Joinpoint 之后所要做的事情就是通知|
|Introduction(引介)|引介是一种特殊的通知在不修改类代码的前提下, Introduction 可以在运行期为类 动态地添加一些方法或 Field|
|Target(目标对象)|代理的目标对象|
|Weaving(织入)|是指把增强应用到目标对象来创建新的代理对象的过程. AspectJ 采用编译期织入和类装在期织入|
|Proxy（代理）|一个类被 AOP 织入增强后，就产生一个结果代理类|
|Aspect(切面)|是切入点和通知（引介）的结合|

---

## 2.2 Advice分类
| Advice分类|  |
|--|--|
|  Before|  前置通知, 在目标执行之前执行通知|
| After |  后置通知, 目标执行后执行通知|
|Around  |  环绕通知, 在目标执行中执行通知, 控制目标执行时机|
|  AfterReturning| 后置返回通知, 目标返回时执行通知 |
| AfterThrowing | 异常通知, 目标抛出异常时执行通知 |

---

## 2.3 切入点指示符
|  | |
|--|--|
|  execution| 用于匹配方法执行的连接点 |
| within |  用于匹配指定类型内的方法执行|
| this | 用于匹配当前AOP代理对象类型的执行方法；注意是AOP代理对象的类型匹配，这样就可能包括引入接口也类型匹配 |
| target | 用于匹配当前目标对象类型的执行方法；注意是目标对象的类型匹配，这样就不包括引入接口也类型匹配 |
|args  | 用于匹配当前执行的方法传入的参数为指定类型的执行方法 |
|  @within| 用于匹配所以持有指定注解类型内的方法 |
|  @target|  用于匹配当前目标对象类型的执行方法，其中目标对象持有指定的注解 |
| @args | 用于匹配当前执行的方法传入的参数持有指定注解的执行  |
| @annotation | 用于匹配当前执行方法持有指定注解的方法  |

---

# 3 AspectJ 使用

在app的build.gradle中
```
  implementation 'org.aspectj:aspectjrt:1.9.4'
```

在项目的build.gradle中添加工具

```
  classpath 'org.aspectj:aspectjtools:1.9.4'
```

注意：这里使用1.9.4，对应minSdkVersion 24。

定义注解

```
/**
 * 用来表示性能监控
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BehaviorTrace {
    String value();
}

```

在Activity定义点击事件

```
   <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="mShake"
        android:text="摇一摇"/>

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="mAudio"
        android:text="语音消息" />

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="mVideo"
        android:text="视频通话" />

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="saySomething"
        android:text="发表说说" />
```

方法上添加 BehaviorTrace 注解
```
  @BehaviorTrace("摇一摇")
    public void mShake(View view) {

    }
    //语音消息
    @BehaviorTrace("语音消息")
    public void mAudio(View view) {

    }

    //视频通话
    @BehaviorTrace("视频通话")
    public void mVideo(View view) {
    }

    //发表说说
    @BehaviorTrace("发表说说")
    public void saySomething(View view) {
    }
```
此时 上面的方法 就是Joinpoint(连接点)，也就是所谓被拦截到的点。


有了“点”之后，我们再去创建“面”BehaviorTraceAspect。
@Aspect 就表示一个切面，所有切入点要进入切面都会走methodAnnottatedWithBehaviorTrace方法

```
import android.os.SystemClock;
import android.util.Log;

import com.hongx.aoptest.annotation.BehaviorTrace;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Random;

@Aspect
public class BehaviorTraceAspect {
    //定义切面的规则
    //1、就再原来的应用中那些注解的地方放到当前切面进行处理
    //execution（注解名   注解用的地方）
    @Pointcut("execution(@com.hongx.aoptest.annotation.BehaviorTrace *  *(..))")
    public void methodAnnottatedWithBehaviorTrace() {
    }


    //2、对进入切面的内容如何处理
    //@Before 在切入点之前运行
    //@After("methodAnnottatedWithBehaviorTrace()")
    //@Around 在切入点前后都运行
    @Around("methodAnnottatedWithBehaviorTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        String value = methodSignature.getMethod().getAnnotation(BehaviorTrace.class).value();

        long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        SystemClock.sleep(new Random().nextInt(2000));
        long duration = System.currentTimeMillis() - begin;
        Log.d("hongxue", String.format("%s功能：%s类的%s方法执行了，用时%d ms",value, className, methodName, duration));
        return result;
    }
}

```

为了能输出日志，需要在app的build.gradle中添加如下代码：

```
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main

final def log = project.logger
final def variants = project.android.applicationVariants

//在构建工程时，执行编辑
variants.all { variant ->
    if (!variant.buildType.isDebuggable()) {
        log.debug("Skipping non-debuggable build type '${variant.buildType.name}'.")
        return;
    }

    JavaCompile javaCompile = variant.javaCompile
    javaCompile.doLast {
        String[] args = ["-showWeaveInfo",
                         "-1.9",
                         "-inpath", javaCompile.destinationDir.toString(),
                         "-aspectpath", javaCompile.classpath.asPath,
                         "-d", javaCompile.destinationDir.toString(),
                         "-classpath", javaCompile.classpath.asPath,
                         "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]
        log.debug "ajc args: " + Arrays.toString(args)

        MessageHandler handler = new MessageHandler(true);
        new Main().run(args, handler);
        for (IMessage message : handler.getMessages(null, true)) {
            switch (message.getKind()) {
                case IMessage.ABORT:
                case IMessage.ERROR:
                case IMessage.FAIL:
                    log.error message.message, message.thrown
                    break;
                case IMessage.WARNING:
                    log.warn message.message, message.thrown
                    break;
                case IMessage.INFO:
                    log.info message.message, message.thrown
                    break;
                case IMessage.DEBUG:
                    log.debug message.message, message.thrown
                    break;
            }
        }
    }
}
```
运行后点击按钮，查看日志：

```
 摇一摇功能：MainActivity类的mShake方法执行了，用时949 ms
 语音消息功能：MainActivity类的mAudio方法执行了，用时876 ms
 视频通话功能：MainActivity类的mVideo方法执行了，用时992 ms
发表说说功能：MainActivity类的saySomething方法执行了，用时778 ms
```

---

我们去看看
app\build\intermediates\javac\debug\compileDebugJavaWithJavac\classes\com\hongx\aoptest 目录下的MainActivity.class 

```

public class MainActivity extends AppCompatActivity {
    public MainActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(2131296284);
    }

    @BehaviorTrace("摇一摇")
    public void mShake(View view) {
        JoinPoint var3 = Factory.makeJP(ajc$tjp_0, this, this, view);
        mShake_aroundBody1$advice(this, view, var3, BehaviorTraceAspect.aspectOf(), (ProceedingJoinPoint)var3);
    }

    @BehaviorTrace("语音消息")
    public void mAudio(View view) {
        JoinPoint var3 = Factory.makeJP(ajc$tjp_1, this, this, view);
        mAudio_aroundBody3$advice(this, view, var3, BehaviorTraceAspect.aspectOf(), (ProceedingJoinPoint)var3);
    }

    @BehaviorTrace("视频通话")
    public void mVideo(View view) {
        JoinPoint var3 = Factory.makeJP(ajc$tjp_2, this, this, view);
        mVideo_aroundBody5$advice(this, view, var3, BehaviorTraceAspect.aspectOf(), (ProceedingJoinPoint)var3);
    }

    @BehaviorTrace("发表说说")
    public void saySomething(View view) {
        JoinPoint var3 = Factory.makeJP(ajc$tjp_3, this, this, view);
        saySomething_aroundBody7$advice(this, view, var3, BehaviorTraceAspect.aspectOf(), (ProceedingJoinPoint)var3);
    }

    static {
        ajc$preClinit();
    }
}
```

---

# 4 模仿淘宝式登录
这里使用动态代理实现

```
public interface ILogin {
    void toLogin();
}
```

```
public class MyHandler implements InvocationHandler {
    private Object target;
    private Context context;

    public MyHandler(Context context) {
        this.target = context;
        this.context = context;
    }

    /**
     * 这个invoke就是拦截Object对象的所有方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (SharePreferenceUtil.getBooleanSp(SharePreferenceUtil.IS_LOGIN, context)) {
            result = method.invoke(target, args);
        } else {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }
        return result;
    }
}

```

```
public class MainActivity extends AppCompatActivity implements ILogin {
    //接口的应用
    private ILogin proxyLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 第一个参数：类加载器
         * 第二个参数：代理对象的目标类
         * 第三个参数：回调处理类
         */
        proxyLogin = (ILogin) Proxy.newProxyInstance(
                this.getClassLoader(), new Class[]{ILogin.class}, new MyHandler(this));
    }

    public void me(View view) {
        proxyLogin.toLogin();
    }

    public void play(View view) {
        proxyLogin.toLogin();
    }

    @Override
    public void toLogin() {
        Intent intent = new Intent(MainActivity.this, MenberAcitivity.class);
        startActivity(intent);
    }

    public void look(View view) {
    	proxyLogin.toLogin();
    }
}
```

```
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        SharePreferenceUtil.setBooleanSp(SharePreferenceUtil.IS_LOGIN, true, this);
        finish();
    }
}
```

```
public class MenberAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menber);
    }

    public void login(View view) {
        SharePreferenceUtil.setBooleanSp(SharePreferenceUtil.IS_LOGIN, true, this);
        finish();
    }
}
```
