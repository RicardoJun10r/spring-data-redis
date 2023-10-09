package com.javatechie.redis.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@RedisHash("posts")
public class Post implements Serializable {

    @Id
    private String id;
    
    @JsonIgnore
    private Usuario usuario;
    private String mensagem;
    private List<String> respostas;
    private Boolean ehPrivado;

}
