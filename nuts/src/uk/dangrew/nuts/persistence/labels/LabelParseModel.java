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
import java.util.Optional;

import uk.dangrew.kode.concept.Concept;
import uk.dangrew.nuts.label.Label;
import uk.dangrew.nuts.label.LabelStore;
import uk.dangrew.nuts.label.Labelables;
import uk.dangrew.nuts.store.Database;

public class LabelParseModel {
   
   private final Database database;
   private final LabelStore labels;
   
   private String id;
   private String name;
   private final List< Concept > concepts;
   
   private String conceptId;
   
   LabelParseModel( Database database ) {
      this.database = database;
      this.labels = database.labels();
      this.concepts = new ArrayList<>();
   }//End Constructor
   
   void startLabel() {
      this.id = null;
      this.name = null;
      this.concepts.clear();;
      this.conceptId = null;
   }//End Method
   
   void finishLabel() {
      Label label = labels.get( id );
      if ( label == null ) {
         label = labels.createConcept( id, name );
      }
      label.concepts().clear();
      label.concepts().addAll( concepts );
   }//End Method
   
   void setId( String value ) {
      this.id = value;
   }//End Method
   
   void setName( String value ) {
      this.name = value;
   }//End Method
   
   void startConcept() {
      this.conceptId = null;
   }//End Method
   
   void finishConcept(){
      Concept concept = null;
      if ( !conceptId.isEmpty() ) {
         for ( Labelables labelable : Labelables.values() ) {
            concept = labelable.redirect( database ).get( conceptId );
            if ( concept != null ) {
               break;
            }
         }
      }
      Optional.ofNullable( concept ).ifPresent( concepts::add );
   }//End Method
   
   void setConceptId( String value ) {
      this.conceptId = value;
   }//End Method
   
}//End Class
