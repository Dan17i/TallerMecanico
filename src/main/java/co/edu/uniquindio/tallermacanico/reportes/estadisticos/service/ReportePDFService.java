package co.edu.uniquindio.tallermacanico.reportes.estadisticos.service;

import co.edu.uniquindio.tallermacanico.reportes.estadisticos.dto.*;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Servicio encargado de generar reportes PDF a partir de datos estadísticos.
 * Utiliza la librería iText para construir documentos en memoria.
 */
@Service
public class ReportePDFService {

    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    /**
     * Genera un PDF con los ingresos mensuales.
     *
     * @param ingresos lista de ingresos por mes
     * @return PDF en formato byte[]
     */
    public byte[] generarPDFIngresosMensuales(List<IngresoMensualDTO> ingresos) {
        return generarReporteGenerico(
                "📊 Reporte de Ingresos Mensuales",
                new String[]{"Mes", "Total Ingresos"},
                ingresos.stream()
                        .map(i -> new String[]{i.getMes(), currencyFormat.format(i.getTotalIngresos())})
                        .toList()
        );
    }

    /**
     * Genera un PDF con los servicios más solicitados.
     *
     * @param servicios lista de servicios estadísticos
     * @return PDF en formato byte[]
     */
    public byte[] generarPDFServiciosSolicitados(List<ServicioEstadisticoDTO> servicios) {
        return generarReporteGenerico(
                "📌 Servicios Más Solicitados",
                new String[]{"Servicio", "Solicitudes"},
                servicios.stream()
                        .map(s -> new String[]{s.getNombreServicio(), String.valueOf(s.getTotalSolicitudes())})
                        .toList()
        );
    }

    /**
     * Genera un PDF con los repuestos más usados.
     *
     * @param repuestos lista de repuestos estadísticos
     * @return PDF en formato byte[]
     */
    public byte[] generarPDFRepuestosUsados(List<RepuestoEstadisticoDTO> repuestos) {
        return generarReporteGenerico(
                "🔧 Repuestos Más Usados",
                new String[]{"Repuesto", "Usos"},
                repuestos.stream()
                        .map(r -> new String[]{r.getNombreRepuesto(), String.valueOf(r.getTotalUsos())})
                        .toList()
        );
    }

    /**
     * Método genérico para generar reportes PDF con título, encabezados y filas dinámicas.
     *
     * @param titulo     título del reporte
     * @param encabezados encabezados de la tabla
     * @param filas      filas de datos
     * @return PDF en formato byte[]
     */
    private byte[] generarReporteGenerico(String titulo, String[] encabezados, List<String[]> filas) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdf);

        // Título
        doc.add(new Paragraph(titulo).setBold().setFontSize(14));

        // Tabla
        Table tabla = new Table(encabezados.length);

        // Encabezados con estilo
        for (String encabezado : encabezados) {
            Cell headerCell = new Cell().add(new Paragraph(encabezado).setBold());
            tabla.addCell(headerCell);
        }

        // Filas de datos
        if (filas.isEmpty()) {
            doc.add(new Paragraph("⚠️ No hay datos disponibles para este reporte."));
        } else {
            for (String[] fila : filas) {
                for (String valor : fila) {
                    tabla.addCell(valor);
                }
            }
            doc.add(tabla);
        }

        doc.close();
        return baos.toByteArray();
    }
}


