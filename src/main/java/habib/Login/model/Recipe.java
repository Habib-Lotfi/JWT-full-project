package habib.Login.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @ManyToOne
    private User user;

    private String image;

    private String description;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean vegetarian;

    private LocalDateTime createdAt;

//    @ManyToMany
//    @JoinTable(
//            name = "user_likes_recipe",
//            joinColumns = @JoinColumn(name = "recipe_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
//    private List<User> likes = new ArrayList<>();
    @ElementCollection
    private List<Long> likes = new ArrayList<>();

}
