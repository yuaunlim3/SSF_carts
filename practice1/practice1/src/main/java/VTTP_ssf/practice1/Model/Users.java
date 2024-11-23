package VTTP_ssf.practice1.Model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Users {

    @NotNull(message = "Name cannot be null") // Ensure name is not null
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
    private String id;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
}
