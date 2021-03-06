package com.seva.kinder.webservice;

import com.seva.kinder.StringConstants;
import com.seva.kinder.entity.Person;
import com.seva.kinder.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestTestApi {
    @Autowired
    private PersonService personService;
    public RestTestApi(PersonService personService){
        this.personService=personService;
    }
//    for get list
    @GetMapping(value = "/userlist",produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Person> getUserInfo(HttpServletRequest request){
        List<Person> personList =null;
        String key= request.getHeader(StringConstants.SEVA_SECRET_KEY);
        if(key !=null && key.equalsIgnoreCase("dev")){
            personList=personService.getAll();
        }
        return personList;
    }
//        for save info
    @PostMapping(value = "/save",produces = {MediaType.APPLICATION_JSON_VALUE})
    public String saveInfo(HttpServletRequest request,@RequestBody Person person){
        String key= request.getHeader(StringConstants.SEVA_SECRET_KEY);
            if(key !=null && key.equalsIgnoreCase("dev")) {
               Person personServiceByEmail=personService.getByEmail(person.getEmail());
                if (personServiceByEmail!= null) {
                    return "email address already should be unique";
                }
                else {
                    personService.savePerson(person);

                return person.toString();
                }
                }
                return null;
            }
    }

