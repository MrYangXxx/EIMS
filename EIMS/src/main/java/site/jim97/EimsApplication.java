package site.jim97;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("site.jim97.mapper")
public class EimsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EimsApplication.class, args);
	}

}

