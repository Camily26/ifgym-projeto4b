package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Medida;
import models.Treino;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Medidas extends Controller {

   
    public static void form() {
        Medida m = (Medida) Cache.get("medida");
        Cache.clear();
        render(m);
    }

    
    public static void salvar(@Valid Medida m) {
        if (Validation.hasErrors()) {
            validation.keep();
            flash.error("Falha ao salvar medida");
            Cache.set("medida", m);
            form();
        }
        m.save();

      
        Usuario u = Usuario.find("nome = ?1", session.get("logado")).first();
        u.medidas.add(m);
        u.save();
        Usuarios.perfil();
    }

    
    public static void historico() {
        Usuario u = Usuario.find("nome = ?1", session.get("logado")).first();

        List<Medida> inversa = new ArrayList<>();
        for (int i = u.medidas.size() - 1; i >= 0; i--) {
            inversa.add(u.medidas.get(i));
        }

        List<Medida> listaMedidas = inversa;
        render(listaMedidas);
    }

    public static void editar(long id) {
        Medida m = Medida.findById(id);
        Usuario u = Usuario.find("nome = ?1", session.get("logado")).first();
        u.medidas.add(m);
        u.save();
        renderTemplate("Medidas/form.html", m);
    }

}
