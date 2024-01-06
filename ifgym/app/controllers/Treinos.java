package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Exercicio;
import models.Treino;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Treinos extends Controller {

   
    public static void form() {
        Treino t = (Treino) Cache.get("treino");
        Cache.clear();
        render(t);
    }
  
    public static void salvar(@Valid Treino t) {
        Treino teste = Treino.find("nome = ?1 and diaDaSemana = ?2", t.nome, t.diaDaSemana).first();
        if (Validation.hasErrors()) {
            validation.keep();
            flash.error("Falha ao salvar treino");
            Cache.set("treino", t);
            form();
        }
        if (teste != null) {
            flash.error("Treino duplicado!");
            form();
        }

        t.save();

        Usuario u = Usuario.find("nome", session.get("logado")).first();
        u.treinos.add(t);
        u.save();
        flash.success("Treino salvo com sucesso!");
        listar();
    }


    public static void listar() {
        Usuario u = Usuario.find("nome", session.get("logado")).first();
        List<Treino> listaDeTreinos = u.treinos;

        if (listaDeTreinos.isEmpty()) {
            flash.error("Registre um treino primeiro");
            form();
        }

        render(listaDeTreinos);
    }

    
    public static void formExer(Treino t) {
        Exercicio ex = (Exercicio) Cache.get("exercicio");
        Cache.clear();

        Treino treino = Treino.find("nome = ?1 and diaDaSemana = ?2", t.nome, t.diaDaSemana).first();
        Cache.set("treino", treino);

        render(ex);
    }

    public static void salvarExercicio(@Valid Exercicio ex) {
        if (Validation.hasErrors()) {
            Cache.clear();
            validation.keep();
            flash.error("Falha ao adcionar exercício, tente denovo");
            Cache.set("exercicio", ex);
            listar();
        }
        ex.save();

        Treino treino = (Treino) Cache.get("treino");
        Treino t = Treino.find("nome = ?1 and diaDaSemana = ?2", treino.nome, treino.diaDaSemana).first();
        Cache.clear();
        t.exercicios.add(ex);
        t.save();

        listar();

    }

    public static void detalhar(long id) {
        Treino t = Treino.findById(id);

        if (t.exercicios.isEmpty()) {
            flash.error("Nenhum exercicio cadastrado ainda");
            listar();
        }

        List<Exercicio> exercicios = new ArrayList();
        for (int i = 0; i < t.exercicios.size(); i++) {
            exercicios.add(t.exercicios.get(i));
        }

        render(exercicios, t);
    }

    public static void removerExer(long id, Treino t) {
        Exercicio ex = Exercicio.findById(id);
        Treino treino = Treino.find("nome = ?1 and diaDaSemana = ?2", t.nome, t.diaDaSemana).first();
        treino.exercicios.remove(ex);
        treino.save();
        ex.delete();

        flash.success("Exercício removido com sucesso");
        listar();

    }

    public static void remover(Long id) {
        Treino t = Treino.findById(id);
        Usuario u = Usuario.find("nome=?1", session.get("logado")).first();
        u.treinos.remove(t);
        u.save();
        t.delete();

        listar();
    }

}
