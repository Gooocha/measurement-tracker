package org.example.measurementtrackerclean.controller;
import org.springframework.stereotype.Controller;import org.springframework.web.bind.annotation.GetMapping;
@Controller public class ViewController {
 @GetMapping("/") String dash(){return "dashboard";} @GetMapping("/ui/login") String login(){return "login";} @GetMapping("/ui/register") String reg(){return "register";} @GetMapping("/ui/meters") String m(){return "meters";} @GetMapping("/ui/readings") String r(){return "readings";} @GetMapping("/ui/ocr") String o(){return "ocr";}
}
