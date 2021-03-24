package UN.Miage.covoit;

public class Trajet {
    String depart, destination, date, heure, placesMax, passagers, prix, commentaire, conducteur;

    public Trajet() {
    }

    public Trajet(String depart, String destination, String date, String heure,String placesMax, String passagers, String prix, String comment, String conducteur) {
        this.depart=depart;
        this.destination=destination;
        this.date=date;
        this.heure=heure;
        this.placesMax=placesMax;
        this.passagers=passagers;
        this.prix=prix;
        this.commentaire=comment;
        this.conducteur=conducteur;
    }


    public String getDepart() {
        return depart;
    }

    public void setDepart(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date =date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getPlacesMax() {
        return placesMax;
    }

    public void setPlacesMax(String placesMax) {
        this.placesMax = placesMax;
    }

    public String getPassagers() {
        return passagers;
    }

    public void setPassagers(String passagers) {
        this.passagers = passagers;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getConducteur() {
        return conducteur;
    }

    public void setConducteur(String conducteur) {
        this.conducteur = conducteur;
    }
}
