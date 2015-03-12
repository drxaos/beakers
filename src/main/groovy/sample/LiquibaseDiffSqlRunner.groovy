package sample

public class LiquibaseDiffSqlRunner {
    public static void main(String[] args) throws Exception {
        SamplesRunner.main(["liquibase", "diff", "sql"] as String[])
    }
}
