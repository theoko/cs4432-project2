package cs4432.project2.query;

public class Query {
    public Query(String query) {

    }

    public static Query parseQuery(String query) {
        return new Query(query);
    }
}
