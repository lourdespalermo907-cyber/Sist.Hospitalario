package Entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface CitaService {
    Cita programarCita(Paciente p, Medico m, Sala s, LocalDateTime fh, BigDecimal costo) throws CitaException;
    List<Cita> getCitasPorPaciente(Paciente p);
}
