package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Endereco extends Model {
	
    @Required
    public String rua;

    @Required
    @Email
    public String bairro;

    @Required
    public String cep;

    @Required
    public String cidade;
    
    @Required
    public String numero; 

}
