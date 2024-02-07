package com.dev.authorizationservercodegrant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer

/*
 * 
 * Testando nosso client 
 * 
 * http://localhost:8080/oauth/
authorize?response_type=code&client_id=client&scope=read
que utiliza o  authotization client 

Após digitar essa url no navegador e aprovar os escopos 

o servidor de autorização irá no redirecionar para

http://localhost:9090/home?code=6Og4Ff e nos oferecer um código de autorização

na qual o client pode solicitar para obter um código de acesso chamado 
ponto de  extremidade /oauth/token . 

Para solicitaros o token podemos utilizar o comando abaixo no prompt.

Obse : o code é o que obtemos nessa url 
http://localhost:9090/home?

curl -v -XPOST -u client:secret "http://localhost:8080/oauth/
token?grant_type=authorization_code&scope=read&code=60g4Ff"



 */
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	/*
	 * 
	 * 
	 * Tipo de concessão "authorization code", o servidor também precisa fornecer
	 * uma página para onde o cliente redireciona o usuário para o login.
	 * 
	 * 
	 * Implementamos essa página usando a configuração de form-login
	 * 
	 * 
	 */

	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin();
	}

	/*
	 * 
	 * Forma mais enxuta de configurar um client
	 * 
	 * 
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory().withClient("client").secret("secret").authorizedGrantTypes("authorization_code")
				/*
				 * Uri de redirecionamento
				 * 
				 */
				.scopes("read").redirectUris("http://localhost:9090/home");

		/*
		 * 
		 * 
		 * É possível ter vários clientes e cada um pode usar concessões diferentes. No
		 * entanto, também é possível configurar várias concessões para um único
		 * cliente. O servidor de autorização age de acordo com a solicitação do
		 * cliente. Dê uma olhada na listagem a seguir para ver como é possível
		 * configurar diferentes concessões para diferentes clientes.
		 */

		/*
		 * Client 1
		 * 
		 * Cliente com ID client1 só pode usar a concessão do código_de_autorização
		 * 
		 * 
		 * clients.inMemory().withClient("client1") .secret("secret1")
		 * .authorizedGrantTypes("authorization_code") .scopes("read")
		 * .redirectUris("http://localhost:9090/home") .and()
		 * 
		 * 
		 * Client 2
		 * 
		 
		 * Cliente com ID client2 pode usar qualquer um de código_de_autorização, senha
		 * e tokens de atualização
		 * 
		 * 
		 * .withClient("client2").secret("secret2")
		 * .authorizedGrantTypes("authorization_code", "password",
		 * "refresh_token").scopes("read") .redirectUris("http://localhost:9090/home");
		 * 
		 */

	}

	/*
	 * 
	 * Injetando um AuthenticationManager
	 * 
	 * 
	 */

	@Autowired
	AuthenticationManager authenticationManager;

	/*
	 * 
	 * Sobreescrevendo o método configure da classe pai
	 * 
	 * 
	 */

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

		endpoints.authenticationManager(authenticationManager);
	}

}
