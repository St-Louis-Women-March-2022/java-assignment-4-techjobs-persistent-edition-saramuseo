package org.launchcode.techjobs.persistent.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Skill extends AbstractEntity {

   // @NotBlank(message = "Description is required.")
    @Size(min= 3, max=100, message = "Description needs to be at least 3 characters long and cannot exceed 255 characters.")
    private String description;

    public Skill() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}