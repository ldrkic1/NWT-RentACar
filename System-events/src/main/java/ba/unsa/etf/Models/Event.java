package ba.unsa.etf.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String timeStamp;
    private String microservice;
    private String action;
    private String resource;
    private String response;
    private Long idKorisnik;


    public Event() {

    }

    public Event(String timeStamp, String microservice, String action, String resource, String response, Long idKorisnik) {
        this.timeStamp = timeStamp;
        this.microservice = microservice;
        this.action = action;
        this.resource = resource;
        this.response = response;
        this.idKorisnik=idKorisnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMicroservice() {
        return microservice;
    }

    public void setMicroservice(String microservice) {
        this.microservice = microservice;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Long getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(Long idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", timeStamp='" + timeStamp + '\'' +
                ", microservice='" + microservice + '\'' +
                ", action='" + action + '\'' +
                ", resource='" + resource + '\'' +
                ", response='" + response + '\'' +
                ", idKorisnik=" + idKorisnik +
                '}';
    }
}