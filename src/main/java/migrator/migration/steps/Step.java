package migrator.migration.steps;

public interface Step {

    void up();
    void down();
}
