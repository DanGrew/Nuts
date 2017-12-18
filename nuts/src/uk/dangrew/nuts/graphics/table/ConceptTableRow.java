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
   
   public ConceptTableRow( TypeT food ) {
      this.concept = food;
   }//End Constructor
   
   public TypeT concept(){
      return concept;
   }//End Method
   
}//End Class
