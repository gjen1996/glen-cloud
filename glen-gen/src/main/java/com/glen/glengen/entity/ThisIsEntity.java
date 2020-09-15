package com.glen.glengen.entity;
/**
 * @author null
 * @create 2020-09-16 00:06:17
 * @Description null
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
@Table(name= "this_is_entity")
public class ThisIsEntity{
    /**
     * null
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    /**
     * null
     */
    private String username;

    /**
     * null
     */
    private String password;

    public ThisIsEntity() {
        super();
    }
}