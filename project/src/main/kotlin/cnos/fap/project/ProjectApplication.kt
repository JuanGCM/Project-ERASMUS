package cnos.fap.project

import cnos.fap.project.models.User
import cnos.fap.project.repos.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
class ProjectApplication{

	@Bean
	fun init(
		usuRepo:UserRepository,
		encoder: PasswordEncoder
	)=CommandLineRunner{

		usuRepo.save(User("Admin","Admin","admin",encoder.encode("cnos"),555555555,"admin@cnosfap.com","Valdocco","Torino" ,"Valdoc","Valdocco","04564564","Hello World","ADMIN"))
	}
}

fun main(args: Array<String>) {
	runApplication<ProjectApplication>(*args)
}
