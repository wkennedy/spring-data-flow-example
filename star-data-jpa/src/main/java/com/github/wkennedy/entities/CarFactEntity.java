package com.github.wkennedy.entities;

import javax.persistence.*;

@Entity
@Table(name = "car_fact", schema = "galaxy_schema", catalog = "")
public class CarFactEntity {
    private Integer id;

    private EngineDimEntity engineDimEntity;
    private MakeDimEntity makeDimEntity;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "engine_dim", referencedColumnName = "id", nullable = false)
    public EngineDimEntity getEngine() {
        return engineDimEntity;
    }

    public void setEngine(EngineDimEntity engine) {
        this.engineDimEntity = engine;
    }

    @OneToOne
    @JoinColumn(name = "make_dim", referencedColumnName = "id", nullable = false)
    public MakeDimEntity getMake() {
        return makeDimEntity;
    }

    public void setMake(MakeDimEntity make) {
        this.makeDimEntity = make;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarFactEntity that = (CarFactEntity) o;

        if (!id.equals(that.id)) return false;
        if (!engineDimEntity.equals(that.engineDimEntity)) return false;
        return makeDimEntity.equals(that.makeDimEntity);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + engineDimEntity.hashCode();
        result = 31 * result + makeDimEntity.hashCode();
        return result;
    }
}
