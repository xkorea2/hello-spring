package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");  //data라는 키값에 hello!! 라는 밸류를 넣어줌.
        return "hello"; //여기서 resources 에 templates 로 가서, 저 파일을 찾는다.
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public HelloClass helloApi(@RequestParam("name") String name) { //HelloClass 타입으로 반환하겠다. 즉, 객체 반환.
        HelloClass hello = new HelloClass();
        hello.setName(name);
        return hello;
    }

    static class HelloClass {    //static class 로 클래스 내부에 클래스 선언할 수 있다. HelloController.Hello 로 사용하면 됨.
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
