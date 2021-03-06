package site.jim97.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import site.jim97.utils.StringUtil;

@Controller
public class RedirectController {
	
	@GetMapping(value={"/","/{prefix}.html"})
	public String index(@PathVariable(required=false) String prefix){
		if(StringUtil.isEmpty(prefix)){
			return "login";
		}else{
			return prefix.split("\\.")[0];
		}
	}
	
	/*@GetMapping("/main.html")
	public String main(){
		return "main";
	}
	
	@GetMapping("/home.html")
	public String home(){
		return "home";
	}*/
	
	@GetMapping("/purchase/{suffix}")
	public String purchase(@PathVariable String suffix){
		return "/purchase/"+suffix.split("\\.")[0];
	}
	
	@GetMapping("/system/{suffix}")
	public String user(@PathVariable String suffix){
		return "/system/"+suffix.split("\\.")[0];
	}
	
	@GetMapping("/basicdata/{suffix}")
	public String basicdata(@PathVariable String suffix){
		return "/basicdata/"+suffix.split("\\.")[0];
	}
	
	@GetMapping("/sale/{suffix}")
	public String sale(@PathVariable String suffix){
		return "/sale/"+suffix.split("\\.")[0];
	}
	
	@GetMapping("/stock/{suffix}")
	public String stock(@PathVariable String suffix){
		return "/stock/"+suffix.split("\\.")[0];
	}
	
	@GetMapping("/count/{suffix}")
	public String count(@PathVariable String suffix){
		return "/count/"+suffix.split("\\.")[0];
	}
}
