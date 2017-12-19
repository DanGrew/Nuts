/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table;

public class ConceptTableRow< TypeT > {

   private final TypeT concept;
   
   public ConceptTableRow( TypeT concept ) {
      this.concept = concept;
   }//End Constructor
   
   public TypeT concept(){
      return concept;
   }//End Method
   
}//End Class
