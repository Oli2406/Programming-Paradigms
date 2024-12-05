import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtility {
  
  public static void analyzeClasses(Class<?>... classes) {
    Map<String, Integer> developerResponsibilityCounts = new HashMap<>();
    
    for (Class<?> clazz : classes) {
      System.out.println("Class: " + clazz.getName());
      
      // Analyze @Responsible annotation
      if (clazz.isAnnotationPresent(Responsible.class)) {
        Responsible responsible = clazz.getAnnotation(Responsible.class);
        String developer = responsible.developer();
        developerResponsibilityCounts.put(developer, developerResponsibilityCounts.getOrDefault(developer, 0) + 1);
        System.out.println("  Responsible: " + developer);
      }
      
      // Analyze @Invariant annotation
      if (clazz.isAnnotationPresent(Invariant.class)) {
        Invariant invariant = clazz.getAnnotation(Invariant.class);
        System.out.println("  Invariant: " + invariant.value());
      }
      
      // List constructors and methods
      System.out.println("  Constructors:");
      for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
        System.out.println("    " + constructor);
        if (constructor.isAnnotationPresent(Precondition.class)) {
          Precondition pre = constructor.getAnnotation(Precondition.class);
          System.out.println("      Precondition: " + pre.value());
        }
        if (constructor.isAnnotationPresent(Postcondition.class)) {
          Postcondition post = constructor.getAnnotation(Postcondition.class);
          System.out.println("      Postcondition: " + post.value());
        }
      }
      
      System.out.println("  Methods:");
      for (Method method : clazz.getDeclaredMethods()) {
        System.out.println("    " + method);
        if (method.isAnnotationPresent(Precondition.class)) {
          Precondition pre = method.getAnnotation(Precondition.class);
          System.out.println("      Precondition: " + pre.value());
        }
        if (method.isAnnotationPresent(Postcondition.class)) {
          Postcondition post = method.getAnnotation(Postcondition.class);
          System.out.println("      Postcondition: " + post.value());
        }
      }
    }
    
    // Display responsibility summary
    System.out.println("\nDeveloper Responsibility Summary:");
    developerResponsibilityCounts.forEach((developer, count) -> {
      System.out.println("  " + developer + ": " + count + " class(es)");
    });
  }
}
