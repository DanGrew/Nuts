/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.tools.dryweight;

/**
 * {@link DryWeightTool} provides the combination of properties and calculations for handling
 * the dry weight problem with calculating nutritional values.
 */
public class DryWeightTool {

   private final DryWeightProperties properties;
   
   /**
    * Constructs a new {@link DryWeightTool}.
    */
   public DryWeightTool(){
      this( new DryWeightProperties(), new DryWeightConverter() );
   }//End Constructor
   
   /**
    * Constructs a new {@link DryWeightTool}.
    * @param properties the {@link DryWeightProperties}.
    * @param converter the {@link DryWeightConverter}.
    */
   DryWeightTool( DryWeightProperties properties, DryWeightConverter converter ) {
      this.properties = properties;
      converter.associate( properties );
   }//End Constructor

   /**
    * Access to the {@link DryWeightProperties}.
    * @return the {@link DryWeightProperties}.
    */
   public DryWeightProperties properties() {
      return properties;
   }//End Method

}//End Class
