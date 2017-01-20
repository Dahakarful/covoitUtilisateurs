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

    public List<Utilisateur> listerTous(){
        List<Utilisateur> utilisateurs = new ArrayList<>();
        DBCursor dbCursor = collection.find();
        while(dbCursor.hasNext()){
            DBObject dbObject = dbCursor.next();
            utilisateurs.add(new Utilisateur((BasicDBObject) dbObject));
        }
        return utilisateurs;
    }

    public void ajouterUtilisateur(Utilisateur utilisateur){
        DBObject doc = new BasicDBObject("nom", utilisateur.getNom()).append("prenom", utilisateur.getPrenom()).append("email", utilisateur.getEmail());
        collection.insert(doc);
        utilisateur.setId(doc.get("_id").toString());
    }
}
