
package view.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

  public static Date asDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }

  public static Date asDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static LocalDate asLocalDate(Date date) {
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static LocalDateTime asLocalDateTime(Date date) {
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
  }
  
  public static int[] calcularDiferencaData(LocalDate dataAntiga) {
        if (dataAntiga == null) {
            return new int[]{0, 0};
        }
        
        LocalDate dataAtual = LocalDate.now();
        
        // O método between calcula a diferença: Data Antiga -> Data Atual
        Period periodo = Period.between(dataAntiga, dataAtual);
        
        int anos = periodo.getYears();
        int meses = periodo.getMonths(); // Isso retorna os meses restantes (0 a 11)
        
        return new int[]{anos, meses};
    }
}

