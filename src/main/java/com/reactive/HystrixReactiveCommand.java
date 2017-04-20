package com.reactive;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.io.IOException;


/**
 * Created by rsikora on 4/14/2017.
 */
public class HystrixReactiveCommand extends HystrixObservableCommand<Task> {

    protected HystrixReactiveCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(name))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(10 * 1_000)));
    }

    @Override
    protected Observable<Task> construct() {
        return Observable.create((Subscriber<? super Task> subscriber) -> {
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpGet httpget = new HttpGet("http://localhost:9000/fake?time=5000");
                CloseableHttpResponse response = httpclient.execute(httpget);
                subscriber.onNext(new Task(IOUtils.toString(response.getEntity().getContent())));
            } catch (IOException e) {
                subscriber.onError(e);
            }
            //System.out.println(Thread.currentThread().getName());
            subscriber.onCompleted();
        })
        .subscribeOn(Schedulers.newThread());
    }

    @Override
    protected Observable<Task> resumeWithFallback() {
        return Observable.empty();
    }
}
