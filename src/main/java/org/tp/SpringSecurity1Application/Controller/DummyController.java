package org.tp.SpringSecurity1Application.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dummy")
public class DummyController {
    @GetMapping
    public String returanString(){
        String name="mounika";
        return  name;
    }
}
