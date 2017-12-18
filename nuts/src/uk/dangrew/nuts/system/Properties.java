package uk.dangrew.nuts.system;

import java.util.UUID;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Properties {

   private final String id;
   private final ObjectProperty< String > nameProperty;
   
   public Properties( String name ) {
      this( UUID.randomUUID().toString(), name );
   }//End Constructor
   
   public Properties( String id, String name ) {
      this.id = id;
      this.nameProperty = new SimpleObjectProperty<>( name );
   }//End Constructor
   
   /**
    * Access to the unique id.
    * @return the id.
    */
   public String id(){
      return id;
   }//End Method

   /**
    * Access to the name property.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< String > nameProperty() {
      return nameProperty;
   }//End Method
   
}//End Class
