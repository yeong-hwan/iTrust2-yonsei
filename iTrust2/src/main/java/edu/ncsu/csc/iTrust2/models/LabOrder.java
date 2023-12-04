package edu.ncsu.csc.iTrust2.models;
import java.time.LocalDate;


import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Convert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;

import com.google.gson.annotations.JsonAdapter;

import edu.ncsu.csc.iTrust2.adapters.LocalDateAdapter;


/* 
 * LabOrder.java for UC23
 * @author: Yewon Lim 
 * @date: 2023/12/04
 */

@Entity
public class LabOrder extends DomainObject{
    // dummy constructor
    public LabOrder() {
    }

    @Id 
    @GeneratedValue ( strategy = GenerationType.AUTO )
    private Long      id;

    @NotNull
    @Basic
    // Allows the field to show up nicely in the database
    @Convert ( converter = LocalDateConverter.class )
    @JsonAdapter ( LocalDateAdapter.class )
    private LocalDate orderDate;

    @NotNull
    @OneToOne ( cascade = CascadeType.ALL )
    @JoinColumn ( name = "labTest_id" , columnDefinition = "long" )
    private LabTest labTest;


    public Long getId () {
        return id;
    }

    public void setId ( final Long id ) {
        this.id = id;
    }

    public LocalDate getOrderDate () {
        return orderDate;
    }

    public void setOrderDate ( final LocalDate orderDate ) {
        this.orderDate = orderDate;
    }

    public LabTest getLabTest () {
        return labTest;
    }

    public void setLabTest ( final LabTest labTest ) {
        this.labTest = labTest;
    }

}
