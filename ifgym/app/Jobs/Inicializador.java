package Jobs;

import models.Usuario;  
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Inicializador extends Job {

    @Override
    public void doJob() throws Exception {
        if (Usuario.count() == 0) {
            Usuario u = new Usuario();
            u.nome = "teste";
            u.email = "teste@teste.com";
            u.senha = "teste";
            u.save();
        }
    }
}
