package sistemaprofesorado.sgp.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lowagie.text.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import sistemaprofesorado.sgp.model.Contrato;
import sistemaprofesorado.sgp.model.Profesor;
import sistemaprofesorado.sgp.model.TituloAcademico;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfService {

    private final AlmacenamientoService fileStorageService;

    private static final Font FONT_TITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Font.NORMAL, new java.awt.Color(0, 51, 102));
    private static final Font FONT_SUBTITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.NORMAL, java.awt.Color.BLACK);
    private static final Font FONT_CUERPO = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, java.awt.Color.DARK_GRAY);
    private static final Font FONT_NEGRITA = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Font.NORMAL, java.awt.Color.BLACK);

    public byte[] generarContratoPdf(Contrato contrato) throws IOException {
        Document document = new Document(PageSize.LETTER);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font fontCuerpo = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font fontNegrita = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

            Paragraph titulo = new Paragraph("CONTRATO DE PRESTACIÓN DE SERVICIOS", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            String nombreProfesor = contrato.getProfesor().getNombres() + " " + contrato.getProfesor().getApellidos();
            String documentoProfesor = contrato.getProfesor().getNumeroDocumento();
            String nombreCargo = contrato.getAplicacion().getOfertaLaboral().getTituloPuesto();
            String fechaInicio = contrato.getFechaInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String salario = String.format("$%.2f", contrato.getSalario());

            Paragraph cuerpo = new Paragraph();
            cuerpo.setAlignment(Element.ALIGN_JUSTIFIED);
            cuerpo.setLeading(20f);

            cuerpo.add(new Chunk("NOSOTROS: Por una parte, la institución, en adelante \"EL CONTRATANTE\", y por otra parte, ", fontCuerpo));
            cuerpo.add(new Chunk(nombreProfesor, fontNegrita));
            cuerpo.add(new Chunk(", con documento de identidad N° " + documentoProfesor + ", en adelante \"EL CONTRATISTA\", acordamos celebrar el presente contrato bajo las siguientes cláusulas:\n\n", fontCuerpo));
            
            cuerpo.add(new Chunk("PRIMERA (Objeto): ", fontNegrita));
            cuerpo.add(new Chunk("EL CONTRATISTA se compromete a prestar sus servicios profesionales desempeñando el cargo de " + nombreCargo + ".\n\n", fontCuerpo));

            cuerpo.add(new Chunk("SEGUNDA (Vigencia): ", fontNegrita));
            cuerpo.add(new Chunk("El presente contrato iniciará sus efectos a partir del día " + fechaInicio + ".\n\n", fontCuerpo));

            cuerpo.add(new Chunk("TERCERA (Honorarios): ", fontNegrita));
            cuerpo.add(new Chunk("EL CONTRATANTE pagará a EL CONTRATISTA la suma mensual de " + salario + " dólares de los Estados Unidos de América, pagaderos mensualmente.\n\n", fontCuerpo));

            cuerpo.add(new Chunk("CUARTA (Confidencialidad): ", fontNegrita));
            cuerpo.add(new Chunk("EL CONTRATISTA se obliga a guardar estricta confidencialidad sobre la información a la que tenga acceso.\n\n", fontCuerpo));

            document.add(cuerpo);
            document.add(new Paragraph("\n\n\n\n"));

            PdfPTable tablaFirmas = new PdfPTable(2);
            tablaFirmas.setWidthPercentage(100);

            PdfPCell celda1 = new PdfPCell(new Paragraph("__________________________\nPOR LA EMPRESA\nRepresentante Legal", fontCuerpo));
            celda1.setBorder(0);
            celda1.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaFirmas.addCell(celda1);

            PdfPCell celda2 = new PdfPCell(new Paragraph("__________________________\n" + nombreProfesor + "\nDUI: " + documentoProfesor, fontCuerpo));
            celda2.setBorder(0);
            celda2.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaFirmas.addCell(celda2);

            document.add(tablaFirmas);
            document.close();
        } catch (DocumentException e) {
            throw new IOException("Error al generar PDF del contrato", e);
        }

        return out.toByteArray();
    }

    public byte[] generarCurriculumPdf(Profesor profesor) throws IOException {
        Document document = new Document(PageSize.LETTER, 50, 50, 50, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{1, 3});

            PdfPCell cellFoto = agregarFotoPerfil(profesor.getFotoPerfil());
            headerTable.addCell(cellFoto);

            PdfPCell cellDatos = new PdfPCell();
            cellDatos.setBorder(Rectangle.NO_BORDER);
            cellDatos.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            Paragraph nombre = new Paragraph(profesor.getNombres() + " " + profesor.getApellidos(), FONT_TITULO);
            cellDatos.addElement(nombre);
            
            Paragraph especialidad = new Paragraph(validar(profesor.getEspecialidad()), FONT_SUBTITULO);
            especialidad.setSpacingAfter(10);
            cellDatos.addElement(especialidad);

            cellDatos.addElement(crearLineaContacto("Email: ", profesor.getEmail()));
            cellDatos.addElement(crearLineaContacto("Teléfono: ", profesor.getNumeroTelefonico()));
            cellDatos.addElement(crearLineaContacto("Ubicación: ", profesor.getMunicipio() + ", " + profesor.getDepartamento()));

            headerTable.addCell(cellDatos);
            document.add(headerTable);

            agregarSeparador(document);

            if (profesor.getResumenProfesional() != null && !profesor.getResumenProfesional().isEmpty()) {
                agregarTituloSeccion(document, "PERFIL PROFESIONAL");
                Paragraph resumen = new Paragraph(profesor.getResumenProfesional(), FONT_CUERPO);
                resumen.setAlignment(Element.ALIGN_JUSTIFIED);
                document.add(resumen);
                document.add(Chunk.NEWLINE);
            }

            agregarTituloSeccion(document, "INFORMACIÓN PERSONAL");
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingBefore(10);
            
            agregarFilaTabla(infoTable, "Documento Identidad:", profesor.getDocumento() + " " + profesor.getNumeroDocumento());
            agregarFilaTabla(infoTable, "NIT:", profesor.getDocumentoNIT());
            agregarFilaTabla(infoTable, "NUP:", profesor.getNup());
            agregarFilaTabla(infoTable, "ISSS:", profesor.getSeguroSocial());
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaNacStr = profesor.getFechaNacimiento() != null ? profesor.getFechaNacimiento().format(formatter) : "N/A";
            agregarFilaTabla(infoTable, "Fecha Nacimiento:", fechaNacStr);
            
            agregarFilaTabla(infoTable, "Estado Civil:", profesor.getEstadoCivil() != null ? profesor.getEstadoCivil().toString() : "");
            
            document.add(infoTable);
            document.add(Chunk.NEWLINE);

            agregarTituloSeccion(document, "FORMACIÓN ACADÉMICA");
            List<TituloAcademico> titulos = profesor.getTitulos();

            if (titulos != null && !titulos.isEmpty()) {
                for (TituloAcademico titulo : titulos) {
                    Paragraph pTitulo = new Paragraph(titulo.getTituloObtenido(), FONT_NEGRITA);
                    document.add(pTitulo);

                    String detalle = titulo.getNombreInstitucion() + " | " + titulo.getAnioGraduacion() + " | " + titulo.getNivelAcademico();
                    Paragraph pDetalle = new Paragraph(detalle, FONT_CUERPO);
                    pDetalle.setIndentationLeft(10);
                    pDetalle.setSpacingAfter(5);
                    document.add(pDetalle);
                }
            } else {
                document.add(new Paragraph("No se ha registrado información académica.", FONT_CUERPO));
            }

            document.add(Chunk.NEWLINE);
            Paragraph footer = new Paragraph("Generado por Sistema de Gestión SGP el " + java.time.LocalDate.now(), 
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8, java.awt.Color.GRAY));
            footer.setAlignment(Element.ALIGN_RIGHT);
            document.add(footer);

            document.close();
        } catch (DocumentException e) {
            throw new IOException("Error al construir el contenido del PDF", e);
        }

        return out.toByteArray();
    }

    private PdfPCell agregarFotoPerfil(String rutaFoto) {
        PdfPCell cellFoto = new PdfPCell();
        cellFoto.setBorder(Rectangle.NO_BORDER);
        try {
            if (rutaFoto != null && !rutaFoto.isEmpty()) {
                var resource = fileStorageService.cargarArchivo(rutaFoto);
                Image img = Image.getInstance(resource.getFile().getAbsolutePath());
                img.scaleToFit(100, 100);
                img.setAlignment(Element.ALIGN_CENTER);
                cellFoto.addElement(img);
            } else {
                cellFoto.addElement(new Paragraph(" "));
            }
        } catch (Exception e) {
            log.warn("No se pudo cargar la imagen de perfil para el PDF: {}", e.getMessage());
            cellFoto.addElement(new Paragraph(" "));
        }
        return cellFoto;
    }

    private void agregarTituloSeccion(Document doc, String texto) throws DocumentException {
        Paragraph p = new Paragraph(texto.toUpperCase(), FONT_SUBTITULO);
        p.setSpacingBefore(10);
        p.setSpacingAfter(5);
        doc.add(p);
        doc.add(new com.lowagie.text.pdf.draw.LineSeparator(0.5f, 100, java.awt.Color.LIGHT_GRAY, Element.ALIGN_CENTER, -2));
        doc.add(Chunk.NEWLINE);
    }

    private void agregarSeparador(Document doc) throws DocumentException {
        doc.add(Chunk.NEWLINE);
        doc.add(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, java.awt.Color.DARK_GRAY, Element.ALIGN_CENTER, -2));
        doc.add(Chunk.NEWLINE);
    }

    private Paragraph crearLineaContacto(String etiqueta, String valor) {
        Phrase phrase = new Phrase();
        phrase.add(new Chunk(etiqueta, FONT_NEGRITA));
        phrase.add(new Chunk(validar(valor), FONT_CUERPO));
        return new Paragraph(phrase);
    }

    private void agregarFilaTabla(PdfPTable table, String label, String value) {
        PdfPCell c1 = new PdfPCell(new Phrase(label, FONT_NEGRITA));
        c1.setBorder(Rectangle.NO_BORDER);
        c1.setPaddingBottom(5);
        
        PdfPCell c2 = new PdfPCell(new Phrase(validar(value), FONT_CUERPO));
        c2.setBorder(Rectangle.NO_BORDER);
        c2.setPaddingBottom(5);

        table.addCell(c1);
        table.addCell(c2);
    }

    private String validar(String texto) {
        return (texto == null || texto.isEmpty()) ? "N/A" : texto;
    }
}