import com.mongodb.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ragonda on 20/01/2017.
 */
public class UtilisateursDao {

    static List<Utilisateurs> utilisateurss = new LinkedList<>();
    private final DB db;
    private final DBCollection collection;

    public UtilisateursDao(DB db){
        this.db = db;
        this.collection = db.getCollection("utilisateurs");
    }

    public List<Utilisateurs> listerTous(){
        List<Utilisateurs> utilisateurs = new ArrayList<>();
        DBCursor dbCursor = collection.find();
        while(dbCursor.hasNext()){
            DBObject dbObject = dbCursor.next();
            utilisateurs.add(new Utilisateurs((BasicDBObject) dbObject));
        }
        return utilisateurs;
    }

    public void ajouterUtilisateur(){

    }
}
