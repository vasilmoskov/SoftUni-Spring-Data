package com.example.football.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "players")
public class PlayerEntity extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private PositionEnum position;
    private TownEntity town;
    private TeamEntity team;
    private StatEntity stat;

    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public PlayerEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    public PlayerEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public PlayerEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    @Column(nullable = false)
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public PlayerEntity setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    @Column(nullable = false)
    public PositionEnum getPosition() {
        return position;
    }

    public PlayerEntity setPosition(PositionEnum position) {
        this.position = position;
        return this;
    }

    @ManyToOne(optional = false)
    public TownEntity getTown() {
        return town;
    }

    public PlayerEntity setTown(TownEntity town) {
        this.town = town;
        return this;
    }

    @ManyToOne(optional = false)
    public TeamEntity getTeam() {
        return team;
    }

    public PlayerEntity setTeam(TeamEntity team) {
        this.team = team;
        return this;
    }

    @ManyToOne(optional = false)
    public StatEntity getStat() {
        return stat;
    }

    public PlayerEntity setStat(StatEntity stat) {
        this.stat = stat;
        return this;
    }

    @Override
    public String toString() {
        return String.format("""
                        Player - %s %s
                        \tPosition - %s
                        \tTeam - %s
                        \tStadium - %s""",
                getFirstName(), getLastName(), getPosition().name(), getTeam().getName(), getTeam().getStadiumName());
    }
}
