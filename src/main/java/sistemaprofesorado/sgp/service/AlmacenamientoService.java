package sistemaprofesorado.sgp.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sistemaprofesorado.sgp.exceptions.AlmacenamientoException;

import org.springframework.beans.factory.annotation.Value;

@Service
public class AlmacenamientoService {
    private final Path fileStorageLocation;

    public AlmacenamientoService(@Value("${storage.location}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new AlmacenamientoException("No se pudo crear el directorio de uploads.", ex);
        }
    }

    public String guardarArchivo(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) return null;

        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileExtension;

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new AlmacenamientoException("No se pudo guardar el archivo " + fileName, ex);
        }
    }

    public Resource cargarArchivo(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new AlmacenamientoException("Archivo no encontrado: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new AlmacenamientoException("Archivo no encontrado " + fileName, ex);
        }
    }
}
