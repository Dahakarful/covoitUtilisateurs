import com.mongodb.*;

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
        System.out.println(utilisateur.getToken() + " " + utilisateur.getTokenExpire());
        DBObject doc = new BasicDBObject("nom", utilisateur.getNom()).append("prenom", utilisateur.getPrenom()).append("email", utilisateur.getEmail())
                .append("motDePasse", utilisateur.getMotDePasse());
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
        System.out.println("Email: "+email+ " motDePasse: "+motDePasse+" token: "+token);
        System.out.println(result);
        // Si l'utilisateur existe dans la base de données
        if (result != null) {
            Utilisateur utilisateur = new Utilisateur((BasicDBObject) result);
            System.out.println(utilisateur);
            // Vérification que le token ne soit pas vide et que sa date d'éxpiration soit
            // plus petite que la date à cette instant T
            if ((utilisateur.getToken() != null) && utilisateur.getToken().equals(token)) {
                System.out.println("3");
                if (utilisateur.getTokenExpire().before(Utils.getCalendar().getTime())) {
                    res = "connect";
                }
                System.out.println("ok");
            } else {
                // Sinon vérification du mot de passe fournit
                if (utilisateur.getMotDePasse().equals(motDePasse)) {
                    System.out.println("motDePasse.equals(MotDepasse)");
                    utilisateur.setToken();
                    utilisateur.setTokenExpire();
                    DBObject oldResult = result;
                    result.put("token", utilisateur.getToken());
                    result.put("tokenExpire", utilisateur.getTokenExpire());
                    System.out.println(result.get("token").toString());
                    collection.update(oldResult, result);
                    res = "updateToken";
                }
            }
        }
        System.out.println("Result: "+res);
        return res;
    }
}