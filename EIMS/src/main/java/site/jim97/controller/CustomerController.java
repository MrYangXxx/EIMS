package site.jim97.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.jim97.entity.Customer;

@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController<Customer>{

}
