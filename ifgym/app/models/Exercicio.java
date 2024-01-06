package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Exercicio extends Model {

    @Required
    public String nome;
    @Required
    public String descricao;
    @Required
    @Min(1)
    public Integer series;
    @Required
    @Min(1)
    public Integer repeticoes;
    @Required
    @Min(1)
    public Double duracao;

}
