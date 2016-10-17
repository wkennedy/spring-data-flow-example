package com.github.wkennedy.entities;

import javax.persistence.*;

@Entity
@Table(name = "engine_dim", schema = "galaxy_schema", catalog = "")
public class EngineDimEntity {
    private Integer id;
    private String code;
    private String displacement;
    private String fuel;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code", nullable = false, length = 32)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "displacement", nullable = false, length = 32)
    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    @Basic
    @Column(name = "fuel", nullable = false, length = 32)
    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EngineDimEntity that = (EngineDimEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (displacement != null ? !displacement.equals(that.displacement) : that.displacement != null) return false;
        if (fuel != null ? !fuel.equals(that.fuel) : that.fuel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (displacement != null ? displacement.hashCode() : 0);
        result = 31 * result + (fuel != null ? fuel.hashCode() : 0);
        return result;
    }
}
