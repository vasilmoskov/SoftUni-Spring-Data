package com.example.football.models.dto;

import com.example.football.models.entity.PositionEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerSeedDto {
    @XmlElement(name = "first-name")
    @Size(min = 3)
    private String firstName;

    @XmlElement(name = "last-name")
    @Size(min = 3)
    private String lastName;

    @XmlElement
    @Email
    private String email;

    @XmlElement(name = "birth-date")
    private String birthDate;

    @XmlElement
    private PositionEnum position;

    @XmlElement
    private NameDto town;

    @XmlElement
    private NameDto team;

    @XmlElement
    private PlayerStatIdDto stat;

    public PlayerSeedDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public PlayerSeedDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PlayerSeedDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public PlayerSeedDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public PlayerSeedDto setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public PositionEnum getPosition() {
        return position;
    }

    public PlayerSeedDto setPosition(PositionEnum position) {
        this.position = position;
        return this;
    }

    public NameDto getTown() {
        return town;
    }

    public PlayerSeedDto setTown(NameDto town) {
        this.town = town;
        return this;
    }

    public NameDto getTeam() {
        return team;
    }

    public PlayerSeedDto setTeam(NameDto team) {
        this.team = team;
        return this;
    }

    public PlayerStatIdDto getStat() {
        return stat;
    }

    public PlayerSeedDto setStat(PlayerStatIdDto stat) {
        this.stat = stat;
        return this;
    }
}
