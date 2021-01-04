package UN.Miage.covoit;

public class Trajet {String depart, destination, date, heure, passagers, prix, commentaire;

    public Trajet() {
    }

    public Trajet(String depart, String destination, String date, String heure, String passagers, String prix, String comment) {
        this.depart=depart;
        this.destination=destination;
        this.date=date;
        this.heure=heure;
        this.passagers=passagers;
        this.prix=prix;
        this.commentaire=comment;
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


}
