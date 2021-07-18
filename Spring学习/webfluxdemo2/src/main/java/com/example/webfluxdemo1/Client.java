package com.example.webfluxdemo1;

import com.example.webfluxdemo1.entity.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/11
 **/
public class Client {
    public static void main(String[] args) {
        //调用服务器地址
        WebClient webClient = WebClient.create("http://127.0.0.1:56264");
        //根据id查询
        String id = "1";
        User user = webClient.get().uri("/users/{id}", id).accept(MediaType.APPLICATION_JSON).
                retrieve().bodyToMono(User.class).block();
        System.out.println(user.getName());
        //查询所有
        Flux<User> results = webClient.get().uri("/users").accept(MediaType.APPLICATION_JSON).
                retrieve().bodyToFlux(User.class);
        results.map(stu -> stu.getName()).buffer().doOnNext(System.out::println).blockFirst();
    }
}
