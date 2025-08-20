package model.classes;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Classe dos animais que estão aguardando atendimento
 * @author juliano
 */
public class SalaEspera {
    private int id;
    private Pet pet;
    private LocalTime horarioChegada;
    private boolean agendado;
    private LocalTime horarioAgendado;
    private boolean urgencia;

    public SalaEspera(int id, Pet pet, LocalTime horarioChegada, boolean agendado, LocalTime horarioAgendado, boolean urgencia) {
        this.id = id;
        this.pet = pet;
        this.horarioChegada = horarioChegada;
        this.agendado = agendado;
        this.horarioAgendado = horarioAgendado;
        this.urgencia = urgencia;
    }

    public SalaEspera(Pet pet, LocalTime horarioChegada, boolean agendado, LocalTime horarioAgendado, boolean urgencia) {
        this.pet = pet;
        this.horarioChegada = horarioChegada;
        this.agendado = agendado;
        this.horarioAgendado = horarioAgendado;
        this.urgencia = urgencia;
    }

    public SalaEspera() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pet getPet() {
        return pet;
    }
    
    public Tutor getTutor(){
        return pet.getTutorPet();
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public LocalTime getHorarioChegada() {
        return horarioChegada;
    }

    public void setHorarioChegada(LocalTime horarioChegada) {
        this.horarioChegada = horarioChegada;
    }

    public boolean isAgendado() {
        return agendado;
    }

    public void setAgendado(boolean agendado) {
        this.agendado = agendado;
    }

    public LocalTime getHorarioAgendado() {
        return horarioAgendado;
    }

    public void setHorarioAgendado(LocalTime horarioAgendado) {
        this.horarioAgendado = horarioAgendado;
    }

    public boolean isUrgencia() {
        return urgencia;
    }

    public void setUrgencia(boolean urgencia) {
        this.urgencia = urgencia;
    }
    
    public Duration getTempoEspera(){
        Duration espera = Duration.between(horarioChegada, LocalTime.now());
        return espera;
    }
    
    public String getUrgencia(){
        if(urgencia){
            return "Sim";
        }else{
            return "Não";
        }
    }
    
    public String getAgendado(){
        if(agendado){
            return "Sim";
        }else{
            return "Não";
        }
    }

    @Override
    public String toString() {
        return "SalaEspera{" + "id=" + id + ", pet=" + pet + ", horarioChegada=" + horarioChegada + ", agendado=" + agendado + ", horarioAgendado=" + horarioAgendado + ", urgencia=" + urgencia + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SalaEspera other = (SalaEspera) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.pet, other.pet);
    }
    
}
