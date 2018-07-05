package uk.dangrew.nuts.graphics.table.configuration;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class TableColumnWidths {

   public static final double DEFAULT = 0.5;
   
   private double foodNameWidth;
   private double portionWidth;
   private double goalWidth;
   private final Map< NutritionalUnit, Double > unitWidths;
   private double combinedUnitWidth;

   public TableColumnWidths() {
      this.foodNameWidth = DEFAULT;
      this.portionWidth = DEFAULT;
      this.goalWidth = DEFAULT;
      this.unitWidths = new EnumMap<>( NutritionalUnit.class );
      Stream.of( NutritionalUnit.values() )
         .collect( Collectors.toMap( Function.identity(), u -> DEFAULT ) )
         .forEach( unitWidths::put );
      this.combinedUnitWidth = DEFAULT;
   }//End Constructor

   public double foodNameWidth() {
      return foodNameWidth;
   }//End Method

   public TableColumnWidths withFoodNameWidth( double width ) {
      this.foodNameWidth = width;
      return this;
   }//End Method

   public double portionWidth() {
      return portionWidth;
   }//End Method

   public TableColumnWidths withPortionWidth( double width ) {
      this.portionWidth = width;
      return this;
   }//End Method
   
   public double goalWidth() {
      return goalWidth;
   }//End Method
   
   public TableColumnWidths withGoalWidth( double width ) {
      this.goalWidth = width;
      return this;
   }//End Method

   public double unitWidthFor( NutritionalUnit unit ) {
      return unitWidths.get( unit );
   }//End Method

   public TableColumnWidths withUnitWidth( NutritionalUnit unit, double width ) {
      this.unitWidths.put( unit, width );
      return this;
   }//End Method

   public double combinedUnitWidth() {
      return combinedUnitWidth;
   }//End Method

   public TableColumnWidths withCombinedUnitWidth( double width ) {
      this.combinedUnitWidth = width;
      return this;
   }//End Method

   public double individualUnitWidthFor( int numberOfVisibleUnits ) {
      return combinedUnitWidth / numberOfVisibleUnits;
   }//End Method
   
}//End Class
