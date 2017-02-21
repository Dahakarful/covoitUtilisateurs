import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import sun.net.www.content.audio.basic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Ragonda on 20/01/2017.
 */
public class Utilisateur {

    private String nom;
    private String prenom;
    private String email;
//    private String id;
    private String motDePasse;
    private String token;
    private Date tokenExpire;

    public Utilisateur(BasicDBObject basicDBObject){
//        this.id = ((ObjectId) basicDBObject.get("_id")).toString();
        this.nom = basicDBObject.getString("nom");
        this.prenom = basicDBObject.getString("prenom");
        this.email = basicDBObject.getString("email");
        this.motDePasse = basicDBObject.getString("motDePasse");
        this.token = basicDBObject.getString("token");
        this.tokenExpire = basicDBObject.getDate("tokenExpire");
    }

    public Utilisateur(String nom, String prenom, String email, String motDePasse){
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
//        this.id = UUID.randomUUID().toString();
        this.motDePasse = motDePasse;
        this.token = "";
        this.tokenExpire = null;
    }

    public Utilisateur(String nom, String prenom, String email, String motDePasse, String token, Date tokenExpire){
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.token = token;
        this.tokenExpire = tokenExpire;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getId() {
//        return id;
//    }

//    public void setId(String id) {
//        this.id = id;
//    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setToken() {
        this.token = UUID.randomUUID().toString();
    }

    public Date getTokenExpire() {
        return tokenExpire;
    }

    public void setTokenExpire(Date tokenExpire) {
        this.tokenExpire = tokenExpire;
    }

    public void setTokenExpire() {
        Calendar cal = Utils.getCalendar();
        cal.add(Calendar.HOUR_OF_DAY, 1);
        this.tokenExpire = cal.getTime();
    }
}
