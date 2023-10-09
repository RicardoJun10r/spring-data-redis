package com.javatechie.redis.respository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.javatechie.redis.entity.Post;

@Repository
public class PostDao {
    
    public static final String HASH_KEY = "posts";

    @Autowired
    private RedisTemplate template;

    @Autowired
    private RedisKeyValueTemplate redisKeyValueTemplate;

    public void criarPost(Post post) {
        template.opsForHash().put(HASH_KEY, post.getId(), post);
    }

    public void comentar(String comentario, String idPost){
        Post post = buscarPost(idPost);
        if(post != null){
            post.getRespostas().add(comentario);
            this.template.opsForHash().put(this.HASH_KEY, idPost, post);
        }
    }

    public Post buscarPost(String idPost) {
        return (Post) template.opsForHash().get(HASH_KEY, idPost);
    }

    public List<Post> listarPosts() {
        List<Post> posts = template.opsForHash().values(HASH_KEY);
        return posts.stream().filter(p -> p.getEhPrivado() == false).collect(Collectors.toList());
    }

    public Boolean deletarPost(String idPost) {
        Post post = buscarPost(idPost);
        if(post != null){
            template.opsForHash().delete(HASH_KEY, idPost);
            return true;
        }
        return false;
    }

}
