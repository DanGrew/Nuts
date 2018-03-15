package uk.dangrew.nuts.apis.tesco.api.parsing;

import java.util.function.Function;

import javafx.beans.property.ObjectProperty;

public class ModelUpdater< UpdateTypeT > {
   
   private final UpdateTypeT updateFrom;
   
   public ModelUpdater( UpdateTypeT updateFrom ) {
      this.updateFrom = updateFrom;
   }//End Constructor
   
   public < TypeT > void set( Function< UpdateTypeT, ObjectProperty< TypeT > > propertyRetriever, UpdateTypeT modelToUpdate ) {
      propertyRetriever.apply( modelToUpdate ).set( propertyRetriever.apply( updateFrom ).get() );
   }//End Method

}//End Class
