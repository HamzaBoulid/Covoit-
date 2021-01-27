package UN.Miage.covoit;

public class Trajet {
    String depart, destination, date, heure, passagersMax, passagersReserves, prix, commentaire, conducteur;

    public Trajet() {
    }

    public Trajet(String depart, String destination, String date, String heure,String passagersMax, String passagersReserves, String prix, String comment, String conducteur) {
        this.depart=depart;
        this.destination=destination;
        this.date=date;
        this.heure=heure;
        this.passagersMax=passagersMax;
        this.passagersReserves=passagersReserves;
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

    public String getPassagersMax() {
        return passagersMax;
    }

    public void setPassagersMax(String passagersMax) {
        this.passagersMax = passagersMax;
    }

    public String getPassagersReserves() {
        return passagersReserves;
    }

    public void setPassagersReserves(String passagersReserves) {
        this.passagersReserves = passagersReserves;
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
