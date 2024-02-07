package com.dev.authorizationservercodegrant.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration

public class SecurityWebConfig  extends WebSecurityConfigurerAdapter{
	
	/*
	 * 
	 * Criando um usuário em memória e criando uma instância 
	 * de UserDetailService para  buscarmos e gerenciarmos 
	 * um usuário 
	 * 
	 */
	@Bean
	public  UserDetailsService userServerDetails() {
		
		/*
		 * 
		 * Criando um instância de UserDetailsService 
		 * 
		 * o InMemoryUserDetailsManager() implementa o
		 * UserDetailsService
		 * 
		 */
		var userDetailsSevice = new InMemoryUserDetailsManager();
		
		
		/*
		 * 
		 * Criar uma instância de UserDetails
		 * 
		 * 
		 */
		
		var userDetails =
				 User.withUsername("marcos")
				.password("12345")
				.authorities("read")
				.build();
		
		
		/*
		 * 
		 * Cria o usuário na memória		
		 */
		userDetailsSevice.createUser(userDetails);
		
		
		return userDetailsSevice;
		
	}
	
	
	/*
	 * 
	 * 
	 * Configurando o método passwordEncoder para 
	 * 
	 * podemos validar a senha .Porém nessa implemnetação não utilizamos nenhum
	 * método para criptografar nossa senha. O NoOpPasswordEncoder é apenas
	 * utilizado para teste
	 */
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return  NoOpPasswordEncoder.getInstance();
	}
	
	/*
	 * 
	 * 
	 * Aqui tornamos a classe AuthenticationManager , que serve 
	 * para delegar o gerenciamento e validação  dos usuários para 
	 * o provedor de autenticação que executa a lógica de  autenticação
	 * .Também tornamos  o AuthenticationManager como um bean gerenciado 
	 * pelo springboot , assim podemos injeta-lo nas classes que queremos 
	 * utilizar esse serviço
	 * 
	 * 
	 * 
	 */
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
		
		
	}
	
		

}
