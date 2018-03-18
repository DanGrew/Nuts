package uk.dangrew.nuts.label;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.nuts.system.Concept;
import uk.dangrew.nuts.system.Properties;

public class Label implements Concept {

   private final Properties properties;
   private final ObservableList< Concept > concepts;
   
   public Label( String name ) {
      this( new Properties( name ) );
   }//End Constructor
   
   public Label( String id, String name ) {
      this( new Properties( id, name ) );
   }//End Constructor
   
   Label( Properties properties ) {
      this.properties = properties;
      this.concepts = FXCollections.observableArrayList();
   }//End Constructor
   
   public ObservableList< Concept > concepts(){
      return concepts;
   }//End Method

   @Override public Properties properties() {
      return properties;
   }//End Method

   @Override public Concept duplicate( String referenceId ) {
      return this;
   }//End Method
   
   @Override public String toString() {
      return properties.nameProperty().get();
   }//End Method

}//End Class
