import com.mongodb.*;
import com.sun.javafx.embed.EmbeddedSceneDSInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ragonda on 20/01/2017.
 */
public class UtilisateurDao {

    private final DB db;
    private final DBCollection collection;

    /**
     * Constructeur initialise la collection utilisateur
     *
     * @param db DB de mongoDB
     */
    public UtilisateurDao(DB db){
        this.db = db;
        this.collection = db.getCollection("utilisateurs");
    }

    /**
     * Ajouter un utilisateur
     * @param utilisateur Utilisateur à ajouter
     */
    public void ajouterUtilisateur(Utilisateur utilisateur){
        DBObject doc = new BasicDBObject("nom", utilisateur.getNom()).append("prenom", utilisateur.getPrenom()).append("email", utilisateur.getEmail())
                .append("motDePasse", utilisateur.getMotDePasse()).append("token", null).append("tokenExpire", null);
        collection.insert(doc);
    }

    /**
     * Connexion à l'application
     *
     * @param email de l'utilisateur
     * @param motDePasse de l'utilisateur
     * @param token de l'utilisateur
     * @return
     */
    public String connexion(String email, String motDePasse, String token) throws ParseException {
        String res = "notConnect";
        DBObject doc = new BasicDBObject("email", email);
        DBObject result = collection.findOne(doc);
        // Si l'utilisateur existe dans la base de données
        if (result != null) {
            Utilisateur utilisateur = new Utilisateur((BasicDBObject) result);
            // Vérification que le token ne soit pas vide et que sa date d'éxpiration soit
            // plus petite que la date à cette instant T
            if ((utilisateur.getToken() != null) && utilisateur.getToken().equals(token)) {
                if (Utils.getCalendar().getTime().before(utilisateur.getTokenExpire())) {
                    res = token;
                }
            } else {
                // Sinon vérification du mot de passe fournit
                if (utilisateur.getMotDePasse().equals(motDePasse)) {
                    utilisateur.setToken();
                    utilisateur.setTokenExpire();
                    result.put("token", utilisateur.getToken());
                    result.put("tokenExpire", utilisateur.getTokenExpire());
                    collection.update(new BasicDBObject("_id", ((BasicDBObject) result).getObjectId("_id")), result);
                    res = utilisateur.getToken();
                }
            }
        }
        return res;
    }

    public Utilisateur getUtilisateur(String email){
        Utilisateur utilisateur = null;
        try {
            System.out.println(email);
            BasicDBObject query = new BasicDBObject();
            query.put("email", email);
            DBObject object = collection.findOne(query);
            System.out.println(object);
            BasicDBObject utilisateurOb = (BasicDBObject) object;
            utilisateur = new Utilisateur(utilisateurOb);
            System.out.println(utilisateur);
        }catch(Exception e){
            System.out.println(e);
        }
        return utilisateur;
    }
}