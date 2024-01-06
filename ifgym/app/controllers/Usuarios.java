package controllers;

import models.Endereco;
import models.Medida;
import models.Status;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Usuarios extends Controller {

 
    public static void form() {
        Usuario u = (Usuario) Cache.get("usuario");
        Cache.clear();
        render(u);
    }

    public static void perfil() {
        Usuario u = Usuario.find("nome = ?1", session.get("logado")).first();

        if (u == null) {
            Logins.logout();
        }

        if (u.medidas.isEmpty()) {
            flash.error("Informe suas medidas");
            Medidas.form();
        }

        Medida m = u.medidas.get(u.medidas.size() - 1);
        render(u, m);
    }

   
    public static void salvar(@Valid Usuario u) {
        Usuario teste = Usuario.find("nome = ?1 and email = ?2", u.nome, u.email).first();
        Usuario teste2 = Usuario.find("senha = ?1 and email = ?2", u.senha, u.email).first();

        if (teste != null||teste2 != null) {
            flash.error("Esse usuário já existe");
            form();
        }

        if (Validation.hasErrors()) {
            validation.keep();
            flash.error("Falha ao salvar usuário");
            Cache.set("usuario", u);
            form();
        }

        flash.success("Salvo com sucesso");
        u.save();

        perfil();
    }

    public static void salvarEndereco(@Valid Usuario u) {
    	Usuario usuarioLogado = Usuario.find("nome", session.get("logado")).first();
    	
    	Endereco end = new Endereco();
    	
    	end.bairro = u.endereco.bairro;
    	end.rua = u.endereco.rua;
    	end.cep = u.endereco.cep;
    	end.numero = u.endereco.numero;
    	end.cidade = u.endereco.cidade;
    	
    	end.save();
    	
    	usuarioLogado.endereco = end;

    	usuarioLogado.save();
        flash.success("Endereco salvo com sucesso!");
    	
        perfil();
    }
    
   
    public static void editar(long id) {
        Usuario u = Usuario.findById(id);
        renderTemplate("Usuarios/form.html", u);
    }

    
    public static void criarEndereco(long id) {
    	Usuario u = Usuario.findById(id);
    	
    	renderTemplate("Usuarios/form-endereco.html", u);
    }
    
    public static void excluir(long id) {
        Usuario u = Usuario.findById(id);
        flash.success("Removido com sucesso");
        u.status = Status.INATIVO;
        u.save();
        Logins.logout();
    }
}
