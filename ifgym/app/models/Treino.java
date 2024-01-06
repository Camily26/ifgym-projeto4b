package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Treino extends Model {

    @Required
    public String nome;

    @Required
    public String diaDaSemana;

    @OneToMany
    public List<Exercicio> exercicios;

}


