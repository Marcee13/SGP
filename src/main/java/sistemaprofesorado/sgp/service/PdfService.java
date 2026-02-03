package sistemaprofesorado.sgp.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import sistemaprofesorado.sgp.model.Contrato;

@Service   
public class PdfService {
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

            com.lowagie.text.pdf.PdfPTable tablaFirmas = new com.lowagie.text.pdf.PdfPTable(2);
            tablaFirmas.setWidthPercentage(100);

            com.lowagie.text.pdf.PdfPCell celda1 = new com.lowagie.text.pdf.PdfPCell(new Paragraph("__________________________\nPOR LA EMPRESA\nRepresentante Legal", fontCuerpo));
            celda1.setBorder(0);
            celda1.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaFirmas.addCell(celda1);

            com.lowagie.text.pdf.PdfPCell celda2 = new com.lowagie.text.pdf.PdfPCell(new Paragraph("__________________________\n" + nombreProfesor + "\nDUI: " + documentoProfesor, fontCuerpo));
            celda2.setBorder(0);
            celda2.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaFirmas.addCell(celda2);

            document.add(tablaFirmas);

            document.close();
        } catch (Exception e) {
            throw new IOException("Error al generar PDF", e);
        }

        return out.toByteArray();
    }
}
