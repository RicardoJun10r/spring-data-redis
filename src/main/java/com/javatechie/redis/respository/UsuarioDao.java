package com.javatechie.redis.respository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.javatechie.redis.entity.Post;
import com.javatechie.redis.entity.Usuario;

@Repository
public class UsuarioDao {
    
    public static final String HASH_KEY = "usuario";

    @Autowired
    private RedisTemplate template;

    private Random contador = new Random();

    public void criarUsuario(Usuario usuario) {
        List<Post> posts = new ArrayList<>();
        List<Usuario> amigos = new ArrayList<>();
        usuario.setPosts(posts);
        usuario.setAmigos(amigos);
        template.opsForHash().put(HASH_KEY, usuario.getEmail(), usuario);
    }

    public Usuario buscarUsuario(String email) {
        return (Usuario) template.opsForHash().get(HASH_KEY, email);
    }

    public void deletarUsuario(String email) {
        template.opsForHash().delete(HASH_KEY, email);
    }

    public void atualizarUsuario(Usuario usuario) {
        template.opsForHash().put(HASH_KEY, usuario.getEmail(), usuario);
    }

    public List<Usuario> listarUsuarios() {
        return template.opsForHash().values(HASH_KEY);
    }

    public void adicionarPost(String email, Post post) {
        Usuario usuario = buscarUsuario(email);
        if(usuario != null){
            post.setId(UUID.randomUUID().toString());
            if(post.getEhPrivado() == null) post.setEhPrivado(false);
            post.setRespostas("");
            usuario.getPosts().add(post);
            atualizarUsuario(usuario);
        }
    }

    private String generatePostId(String email) {
        return email + "Post" + this.contador.nextInt();
    }

    public void deletarPost(String email, String idPost) {
        Usuario usuario = buscarUsuario(email);
        if(usuario != null){
            usuario.getPosts().removeIf(post -> post.getId().equals(idPost));
            atualizarUsuario(usuario);
        }
    }

    public Post buscarPost(String email, String idPost){
        Usuario usuario = buscarUsuario(email);
        if(usuario != null){
            return usuario.getPosts().stream().filter(post -> post.getId().equals(idPost)).findFirst().get();
        }
        return null;
    }

    public List<Post> listarPosts(String email, Boolean filtro){
        Usuario usuario = buscarUsuario(email);
        if(usuario != null){
            return usuario.getPosts().stream().filter(post -> post.getEhPrivado().equals(filtro)).collect(Collectors.toList());
        }
        return null;
    }

    public Boolean comentar(String email, String post, String comentario){
        Usuario usuario = buscarUsuario(email);
        if(usuario != null){
            usuario.getPosts().forEach(p -> {
                if(p.getId().equals(post)){
                    String aux = p.getRespostas();
                    aux += "\n" + comentario;
                    p.setRespostas(aux);
                }
            });
            atualizarUsuario(usuario);
            return true;
        }
        return false;
    }

    public List<Usuario> listarAmigos(String email){
        Usuario usuario = buscarUsuario(email);
        if(usuario != null){
            return usuario.getAmigos();
        }
        return null;
    }

    public void adicionarAmigo(String meu_email, String email_amigo) {
        Usuario usuario = buscarUsuario(meu_email);
        if(usuario != null){
            Usuario amigo = buscarUsuario(email_amigo);
            if(amigo != null){
                usuario.getAmigos().add(amigo);
                atualizarUsuario(usuario);
            }
        }
    }

    public void removerAmigo(String meu_email, String email_amigo) {
        Usuario usuario = buscarUsuario(meu_email);
        if(usuario != null){
            Usuario amigo = buscarUsuario(email_amigo);
            if(amigo != null){
                usuario.getAmigos().removeIf(a -> a.equals(amigo));
                atualizarUsuario(usuario);
            }
        }
    }

}
