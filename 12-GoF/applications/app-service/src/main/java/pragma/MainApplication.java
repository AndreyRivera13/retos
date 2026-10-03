package pragma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import pragma.model.ContadorReportes;
import pragma.model.ExportadorCSV;
import pragma.model.ExportadorPDF;
import pragma.model.GeneradorReportes;
import pragma.model.LogReportes;
import pragma.model.Reporte;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);

        pruebaManual();
    }

    private static void pruebaManual() {
        Reporte reporte = new Reporte.Builder()
                .conEncabezado("Ventas Q1")
                .conTabla("producto,total")
                .conPiePagina("Generado automáticamente")
                .build();
        System.out.println("1. Builder -> " + reporte.getSecciones().size() + " secciones (esperado: 3)");

        GeneradorReportes generador = new GeneradorReportes();
        ContadorReportes contador = new ContadorReportes();
        LogReportes log = new LogReportes();
        generador.agregarObservador(contador);
        generador.agregarObservador(log);

        System.out.println("2. Strategy PDF:\n" + generador.generar(reporte, new ExportadorPDF()));
        System.out.println("3. Strategy CSV:\n" + generador.generar(reporte, new ExportadorCSV()));

        System.out.println("4. Observer contador -> " + contador.getTotal() + " (esperado: 2)");
        System.out.println("5. Observer log -> " + log.getRegistros() + " (esperado: 2 registros)");
    }
}
