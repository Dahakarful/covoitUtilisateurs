import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import sun.net.www.content.audio.basic;

import java.util.UUID;

/**
 * Created by Ragonda on 20/01/2017.
 */
public class Utilisateurs {

    private String nom;
    private String prenom;
    private String email;
    private String id;

    public Utilisateurs(BasicDBObject basicDBObject){
        this.id = ((ObjectId) basicDBObject.get("_id")).toString();
        this.nom = basicDBObject.getString("nom");
        this.prenom = basicDBObject.getString("prenom");
        this.email = basicDBObject.getString("email");
    }

    public Utilisateurs(String nom, String prenom, String email){
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.id = UUID.randomUUID().toString();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
