package uk.dangrew.nuts.research;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.kode.concept.Concept;
import uk.dangrew.kode.concept.Properties;

public class ResearchArticle implements Concept {

   private final Properties properties;
   private final ObjectProperty< String > content;
   
   public ResearchArticle( String name ) {
      this( new Properties( name ) );
   }//End Constructor
   
   public ResearchArticle( String id, String name ) {
      this( new Properties( id, name ) );
   }//End Constructor
   
   ResearchArticle( Properties properties ) {
      this.properties = properties;
      this.content = new SimpleObjectProperty<>();
   }//End Constructor
   
   public ObjectProperty< String > content(){
      return content;
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
