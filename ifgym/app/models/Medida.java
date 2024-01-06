package models;

import java.time.LocalDate;
import javax.persistence.Entity;
import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Medida extends Model {
    @Required
    @Min(1)
    public Integer peso;

    @Required
    @Min(1)
    public Integer altura;

    @Required
    @Min(1)
    public Double ombro;

    @Required
    @Min(1)
    public Double braco;

    @Required
    @Min(1)
    public Double antebraco;

    @Required
    @Min(1)
    public Double busto;

    @Required
    @Min(1)
    public Double abdomen;

    @Required
    @Min(1)
    public Double quadril;

    @Required
    @Min(1)
    public Double cintura;

    @Required
    @Min(1)
    public Double gluteo;

    @Required
    @Min(1)
    public Double quadriceps;

    @Required
    @Min(1)
    public Double panturrilha;


    // Cria um registro da medida, ou seja, o dia em que ela foi feita
    LocalDate dataDeCriacao;

    public Medida() {
        dataDeCriacao = LocalDate.now();
    }

}
