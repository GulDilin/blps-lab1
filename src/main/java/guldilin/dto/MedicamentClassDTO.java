package guldilin.dto;

import guldilin.model.MedicamentClass;

import javax.validation.constraints.NotBlank;

public class MedicamentClassDTO {

    private Long id;

    private Long parentClass;

    @NotBlank(message = "Title cannot be null")
    private String title;

    private String description;

    public MedicamentClassDTO() {
    }

    public MedicamentClassDTO(MedicamentClass medicamentClass) {
        this.id = medicamentClass.getId();
        if (medicamentClass.getParentClass() != null) {
            this.parentClass = medicamentClass.getParentClass().getId();
        }
        this.title = medicamentClass.getTitle();
        this.description = medicamentClass.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentClass() {
        return parentClass;
    }

    public void setParentClass(Long parentGroup) {
        this.parentClass = parentGroup;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
