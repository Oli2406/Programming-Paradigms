import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Responsible(developer = "Oliver Kastner")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface HistoryConstraint {
    String value();
}
