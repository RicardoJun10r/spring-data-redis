package com.javatechie.redis.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@RedisHash("usuarios")
public class Usuario implements Serializable {
    
    @Id
    private String email;
    private String password;
    private List<Post> posts;
    private List<Usuario> amigos;

}
