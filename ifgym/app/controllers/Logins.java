package controllers;

import models.Usuario;
import play.mvc.Controller;

public class Logins extends Controller {


    public static void form() {
        render();
    }


    public static void login(String email, String senha) {
        Usuario u = Usuario.find("email = ?1 and senha = ?2", email, senha).first();

        if (u == null) {
            flash.error("Senha e/ou email incorreto(s)");
            form();
        }

        session.put("logado", u.nome);

        flash.success("Bem vindo " + u.nome);
        Treinos.listar();
    }

    
    public static void logout() {
        if (!session.contains("logado")) {
            flash.error("Nínguem logado");
        } else {
            flash.success(session.get("logado") + " deslogou");
        }

        session.clear();
        form();
    }

}
