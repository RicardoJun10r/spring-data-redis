package com.javatechie.redis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javatechie.redis.entity.Post;
import com.javatechie.redis.entity.Usuario;
import com.javatechie.redis.respository.UsuarioDao;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioDao usuarioDao;

    // @Autowired
    // private PostDao postDao;

    @PostMapping
    public void save(@RequestBody Usuario usuario) {
        usuarioDao.criarUsuario(usuario);
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioDao.listarUsuarios();
    }

    @GetMapping("/{email}")
    public Usuario buscar(@PathVariable String email) {
        return usuarioDao.buscarUsuario(email);
    }
    
    @DeleteMapping("/{email}")
    public void remove(@PathVariable String email)   {
    	usuarioDao.deletarUsuario(email);
	}

    @PostMapping("/post/{email}")
    public void criarPost(@PathVariable String email, @RequestBody Post post) {
        usuarioDao.adicionarPost(email, post);
    }

    @DeleteMapping("/post/{email}/{post}")
    public void deletarPost(@PathVariable String email, @PathVariable String post) {
        usuarioDao.deletarPost(email, post);
    }

    @GetMapping("/post/{email}/{post}")
    public Post buscarPost(@PathVariable String email, @PathVariable String post) {
        return usuarioDao.buscarPost(email, post);
    }

    @GetMapping("/post/{email}")
    public List<Post> listarPost(@PathVariable String email, @RequestParam(defaultValue = "false") Boolean filtro) {
        return usuarioDao.listarPosts(email, filtro);
    }

    @PostMapping("/post/{email}/{post}")
    public Boolean comentar(@PathVariable String email, @PathVariable String post, @RequestBody String respostas) {
        return this.usuarioDao.comentar(email, post, respostas);
    }

    @GetMapping("/amigos/{email}")
    public List<Usuario> listarAmigos(@PathVariable String email) {
        return usuarioDao.listarAmigos(email);
    }

    @PutMapping("/amigos/add/{email}/{amigo}")
    public void adicionarAmigo(@PathVariable String email, @PathVariable String amigo) {
        usuarioDao.adicionarAmigo(email, amigo);
    }

    @PutMapping("/amigos/del/{email}/{amigo}")
    public void removerAmigo(@PathVariable String email, @PathVariable String amigo) {
        usuarioDao.removerAmigo(email, amigo);
    }

    // @GetMapping("/post")
    // public List<Post> listarTodosPosts(){
    //     return this.postDao.listarPosts();
    // }

}
