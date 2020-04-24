package cs4432.project2.query;

public class QueryFactory {
    public Query getQuery(String query) {
        query = query.toLowerCase();
        if (query.equals("create index on randomv")) {
            return Query.BUILD_INDEX;
        } else if (query.startsWith("select * from project2dataset where")) {
            query = query.replace("select * from project2dataset where", "");
            if (query.contains("!=")) {
                return Query.INEQUALITY;
            } else if (query.contains(">") && query.contains("<")) {
                return Query.RANGE;
            } else if (query.contains("=")) {
                return Query.EQUALITY;
            }
        }

        return null;
    }
}
