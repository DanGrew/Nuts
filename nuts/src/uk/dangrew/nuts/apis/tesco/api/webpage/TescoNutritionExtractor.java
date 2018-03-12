package uk.dangrew.nuts.apis.tesco.api.webpage;

import uk.dangrew.nuts.apis.tesco.api.parsing.CalculatedNutritionParsingHandler;
import uk.dangrew.nuts.apis.tesco.api.parsing.CalculatedNutritionType;
import uk.dangrew.nuts.apis.tesco.graphics.selection.TescoStringParser;

public class TescoNutritionExtractor implements Runnable {

   static final String EXCLUSION_REFRENCE_INTAKE = "reference intake";
   static final String EXCLUSION_INVALID_COLUMN_NAME = "-";
   
   private final TescoStringParser stringParser;
   private final TescoWebpageNutritionTable table;
   private final CalculatedNutritionParsingHandler model;
   
   public TescoNutritionExtractor( TescoWebpageNutritionTable table, CalculatedNutritionParsingHandler model ) {
      this.table = table;
      this.model = model;
      this.stringParser = new TescoStringParser();
   }//End Constructor
   
   @Override public void run() {
      removeExclusions();
      detectSplitEnergyRows();
      performExtraction();
   }//End Method
   
   private void performExtraction() {
      model.setPer100Header( table.columnRow( 1, 0 ) );
      model.setPerServingHeader( table.columnRow( 2, 0 ) );
      for ( int row = 1; row < table.rowCount(); row++ ) {
         String name = table.columnRow( 0, row );
         if ( name == null ) {
            continue;
         }
         
         model.startedCalculatedNutrientsObject();
         model.setName( name );
         model.setValuePer100( table.columnRow( 1, row ) );
         model.setValuePerServing( table.columnRow( 2, row ) );
         model.finishedCalculatedNutrientsObject();
      }
   }//End Method
   
   private void removeExclusions(){
      for ( int column = 0; column < table.columnCount(); column++ ) {
         String name = table.columnRow( column, 0 );
         if ( isColumnExclusion( name ) ) {
            table.clearColumn( column );
         }
      }
      for ( int row = 1; row < table.rowCount(); row++ ) {
         String name = table.columnRow( 0, row );
         if ( isRowExclusion( name ) ) {
            table.clearRow( row );
         }
      }
   }//End Method
   
   private boolean isRowExclusion( String rowName ) {
      if ( rowName == null ) {
         return false;
      }
      String parseable = rowName.toLowerCase();
      if ( parseable.contains( EXCLUSION_REFRENCE_INTAKE ) ) {
         return true;
      }
      return false;
   }//End Method
   
   private boolean isColumnExclusion( String columnName ) {
      if ( columnName == null ) {
         return false;
      }
      String parseable = columnName.toLowerCase();
      if ( parseable.contains( EXCLUSION_INVALID_COLUMN_NAME ) ) {
         return true;
      }
      return false;
   }//End Method
   
   private void detectSplitEnergyRows(){
      if ( !stringParser.isCombinedEnergy( table.columnRow( 0, 1 ) ) ){
         return;
      }
      boolean firstRowKj = CalculatedNutritionType.EnergyInKj.matches( table.columnRow( 1, 1 ) );
      boolean secondRowKcal = CalculatedNutritionType.EnergyInKcal.matches( table.columnRow( 1, 2 ) );
      boolean isSplitAcrossRows = firstRowKj && secondRowKcal;
      
      if ( !isSplitAcrossRows ) {
         return;
      }
      
      table.modifyColumnRow( 0, 1, "Energy (kj)" );
      table.modifyColumnRow( 0, 2, "Energy (kcal)" );
   }//End Method
   

}//End Class
