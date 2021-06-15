package isa9.Farmacy.config;


import isa9.Farmacy.security.TokenUtils;
import isa9.Farmacy.security.auth.RestAuthenticationEntryPoint;
import isa9.Farmacy.security.auth.TokenAuthenticationFilter;
import isa9.Farmacy.service.impl.db.dbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
// Ukljucivanje podrske za anotacije "@Pre*" i "@Post*" koje ce aktivirati autorizacione provere za svaki pristup metodi
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// Implementacija PasswordEncoder-a koriscenjem BCrypt hashing funkcije.
	// BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Servis koji se koristi za citanje podataka o korisnicima aplikacije
	@Autowired
	private dbUserService userService;

	// Handler za vracanje 401 kada klijent sa neodogovarajucim korisnickim imenom i lozinkom pokusa da pristupi resursu
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	// Registrujemo authentication manager koji ce da uradi autentifikaciju korisnika za nas
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	// Definisemo uputstvo za authentication managera koji servis da koristi da izvuce podatke o korisniku koji zeli da se autentifikuje,
	//kao i kroz koji enkoder da provuce lozinku koju je dobio od klijenta u zahtevu da bi adekvatan hash koji dobije kao rezultat bcrypt algoritma uporedio sa onim koji se nalazi u bazi (posto se u bazi ne cuva plain lozinka)
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	// Injektujemo implementaciju iz TokenUtils klase kako bismo mogli da koristimo njene metode za rad sa JWT u TokenAuthenticationFilteru
	@Autowired
	private TokenUtils tokenUtils;

	// Definisemo prava pristupa odredjenim URL-ovima
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// komunikacija izmedju klijenta i servera je stateless posto je u pitanju REST aplikacija
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				// sve neautentifikovane zahteve obradi uniformno i posalji 401 gresku
				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

				// svim korisnicima dopusti da pristupe putanjama /auth/**, (/h2-console/** ako se koristi H2 baza) i /api/foo
				.authorizeRequests()
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/api/pharmacies").permitAll()
				.antMatchers("/api/pharmacies/search").permitAll()
				.antMatchers("/api/medicines").permitAll()
				.antMatchers("/api/medicines/**").permitAll()
				.antMatchers("/api/medicines/tmp-test").permitAll()
				.antMatchers("/api/medicines/search").permitAll()
				.antMatchers("/api/medicines/prices/**").permitAll()
				.antMatchers("/api/medicines/medicinesInStock").permitAll()
				.antMatchers("/api/auth/getLoggedIn").permitAll()
				.antMatchers("/api/users/dermatologists/pharmacy/**").permitAll()
				.antMatchers("/api/users/pharmacists/pharmacy/**/**").permitAll()
				.antMatchers("/api/users/pharm/filter/pharmacy/**").permitAll()
				.antMatchers("/api/users/derm/filter/pharmacy/**").permitAll()
				.antMatchers("/api/users/allDermatologists**").permitAll()
				.antMatchers("/api/users/allPharmacists**").permitAll()
				.antMatchers("/api/users/dermatologists/**").permitAll()
				.antMatchers("/api/users/pharmacists/**").permitAll()
				.antMatchers("/api/users/register/patient").permitAll()
				.antMatchers("/api/users/activatePatient**").permitAll()
				.antMatchers("/api/medicines/pharmacy/**").permitAll()
				.antMatchers("/api/pharmacies/**").permitAll()
				.antMatchers("/api/users/patients/**").permitAll()
				.antMatchers("/api/appointments/**").permitAll()	// DELETE THIS LINE (DEVELOP PURPOSES MilosPP)
				.antMatchers("/api/appointments/calendar/derm/**/pharmacy/**").permitAll()

				
				// za svaki drugi zahtev korisnik mora biti autentifikovan
				.anyRequest().authenticated().and()
				// za development svrhe ukljuci konfiguraciju za CORS iz WebConfig klase
				.cors().and()

				// umetni custom filter TokenAuthenticationFilter kako bi se vrsila provera JWT tokena umesto cistih korisnickog imena i lozinke (koje radi BasicAuthenticationFilter)
				.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, userService),
						BasicAuthenticationFilter.class);
		// zbog jednostavnosti primera
		http.csrf().disable();
	}

	// Generalna bezbednost aplikacije
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
		web.ignoring().antMatchers(HttpMethod.POST, "/api/auth/**",
                                                               "/api/users/register/patient",
																"api/auth/getLoggedIn/**",
															"/api/users/dermatologists/pharmacy/**",
															"/api/users/pharmacists/pharmacy/**/**",
															"/api/users/pharm/filter/pharmacy/**",
															"/api/users/derm/filter/pharmacy/**",
															"/api/users/register/patient",
															"/api/users/activatePatient**",
															"/api/medicines/pharmacy/**",
				"/api/users/allDermatologists**", "/api/users/allPharmacists**",
				"/api/users/dermatologists/**", "/api/users/pharmacists/**",
															"/api/medicines/prices/**");  // <-- svi korisnici mogu da udju na ove stranice
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html","/**/*.css", "/**/*.js");
	}

}
