/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.labels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import uk.dangrew.kode.concept.Concept;
import uk.dangrew.nuts.label.Label;
import uk.dangrew.nuts.label.LabelStore;

public class LabelWriteModel {
   
   private final LabelStore labels;
   private final List< Label > labelBuffer;
   private final List< Concept > conceptBuffer;
   private Label currentLabel;
   private Concept currentConcept; 
   
   LabelWriteModel( LabelStore labels ) {
      this.labels = labels;
      this.labelBuffer = new ArrayList<>();
      this.conceptBuffer = new ArrayList<>();
   }//End Constructor
   
   Integer getNumberOfLabels( String key ){
      labelBuffer.clear();
      labelBuffer.addAll( labels.objectList() );
      return labelBuffer.size();
   }//End Method
   
   Integer getNumberOfConcepts( String key ){
      return labelBuffer.remove( 0 ).concepts().size();
   }//End Method
   
   void startWritingLabels() {
      labelBuffer.clear();
      labelBuffer.addAll( labels.objectList() );
   }//End Method
   
   void startWritingLabel() {
      if ( labelBuffer.isEmpty() ) {
         return;
      }
      this.currentLabel = labelBuffer.remove( 0 );
      this.conceptBuffer.clear();
      this.conceptBuffer.addAll( 
               currentLabel.concepts().stream()
                  .filter( Objects::nonNull )
                  .collect( Collectors.toList() ) 
      );
   }//End Method

   String getId() {
      return currentLabel.properties().id();
   }//End Method

   String getName() {
      return currentLabel.properties().nameProperty().get();
   }//End Method
   
   void startWritingConcepts() {
      if ( conceptBuffer.isEmpty() ) {
         return;
      }
      this.currentConcept = conceptBuffer.remove( 0 );
   }//End Method
   
   String getConceptId() {
      return currentConcept.properties().id();
   }//End Method
   
}//End Class
