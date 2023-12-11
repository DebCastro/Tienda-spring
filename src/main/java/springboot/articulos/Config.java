package springboot.articulos;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import springboot.articulos.interceptores.InterceptorAdmin;



@Component
public class Config implements WebMvcConfigurer{

	@Autowired
	private InterceptorAdmin  interceptorAdmin;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptorAdmin);
		registry.addInterceptor(localeChangeInterceptor());//agregamos el interceptor para el cambio de idioma definido mas abajo
	}
	//CONFIGURACIÓN para poder cambiar de idioma
	@Bean
	public LocaleResolver localeResolver() {
		//aqui indicamos ue la selleccion de idioma se mantenga en sesión
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.getDefault());
		return localeResolver;
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setIgnoreInvalidLocale(true);
		localeChangeInterceptor.setParamName("idioma");
		return localeChangeInterceptor;
	}

	
}
