import com.mongodb.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ragonda on 20/01/2017.
 */
public class UtilisateurDao {

    static List<Utilisateur> utilisateurs = new LinkedList<>();
    private final DB db;
    private final DBCollection collection;

    public UtilisateurDao(DB db){
        this.db = db;
        this.collection = db.getCollection("utilisateurs");
    }

    public List<Utilisateur> listerUtilisateurs(){
        List<Utilisateur> utilisateurs = new ArrayList<>();
        DBCursor dbCursor = collection.find();
        while(dbCursor.hasNext()){
            DBObject dbObject = dbCursor.next();
            utilisateurs.add(new Utilisateur((BasicDBObject) dbObject));
        }
        return utilisateurs;
    }

    public void ajouterUtilisateur(Utilisateur utilisateur){
        DBObject doc = new BasicDBObject("nom", utilisateur.getNom()).append("prenom", utilisateur.getPrenom()).append("email", utilisateur.getEmail())
                .append("motDePasse", utilisateur.getMotDePasse());
        collection.insert(doc);
        utilisateur.setId(doc.get("_id").toString());
    }

    public boolean connexion(String email, String motDePasse){
        boolean connecte = false;
        DBObject doc = new BasicDBObject("email", email);
        DBObject result = collection.findOne(doc);
        if(result != null) {
            Utilisateur utilisateur = (Utilisateur) result;
            if (utilisateur.getMotDePasse().equals(motDePasse)) {
                if(utilisateur.getToken() == "" || (utilisateur.getToken() != "" && utilisateur.getTokenExpire().before(Utils.getCalendar().getTime()))) {
                    utilisateur.setToken();
                    utilisateur.setTokenExpire();
                }
                connecte = true;
            }
        }
        return connecte;
    }

    public void supprimerUtilisateur(Utilisateur utilisateur){
        DBObject doc = (DBObject) utilisateur;
        collection.remove(doc);
    }

    public Utilisateur chercherUtilisateur(String nom){
        DBObject doc = new BasicDBObject("nom", nom);
        Utilisateur utilisateur = (Utilisateur) collection.findOne(doc);
        return utilisateur;
    }
}