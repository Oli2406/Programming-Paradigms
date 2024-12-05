import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Responsible(developer = "Ryan Foster")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Invariant {
  String value();
}
