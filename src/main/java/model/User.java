package model;

import jakarta.persistence.*;

@Entity
@Table(name = "User")
public class User {

    @Id
    @Column(name = "login")
    private String login;
    @Column(name = "pass")
    private String pass;
    @Column(name = "nom")
    private String nom;
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "role")
    private String role; // Ajout du champ role

    public User() {
        super();
    }

    public User(String login, String pass, String nom, String prenom, String role) {
        super();
        this.login = login;
        this.pass = pass;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role; // Initialisation du champ role
    }

    // Getters et setters pour le champ role

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Autres getters et setters...

    public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Override
    public String toString() {
        return "<tr><td>" + login + "</td><td>" + nom + "</td><td>" + prenom + "</td></tr>";
    }
}
