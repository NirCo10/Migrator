package migrator.migration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.DiscriminatorType.STRING;
import static javax.persistence.InheritanceType.SINGLE_TABLE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter(PROTECTED)
@Table(name = "migrations")
@RequiredArgsConstructor(access = PROTECTED)
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "version", discriminatorType = STRING, length = 4)
public abstract class Migration {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy =  "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    protected String id;

    @Column(name = "version", insertable = false, updatable = false, length = 4)
    protected final String version;

    @NotNull
    @Column(name = "description")
    protected final String description;

    @NotNull
    @Column(name = "up")
    @Getter(lazy = true)
    private final String up = this.up();

    @NotNull
    @Column(name = "down")
    @Getter(lazy = true)
    private final String down = this.down();

    @Setter(AccessLevel.PUBLIC)
    @Column(name = "did_run_last")
    protected boolean ranLast = false;

    protected abstract String up();
    protected abstract String down();
}
