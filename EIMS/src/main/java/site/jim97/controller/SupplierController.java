package site.jim97.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.jim97.entity.Supplier;

@RestController
@RequestMapping("/supplier")
public class SupplierController extends BaseController<Supplier>{

}
