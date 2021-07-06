package fengkuang.code_9_4;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/4
 **/
//创建我自己的订阅者
class MySubscriber<T> implements Flow.Subscriber {
    //发布者与订阅者之间的纽带
    private Flow.Subscription subscription;
    //订阅时会触发该问题
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        //开始请求数据
        subscription.request(1);
    }
    //接收到数据是会触发该方法
    @Override
    public void onNext(Object item) {
        System.out.println("获取到数据" + item);
        //请求下一条数据
        subscription.request(1);
    }
    //订阅出错时
    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        synchronized ("java") {
            "java".notifyAll();
        }
    }
    //订阅结束时触发该方法
    @Override
    public void onComplete() {
        System.out.println("订阅结束时");
        synchronized ("java") {
            "java".notifyAll();
        }
    }
}
public class PubSubTest {
    public static void main(String[] args) {
        //创建一个SubmissionPublisher作为发布者
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        //创建订阅者
        MySubscriber<String> subscriber = new MySubscriber<>();
        //注册订阅者
        publisher.subscribe(subscriber);
        //发布几个数据项
        System.out.println("开发发布数据...");
        List.of("Java", "Go", "Erlang", "Swift", "Lua")
                .forEach(im -> {
                    //提交数据
                    publisher.submit(im);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        //发布结束
        publisher.close();
        //发布结束之后，为了让线程不会死亡，暂停线程
        synchronized ("java") {
            try {
                "java".wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
