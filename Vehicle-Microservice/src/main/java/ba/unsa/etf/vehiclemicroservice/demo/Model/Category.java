package ba.unsa.etf.vehiclemicroservice.demo.Model;

import ba.unsa.etf.vehiclemicroservice.demo.Model.Vehicle;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="Category name is required")
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Vehicle> vehicle;

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

