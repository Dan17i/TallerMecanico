package co.edu.uniquindio.tallermacanico.reportes.estadisticos.service;

import co.edu.uniquindio.tallermacanico.reportes.estadisticos.dto.*;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportePDFService {

    public byte[] generarPDFIngresosMensuales(List<IngresoMensualDTO> ingresos) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdf);

        doc.add(new Paragraph("📊 Reporte de Ingresos Mensuales"));
        Table tabla = new Table(2);
        tabla.addCell("Mes");
        tabla.addCell("Total Ingresos");

        for (IngresoMensualDTO ingreso : ingresos) {
            tabla.addCell(ingreso.getMes());
            tabla.addCell("$" + ingreso.getTotalIngresos());
        }

        doc.add(tabla);
        doc.close();
        return baos.toByteArray();
    }

    public byte[] generarPDFServiciosSolicitados(List<ServicioEstadisticoDTO> servicios) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdf);

        doc.add(new Paragraph("📌 Servicios Más Solicitados"));
        Table tabla = new Table(2);
        tabla.addCell("Servicio");
        tabla.addCell("Solicitudes");

        for (ServicioEstadisticoDTO servicio : servicios) {
            tabla.addCell(servicio.getNombreServicio());
            tabla.addCell(String.valueOf(servicio.getTotalSolicitudes()));
        }

        doc.add(tabla);
        doc.close();
        return baos.toByteArray();
    }

    public byte[] generarPDFRepuestosUsados(List<RepuestoEstadisticoDTO> repuestos) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdf);

        doc.add(new Paragraph("🔧 Repuestos Más Usados"));
        Table tabla = new Table(2);
        tabla.addCell("Repuesto");
        tabla.addCell("Usos");

        for (RepuestoEstadisticoDTO repuesto : repuestos) {
            tabla.addCell(repuesto.getNombreRepuesto());
            tabla.addCell(String.valueOf(repuesto.getTotalUsos()));
        }

        doc.add(tabla);
        doc.close();
        return baos.toByteArray();
    }
}

