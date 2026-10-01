package pragma.repository;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;

@Entity
public class CitaEntity {
    @Id
    private String id;
    private String doctorId;
    private String horario;

    public CitaEntity() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
