import com.mongodb.*;

import static spark.Spark.*;

/**
 * Created by Ragonda on 20/01/2017.
 */
public class Application {

    private static DB mongo() throws Exception{
        String host = System.getenv("MONGODB_ADDON_HOST");
        if(host == null){
            MongoClient mongoClient = new MongoClient("localhost");
            return mongoClient.getDB("covoiturage");
        }
        int port = 27017;
        String dbname = System.getenv("MONGODB_ADDON_DB");
        String username = System.getenv("MONGODB_ADDON_USER");
        String password = System.getenv("MONGODB_ADDON_PASSWORD");
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
        MongoClient mongoClient = new MongoClient(new ServerAddress(host, port), mongoClientOptions);
        mongoClient.setWriteConcern(WriteConcern.SAFE);
        DB db = mongoClient.getDB(dbname);
        if(db.authenticate(username, password.toCharArray())){
            return db;
        }else{
            throw new RuntimeException("Not able to authenticate with MongoDB");
        }
    }

    public static void main(String args[]) throws Exception {
        port(8081);
        enableCORS("*", "*", "*");

        UtilisateurDao utilisateurDao = new UtilisateurDao(mongo());

        // AJOUTER UTILISATEUR ---------------------------------------------------
        post("/senregistrer", (req, res) -> {
            utilisateurDao.ajouterUtilisateur(new Utilisateur(req.queryParams("nom"),
                    req.queryParams("prenom"), req.queryParams("email"),
                    MotDePasseCryptage.cryptWithMD5(req.queryParams("motDePasse"))));
            res.status(201);
            return 1;
        }, new JsonTransformer());
        // --------------------------------------------------------------------------

        // CONNEXION -----------------------------------------------------------------
        post("/connexion", (req, res) -> {
            String token = req.headers("Authorization");
            System.out.println(token);
            if(token == null){
                token = "";
            }
           String result = utilisateurDao.connexion(req.queryParams("email"),
                   MotDePasseCryptage.cryptWithMD5(req.queryParams("motDePasse")),
                   token);
           if("connect".equals(result) || "updateToken".equals(result)){
               res.status(201);
               return result;
           }else{
               res.status(401);
               return result;
           }
        }, new JsonTransformer());
        // ----------------------------------------------------------------------------
    }

    // Enables CORS on requests. This method is an initialization method and should be called once.
    private static void enableCORS(final String origin, final String methods, final String headers) {
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }
}
