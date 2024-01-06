package controllers;

import play.mvc.Before;
import play.mvc.Controller;

public class Secure extends Controller {
    @Before(unless = { "Usuarios.form", "Usuarios.salvar" })
    static void checkAuthentification() {
        if (!session.contains("logado")) {
            Logins.form();
        }
    }
}
