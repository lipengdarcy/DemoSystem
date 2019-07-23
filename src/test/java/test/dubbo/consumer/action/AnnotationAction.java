package test.dubbo.consumer.action;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import test.dubbo.provider.service.AnnotationService;

@Component("annotationAction")
public class AnnotationAction {

    @Reference
    private AnnotationService annotationService;
    
    public String doSayHello(String name) {
        return annotationService.sayHello(name);
    }
}