package sistemaprofesorado.sgp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import sistemaprofesorado.sgp.enums.Rol;
import sistemaprofesorado.sgp.model.Usuario;
import sistemaprofesorado.sgp.repository.UsuarioRepository;

@Configuration
public class DataInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.findByEmail("admin@sge.com").isEmpty()) {
                
                Usuario admin = new Usuario();
                admin.setEmail("admin@sge.com");
                admin.setPassword(passwordEncoder.encode("MiContr@123$"));
                admin.setRol(Rol.ADMINISTRATIVO);
                
                usuarioRepository.save(admin);
                
                logger.info("Usuario ADMINISTRADOR creado exitosamente: admin@sge.com");
            }
        };
    }
}
